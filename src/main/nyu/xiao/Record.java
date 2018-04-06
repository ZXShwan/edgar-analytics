package main.nyu.xiao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: zx
 * Date: 04/04/2018
 * Description: Record class, to deal with each line of input file (create a new record when reached a new line)
 */
public class Record {

    // HashMap, map the header's entries to their index
    private static Map<String, Integer> entryMap;
    // store the header's entries
    private final String[] entries;

    // ip address
    private final String ip;
    // combination of cik, accession and extention fields as an unique request
    private final String request;
    // date and time of request
    private LocalDateTime dateTime;
    // record id, same order with the line read from input file, increased by 1 when reading a new line
    private static int recordId;

    /**
     * Static method, initialize the entryMap with the header from input file
     * @param header The header from input file
     * @throws CustomException
     */
    public static void initFromHeader(String header) throws CustomException {
        String[] headers = Utils.getEntries(header);
        if(Utils.isArrayEmpty(headers)) {
            throw new CustomException("Input file with no header line!");
        }
        entryMap = new HashMap<>();
        for(int i = 0; i < headers.length; i++) {
            entryMap.put(headers[i], i);
        }
        recordId = 0;
    }

    /**
     * Constructor, create a new Record Object
     * @param line A line read from input file
     */
    public Record(String line) {
        this.entries = Utils.getEntries(line);
        this.ip = extractIp();
        this.request = extractRequest();
        this.dateTime = extractDateTime();
        recordId++;
    }

    /**
     * Extract a user ip from the line
     * @return A user ip (String)
     */
    private String extractIp() {
        return entries[entryMap.get("ip")];
    }

    /**
     * Extract a request from the line
     * @return A request (String)
     */
    private String extractRequest() {
        String cik = entries[entryMap.get("cik")];
        String accession = entries[entryMap.get("accession")];
        String extention = entries[entryMap.get("extention")];
        StringBuilder sb = new StringBuilder();
        return sb.append(cik).append(accession).append(extention).toString();
    }

    /**
     * Extract date time from the line
     * @return A LocalDateTime object, datetime
     */
    private LocalDateTime extractDateTime() {
        DateTimeFormatter dtf = Utils.getCustomDateTimeFormatter();
        String date = entries[entryMap.get("date")];
        String time = entries[entryMap.get("time")];
        String dateTime = date + " " + time;
        return LocalDateTime.parse(dateTime, dtf);
    }

    public String getIp() {
        return this.ip;
    }

    public String getRequest() {
        return this.request;
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    public int getRecordId() {
        return recordId;
    }
}
