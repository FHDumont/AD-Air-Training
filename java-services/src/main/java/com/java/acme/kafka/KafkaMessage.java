package com.java.acme.kafka;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class KafkaMessage {

    @JsonProperty("message")
    private String message;
    @JsonProperty("postData")
    private String postData;

    /**
     * No args constructor for use in serialization
     * 
     */
    public KafkaMessage() {
    }

    /**
     * 
     * @param message
     * @param errantTier
     * @param anomaly
     */
    public KafkaMessage(String message) {
        super();
        this.message = message;
    }

    public KafkaMessage(String message, String postData) {
        super();
        this.message = message;
        this.postData = postData;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("postData")
    public String getPostData() {
        return postData;
    }

    @JsonProperty("postData")
    public void setPostData(String postData) {
        this.postData = postData;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        String output = null;
        try {
            output = mapper.writeValueAsString(this);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } finally {

        }
        return output;
    }
}