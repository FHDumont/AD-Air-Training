package com.java.acme.kafka;

import java.util.*;

import com.java.acme.kafka.AcmeKafkaConsumer;
import com.java.acme.WebConnect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.Random;

public class TicketServicesKafkaListener extends AcmeKafkaConsumer {

    private String MONGO_URL = "mongodb://mongo-tickets:27017";
    private String DATABASE_NAME = "tickets";

    public void handleMessage(String message) {

        try {

            KafkaMessage kafkaMessage = new ObjectMapper().readValue(message, KafkaMessage.class);
            String requestAction = kafkaMessage.getMessage();

            System.out.println("handleMessage: " + message + ", requestAction: " + requestAction);

            if (requestAction.equals("reserveFlight")) {

                try {

                    Document doc = Document.parse(kafkaMessage.getPostData());
                    ObjectMapper mapper = new ObjectMapper();
                    System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(doc));
                    doc.put("requestDate", new Date());

                    int nextStepDelay = doc.getInteger("step2Delay", 60); // delay is in seconds
                    String ticketId = doc.getString("ticketId");
                    insertTicketInfo(ticketId, doc, nextStepDelay);
                } catch (Throwable e) {
                    System.err.println(e);
                }

            } else if (requestAction.equals("checkout")) {

                try {

                    Document filter = new Document("paymentIssuedDate", new Document("$exists", false));
                    filter.append("nextStepAfter", new Document("$lt", new Date()));
                    getRandomDocument(filter);

                    WebConnect.makeWebRequest("fraud-check", "8080", "fraudCheck");
                } catch (Throwable e) {
                    System.err.println(e);
                }

            } else {

                try {

                    Document filter = new Document("ticketIssuedDate", new Document("$exists", false));
                    filter.append("nextStepAfter", new Document("$lt", new Date()));
                    getRandomDocument(filter);
                } catch (Throwable e) {
                    System.err.println(e);
                }

            }
        } catch (Exception e) {
            System.out.println("Error from BookingServicesKafkaListener:handleMessage: " + e.getMessage());
        }

    }

    private void insertTicketInfo(String ticketId, Document ticketInfo, int nextStepDelay) {

        if (nextStepDelay > 0) {

            Date nextStepAfter = new Date(System.currentTimeMillis() + (nextStepDelay * 1000L));
            ticketInfo.put("nextStepAfter", nextStepAfter);

            MongoClient mongoClient = new MongoClient(new MongoClientURI(MONGO_URL));
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection(DATABASE_NAME);

            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            collection.insertOne(ticketInfo);
            mongoClient.close();
        }
    }

    private Document getRandomDocument(Document filter) {

        MongoClient mongoClient = new MongoClient(new MongoClientURI(MONGO_URL));
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        MongoCollection<Document> collection = database.getCollection(DATABASE_NAME);
        int recordCount = (int) collection.count(filter);

        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        if (recordCount > 1) {
            Random rand = new Random();
            int selectedIndex = rand.nextInt(recordCount - 1);
            Document result = collection.find(filter).skip(selectedIndex).limit(1).first();
            mongoClient.close();
            return result;
        } else {
            mongoClient.close();
            return null;
        }
    }

}