package com.java.acme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@RestController
public class MockServicesController {

    @RequestMapping("/fraudCheck")
    public String fraudCheck() {
        processRequest();
        return "Hello from MockServices:fraudCheck";
    }

    @RequestMapping("/SAP")
    public String SAP() {
        processRequest();
        return "Hello from MockServices:SAP";
    }

    private void processRequest() {

        int delayRange = 100;
        int delayInt = 50;
        Random rand = new Random();

        if (rand.nextInt(3) == 2) {
            delayInt = 750;
            delayRange = 2000;
        }

        try {
            delayInt += rand.nextInt(delayRange);

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
