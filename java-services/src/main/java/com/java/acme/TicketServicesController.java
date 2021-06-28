package com.java.acme;

import java.util.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.Random;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

@RestController
@RequestMapping("/ticket-services")
public class TicketServicesController extends BaseController {

    private String MONGO_URL = "mongodb://mongo-tickets:27017";
    private String DATABASE_NAME = "tickets";
    Logger logger = Logger.getLogger(TicketServicesController.class);

    @RequestMapping(value = { "", "/" })
    public String home() {
        return "Hello from ticket-services:home()";
    }

    @RequestMapping("/bookFlight")
    public String bookFlight(@RequestBody String postPayload, HttpServletRequest request) {

        try {
            System.out.println("bookFlight");
            Document filter = new Document("ticketIssuedDate", new Document("$exists", false));
            filter.append("nextStepAfter", new Document("$lt", new Date()));
            getRandomDocument(filter);
        } catch (Throwable e) {
            System.err.println(e);
        }

        return "Hello from " + request.getServletPath();
    }

    @RequestMapping("/reserveFlight")
    public String reserveFlight(@RequestBody String postPayload, HttpServletRequest request) {

        try {

            Document doc = Document.parse(postPayload);
            ObjectMapper mapper = new ObjectMapper();
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(doc));
            doc.put("requestDate", new Date());

            int nextStepDelay = doc.getInteger("step2Delay", 60); // delay is in seconds
            String ticketId = doc.getString("ticketId");
            insertTicketInfo(ticketId, doc, nextStepDelay);
        } catch (Throwable e) {
            System.err.println(e);
        }

        return "Hello from " + request.getServletPath();
    }

    @RequestMapping("/managerApproval")
    public String managerApproval(@RequestBody String postPayload, HttpServletRequest request) {

        try {
            System.out.println("managerApproval");
            Document filter = new Document("managerApprovalDate", new Document("$exists", false));
            filter.append("nextStepAfter", new Document("$lt", new Date()));
            getRandomDocument(filter);
        } catch (Throwable e) {
            System.err.println(e);
        }

        return "Hello from " + request.getServletPath();
    }

    @RequestMapping("/paymentIssued")
    public String paymentIssued(@RequestBody String postPayload, HttpServletRequest request) {

        try {
            System.out.println("paymentIssued");
            Document filter = new Document("paymentIssuedDate", new Document("$exists", false));
            filter.append("nextStepAfter", new Document("$lt", new Date()));
            getRandomDocument(filter);
        } catch (Throwable e) {
            System.err.println(e);
        }

        return "Hello from " + request.getServletPath();
    }

    @RequestMapping("/ticketIssued")
    public String ticketIssued(@RequestBody String postPayload, HttpServletRequest request) {

        try {
            System.out.println("ticketIssued");
            Document filter = new Document("ticketIssuedDate", new Document("$exists", false));
            filter.append("nextStepAfter", new Document("$lt", new Date()));
            getRandomDocument(filter);
        } catch (Throwable e) {
            System.err.println(e);
        }

        return "Hello from " + request.getServletPath();
    }

    @RequestMapping("/checkout")
    public String checkout(@RequestBody String postPayload, HttpServletRequest request) {

        try {
            System.out.println("paymentIssued");
            Document filter = new Document("paymentIssuedDate", new Document("$exists", false));
            filter.append("nextStepAfter", new Document("$lt", new Date()));
            getRandomDocument(filter);

            WebConnect.makeWebRequest("fraud-check", "8080", "fraudCheck");
        } catch (Throwable e) {
            System.err.println(e);
        }

        return "Hello from " + request.getServletPath();
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