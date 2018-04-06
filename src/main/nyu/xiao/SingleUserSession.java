package main.nyu.xiao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

/**
 * Author: zx
 * Date: 04/04/2018
 * Description: SingleUserSession, represents one user's session
 */
public class SingleUserSession {

    // user's ip
    private final String userIp;

    // the first time (same with recordID) that the user send a request to website
    private final int firstRequestId;
    // total number of requests during the session
    private int totalCount;

    // date and time of the first webpage request in the session (yyyy-mm-dd hh:mm:ss)
    private final LocalDateTime firstRequestTime;
    // date and time of the last webpage request in the session (yyyy-mm-dd hh:mm:ss)
    private LocalDateTime lastRequestTime;

    /**
     * Constructor, create a new session for a specific user
     * @param record Record object
     */
    public SingleUserSession(Record record) {
        this.userIp = record.getIp();
        this.firstRequestId = record.getRecordId();
        this.totalCount = 1;
        this.firstRequestTime = record.getDateTime();
        this.lastRequestTime = record.getDateTime();
    }

    /**
     * Update session information, including total number of requests and last request datetime
     * @param record Record object
     */
    public void updateSession(Record record) {
        this.totalCount++;
        this.lastRequestTime = record.getDateTime();
    }

    /**
     * Format the output for the output file
     * @return A formatted String
     */
    public String getSessionOutPut() {
        DateTimeFormatter dtf = Utils.getCustomDateTimeFormatter();
        StringJoiner sj = new StringJoiner(",");
        sj.add(getUserIp());
        sj.add(dtf.format(getFirstRequestTime()));
        sj.add(dtf.format(getLastRequestTime()));
        sj.add(String.valueOf(Utils.getDuration(getFirstRequestTime(), getLastRequestTime()) + 1));
        sj.add(String.valueOf(getTotalCount()));
        return sj.toString();
    }

    public String getUserIp() {
        return this.userIp;
    }

    public int getFirstRequestId() {
        return this.firstRequestId;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public LocalDateTime getFirstRequestTime() {
        return this.firstRequestTime;
    }

    public LocalDateTime getLastRequestTime() {
        return this.lastRequestTime;
    }
}
