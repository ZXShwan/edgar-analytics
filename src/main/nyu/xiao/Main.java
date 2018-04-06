package main.nyu.xiao;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;

/**
 * Author: zx
 * Date: 04/04/2018
 * Description: Main class, to run the project
 */
public class Main {

    /**
     * Read file 'inactivity_period.txt' to get the time limit for session
     * @param inactivity_period_path file path
     * @return A int value of time limit
     */
    private static int getExpiredTimeLimit(String inactivity_period_path) {
        Scanner reader = Utils.getReader(inactivity_period_path);
        String time = reader.nextLine();
        if(Utils.isStringEmpty(time)) {
            throw new CustomException("Empty file: 'inactivity_period_path'");
        }
        int expiredTimeLimit = Integer.parseInt(time);
        if(expiredTimeLimit < 1 || expiredTimeLimit > 86400) {
            throw new CustomException("Invalid inactivity value (should range from 1 to 86400)!");
        }
        return expiredTimeLimit;
    }

    /**
     * Main method to run the project
     * @param args Standard input from console
     *             args[0]: input file path (log.csv)
     *             args[1]: period of inactivity file path (inactivity_period.txt)
     *             args[2]: output file path (sessionization.txt)
     * @throws IOException
     */
    public static void main(String[] args) throws IOException{

        String input_path = args[0];
        String inactivity_period_path = args[1];
        String output_path = args[2];

        // get reader and writer from Utils class
        Scanner reader = Utils.getReader(input_path);
        BufferedWriter writer = Utils.getWriter(output_path);

        // first line of input file which is the header information
        String header = reader.nextLine();
        Record.initFromHeader(header);

        // get all users sessions
        AllUsersSessions allUsersSessions = new AllUsersSessions(getExpiredTimeLimit(inactivity_period_path));

        // reader the file line by line
        while(reader.hasNextLine()) {
            String line = reader.nextLine();
            Record record = new Record(line);
            allUsersSessions.addNewSessionFromRecord(record);
            allUsersSessions.collectExpiredSessions();
        }

        // get the expired sessions and write to output file
        Map<Integer, SingleUserSession> expiredSessions = allUsersSessions.getExpiredSessions();
        for(SingleUserSession session : expiredSessions.values()) {
            writer.write(session.getSessionOutPut());
            writer.newLine();
        }

        // write all remaining sessions when reached the end of input file
        if(allUsersSessions.hasSessions()) {
            for(SingleUserSession session : allUsersSessions.getAllUsersSessionsMap().values()) {
                writer.write(session.getSessionOutPut());
                writer.newLine();
            }
        }

        reader.close();
        writer.close();
    }
}
