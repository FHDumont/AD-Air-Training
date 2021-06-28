package com.java.acme.kafka;

import com.java.acme.kafka.AcmeKafkaConsumer;
import com.java.acme.WebConnect;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BookingServicesKafkaListener extends AcmeKafkaConsumer {

    public void handleMessage(String message) {

        try {

            KafkaMessage kafkaMessage = new ObjectMapper().readValue(message, KafkaMessage.class);
            String requestAction = kafkaMessage.getMessage();

            System.out.println("handleMessage: " + message + ", requestAction: " + requestAction);

            if (requestAction.equals("reserveFlight") || requestAction.equals("managerApproval")
                    || requestAction.equals("paymentIssued") || requestAction.equals("bookFlight")
                    || requestAction.equals("checkout")) {
                WebConnect.makeWebRequest("approval-services", "8080", "approval-services/" + requestAction);
            }
        } catch (Exception e) {
            System.out.println("Error from BookingServicesKafkaListener:handleMessage: " + e.getMessage());
        }

    }
}