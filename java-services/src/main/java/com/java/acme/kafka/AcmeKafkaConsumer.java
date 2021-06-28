package com.java.acme.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.net.InetAddress;
import java.net.UnknownHostException;

public abstract class AcmeKafkaConsumer {

    abstract void handleMessage(String message);

    private KafkaConsumer<String, String> kafkaConsumer;

    protected static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");

    public void initListener(String topicList) {

        org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);

        System.out.println("AcmeKafkaListener startListener");

        String hostname = "";
        try {
            InetAddress ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
            System.out.println("Your current IP address : " + ip);
            System.out.println("Your current Hostname : " + hostname);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        String[] topics = topicList.split(",");

        String kafkaBootstrapServers = "kafka:9092";
        Properties consumerProperties = new Properties();
        consumerProperties.put("bootstrap.servers", kafkaBootstrapServers);
        consumerProperties.put("group.id", hostname);
        consumerProperties.put("auto.commit.interval.ms", "1000");
        consumerProperties.put("max.poll.records", "1");
        consumerProperties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProperties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        kafkaConsumer = new KafkaConsumer<>(consumerProperties);
        kafkaConsumer.subscribe(Arrays.asList(topics));

        Thread kafkaConsumerThread = new Thread(() -> {
            System.out.println("Starting AcmeKafkaListener consumer thread.");
            startListening();
        });

        kafkaConsumerThread.start();
    }

    private void startListening() {

        while (true) {

            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);

            for (ConsumerRecord<String, String> record : records) {

                final String message = record.value();
                // System.out.println("Received message: " + message);

                org.apache.log4j.Logger.getRootLogger()
                        .info(dateTimeFormatter.format(LocalDateTime.now()) + " Starting message handled thread.");
                new Thread(() -> {
                    org.apache.log4j.Logger.getRootLogger()
                            .info(dateTimeFormatter.format(LocalDateTime.now()) + " Handling message");
                    handleMessage(message);
                    org.apache.log4j.Logger.getRootLogger()
                            .info(dateTimeFormatter.format(LocalDateTime.now()) + " Message handled.");
                }).start();

                org.apache.log4j.Logger.getRootLogger()
                        .info(dateTimeFormatter.format(LocalDateTime.now()) + " Committing offset to Kafka");
                Map<TopicPartition, OffsetAndMetadata> commitMessage = new HashMap<>();
                commitMessage.put(new TopicPartition(record.topic(), record.partition()),
                        new OffsetAndMetadata(record.offset() + 1));
                kafkaConsumer.commitSync(commitMessage);
                org.apache.log4j.Logger.getRootLogger()
                        .info(dateTimeFormatter.format(LocalDateTime.now()) + " Offset committed to Kafka");
            }
        }
    }
}