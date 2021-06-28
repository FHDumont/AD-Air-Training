package com.java.acme;

import java.util.Random;

public class MySQLConnection {
    public MySQLConnection() {
    }

    public void queryDB(String dbName, String query) {
        queryDB(dbName, query, 50, 75);
    }

    public void queryDB(String dbName, String query, int minDelayMS, int maxDelayMS) {

        try {
            int delayInt = minDelayMS;
            Random rand = new Random();
            delayInt += rand.nextInt(maxDelayMS - minDelayMS);

            try {
                Thread.sleep(delayInt);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}