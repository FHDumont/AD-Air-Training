package com.java.acme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.java.acme.kafka.*;

@SpringBootApplication
@RestController
public class Application {

    @RequestMapping(value = { "", "/" })
    public String home() {
        return "Hello World";
    }

    public static void main(String[] args) {

        String topicList = System.getenv("TOPIC_LIST");
        String kafkaListenerClass = System.getenv("KAFKA_LISTENER_CLASS");

        if (kafkaListenerClass != null && kafkaListenerClass.length() > 0) {

            try {
                System.out.println("main: Starting Kafka consumer thread.");
                Class<?> classObject = Class.forName(kafkaListenerClass);
                AcmeKafkaConsumer acmeKafkaConsumer = (AcmeKafkaConsumer) classObject.newInstance();
                acmeKafkaConsumer.initListener(topicList);
            } catch (Throwable e) {
                System.err.println(e);
            }
        } else {
            SpringApplication.run(Application.class, args);
        }
    }
}
