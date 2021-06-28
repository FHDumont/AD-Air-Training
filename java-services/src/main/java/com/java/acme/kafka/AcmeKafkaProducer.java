package com.java.acme.kafka;

import org.apache.kafka.clients.producer.*;
import java.util.Properties;

public class AcmeKafkaProducer {

    private static KafkaProducer<String, String> kafkaProducer;

    public static void postKafkaMessage(String topicName, String message, String postPayload) {

        if (kafkaProducer == null) {
            String kafkaBootstrapServers = "kafka:9092";
            Properties producerProperties = new Properties();
            producerProperties.put("bootstrap.servers", kafkaBootstrapServers);
            producerProperties.put("acks", "all");
            producerProperties.put("retries", 0);
            producerProperties.put("batch.size", 16384);
            producerProperties.put("linger.ms", 1);
            producerProperties.put("buffer.memory", 33554432);
            producerProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            producerProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            kafkaProducer = new KafkaProducer<>(producerProperties);
        }

        KafkaMessage msg = new KafkaMessage();
        msg.setMessage(message);
        msg.setPostData(postPayload);

        ProducerRecord record = new ProducerRecord<>(topicName, msg.toString());
        kafkaProducer.send(record);
    }

    public static void postKafkaMessage(String topicName, String message) {

        if (kafkaProducer == null) {
            String kafkaBootstrapServers = "kafka:9092";
            Properties producerProperties = new Properties();
            producerProperties.put("bootstrap.servers", kafkaBootstrapServers);
            producerProperties.put("acks", "all");
            producerProperties.put("retries", 0);
            producerProperties.put("batch.size", 16384);
            producerProperties.put("linger.ms", 1);
            producerProperties.put("buffer.memory", 33554432);
            producerProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            producerProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            kafkaProducer = new KafkaProducer<>(producerProperties);
        }

        KafkaMessage msg = new KafkaMessage();
        msg.setMessage(message);
        ProducerRecord record = new ProducerRecord<>(topicName, msg.toString());
        kafkaProducer.send(record);
    }
}