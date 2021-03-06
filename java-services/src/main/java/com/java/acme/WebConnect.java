package com.java.acme;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class WebConnect {

    public static void queryDB(String dbName, String query) {
        MySQLConnection mySQLConnection = new MySQLConnection();
        mySQLConnection.queryDB(dbName, query);
    }

    public static void queryDB(String dbName, String query, int minDelayMS, int maxDelayMS) {
        MySQLConnection mySQLConnection = new MySQLConnection();
        mySQLConnection.queryDB(dbName, query, minDelayMS, maxDelayMS);
    }

    public static String makeWebRequest(String hostName, String port, String callName) {

        try {
            String url = "";
            if (hostName.startsWith("https://")) {
                url = hostName + ":" + port + "/" + callName;
            } else {
                url = "http://" + hostName + ":" + port + "/" + callName;
            }
            System.out.println("\nSending 'GET' request to URL : " + url);
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");
            String responseCode = con.getResponseCode() + "";

            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer responseBuffer = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                responseBuffer.append(inputLine);
            }
            in.close();
            return responseBuffer.toString();
        } catch (Exception e) {
            System.out.println("Error from makeWebRequest: " + e.getMessage());
            return "Error from makeWebRequest: " + e.getMessage();
        }
    }

    public static String postWebRequest(String hostName, String port, String callName, String postData) {

        try {
            String url = "";
            if (hostName.startsWith("https://")) {
                url = hostName + ":" + port + "/" + callName;
            } else {
                url = "http://" + hostName + ":" + port + "/" + callName;
            }

            System.out.println("\nSending 'POST' request to URL : " + url);
            byte[] postDataBytes = postData.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postDataBytes.length;

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setDoOutput(true);
            con.setInstanceFollowRedirects(false);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("charset", "utf-8");
            con.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            con.setUseCaches(false);
            con.getOutputStream().write(postDataBytes);

            String responseCode = con.getResponseCode() + "";
            System.out.println("Response Code : " + responseCode);
            return con.getResponseMessage();
        } catch (Exception e) {
            System.out.println("Error from postWebRequest: " + e.getMessage());
            return "Error from postWebRequest: " + e.getMessage();
        }
    }
}
