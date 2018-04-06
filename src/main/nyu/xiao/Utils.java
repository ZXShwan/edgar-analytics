package main.nyu.xiao;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Author: zx
 * Date: 04/04/2018
 * Description: Utils class, provide common static methods
 */
public class Utils {

    /**
     * Split line by ',' and get an array
     * @param line A line contains entries
     * @return An array of entries
     */
    public static String[] getEntries(String line) {
        String[] entries = line.split(",");
        return entries;
    }

    /**
     * Check if a String Object is empty
     * @param str A String
     * @return True if it is empty
     */
    public static boolean isStringEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * Check if an Array is empty
     * @param arr An Array
     * @return True if it is empty
     */
    public static boolean isArrayEmpty(Object[] arr) {
        return arr == null || arr.length == 0;
    }

    /**
     * Get a Scanner as a file reader
     * @param inputFile A String which is the input file path
     * @return A Scanner as a reader
     */
    public static Scanner getReader(String inputFile) {
        Scanner sc = null;
        try {
            FileInputStream inputStream = new FileInputStream(inputFile);
            sc = new Scanner(inputStream);
        } finally {
            return sc;
        }
    }

    /**
     * Get a BufferedWriter as a file writer
     * @param outputFile A String which is the output file path
     * @return A BufferedWriter as a writer
     */
    public static BufferedWriter getWriter(String outputFile) {
        BufferedWriter bw = null;
        try {
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            bw = new BufferedWriter(new OutputStreamWriter(outputStream));
        } finally {
            return bw;
        }
    }

    /**
     * Get a simple DateTimeFormatter which we need to format datetime
     * @return A DateTimeFormatter
     */
    public static DateTimeFormatter getCustomDateTimeFormatter() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return formatter;
    }

    /**
     * Calculate the duration between 2 datetime
     * @param begin Begin datetime
     * @param end End datetime
     * @return Duration in seconds
     */
    public static int getDuration(LocalDateTime begin, LocalDateTime end) {
        Duration duration = Duration.between(begin, end);
        return (int) duration.toMillis() / 1000;
    }
}
