package main.nyu.xiao;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Author: zx
 * Date: 04/04/2018
 * Description: AllUsersSessions class, to collect all users' sessions and deal with them
 */
public class AllUsersSessions {

    // LinkedHashMap, key: userIp, value: SingleUserSession
    // store all users' session and linked by recordId (the same order with the input file)
    private final Map<String, SingleUserSession> allUsersSessionsMap;

    // TreeMap, key: SingleUserSession (ordered by datetime in session), value: userIp
    // used to find expired sessions
    /*private final TreeMap<SingleUserSession, String> allUsersIpsMap;*/
    // TreeMap, key: recordId (the same order with the input file), value: SingleUserSession

    // collect all the expired sessions
    private final Map<Integer, SingleUserSession> expiredUsersSessionsMap;
    // the period of inactivity in seconds which read from file 'inactivity_period.txt'
    private final int expiredTimeLimit;
    // current request time, the same time with the line read from file 'log.csv' currently
    private LocalDateTime currentRequestTime;

    public AllUsersSessions(int expiredTimeLimit) {
        this.allUsersSessionsMap = new LinkedHashMap<>();
        /*this.allUsersIpsMap = new TreeMap<>(new Comparator<SingleUserSession>() {
            // compare the datetime in session, order the TreeMap by datetime so we will find expired sessions easily
            @Override
            public int compare(SingleUserSession o1, SingleUserSession o2) {
                if(o1.equals(o2)) {
                    return 0;
                } else {
                    if (o1.getLastRequestTime().equals(o2.getLastRequestTime())) {
                        return 1;
                    } else {
                        return o1.getLastRequestTime().compareTo(o2.getLastRequestTime());
                    }
                }
            }
        });*/
        this.expiredUsersSessionsMap = new TreeMap<>();
        this.expiredTimeLimit = expiredTimeLimit;
    }

    /**
     * When read a new line, add the session into all users' sessions (a AllUsersSessions object)
     * @param record A Record object, which created from new line
     */
    public void addNewSessionFromRecord(Record record) {
        this.currentRequestTime = record.getDateTime();
        String newIp = record.getIp();
        if(allUsersSessionsMap.containsKey(newIp)) {
            // the ip from read line already exists
            /*allUsersIpsMap.remove(allUsersSessionsMap.get(newIp));
            allUsersSessionsMap.get(newIp).updateSession(record);
            allUsersIpsMap.put(allUsersSessionsMap.get(newIp), newIp);*/
            allUsersSessionsMap.get(newIp).updateSession(record);
        } else {
            // the ip from read line is a new ip
            SingleUserSession session = new SingleUserSession(record);
            allUsersSessionsMap.put(newIp, session);
            /*allUsersIpsMap.put(session, newIp);*/
        }
    }

    /**
     * Check whether there are sessions remains
     * @return true if has remaining sessions
     */
    public boolean hasSessions() {
        return !allUsersSessionsMap.isEmpty();
    }

    /**
     * find expired sessions and collect them with a TreeMap
     */
    public void collectExpiredSessions() {
        // first way to find all expired sessions, time consuming (has to traverse all the sessions)
        for(Iterator<Map.Entry<String, SingleUserSession>> it = allUsersSessionsMap.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, SingleUserSession> entry = it.next();
            SingleUserSession session = entry.getValue();
            if(Utils.getDuration(session.getLastRequestTime(), currentRequestTime) > expiredTimeLimit) {
                expiredUsersSessionsMap.put(session.getFirstRequestId(), session);
                it.remove();
            }
        }

        // optimized method, just need to compare time limit with sessions which start from the earliest one
        // still have some bugs here
        /*SingleUserSession firstSession = allUsersIpsMap.firstKey();
        while(Utils.getDuration(firstSession.getLastRequestTime(), currentRequestTime) > expiredTimeLimit) {
            expiredUsersSessionsMap.put(firstSession.getFirstRequestId(), firstSession);
            allUsersSessionsMap.remove(allUsersIpsMap.get(firstSession));
            allUsersIpsMap.remove(firstSession);
            firstSession = allUsersIpsMap.firstKey();
        }*/

    }

    public Map<String, SingleUserSession> getAllUsersSessionsMap() {
        return this.allUsersSessionsMap;
    }

    /*public Map<SingleUserSession, String> getAllUsersIpsMap() {
        return this.allUsersIpsMap;
    }*/

    public Map<Integer, SingleUserSession> getExpiredSessions() {
        return this.expiredUsersSessionsMap;
    }
}
