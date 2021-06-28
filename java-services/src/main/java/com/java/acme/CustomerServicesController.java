package com.java.acme;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import com.java.acme.kafka.AcmeKafkaProducer;

@RestController
@RequestMapping("/customer-services")
public class CustomerServicesController extends BaseController {

    @RequestMapping(value = { "", "/" })
    public String home() {
        return "Hello from customer-services:home()";
    }

    @RequestMapping("/bookFlight")
    public String bookFlight(@RequestBody String postPayload, HttpServletRequest request) {
        String requestAction = getRequestAction(request);
        AcmeKafkaProducer.postKafkaMessage("customer-services", requestAction, postPayload);
        return "Hello from " + request.getServletPath();
    }

    @RequestMapping("/reserveFlight")
    public String reserveFlight(@RequestBody String postPayload, HttpServletRequest request) {
        String requestAction = getRequestAction(request);
        AcmeKafkaProducer.postKafkaMessage("customer-services", requestAction, postPayload);
        return "Hello from " + request.getServletPath();
    }

    @RequestMapping("/managerApproval")
    public String managerApproval(@RequestBody String postPayload, HttpServletRequest request) {
        String requestAction = getRequestAction(request);
        AcmeKafkaProducer.postKafkaMessage("customer-services", requestAction, postPayload);
        return "Hello from " + request.getServletPath();
    }

    @RequestMapping("/ticketIssued")
    public String ticketIssued(@RequestBody String postPayload, HttpServletRequest request) {
        String requestAction = getRequestAction(request);
        AcmeKafkaProducer.postKafkaMessage("customer-services", requestAction, postPayload);
        return "Hello from " + request.getServletPath();
    }

    @RequestMapping("/checkout")
    public String checkout(@RequestBody String postPayload, HttpServletRequest request) {
        String requestAction = getRequestAction(request);
        AcmeKafkaProducer.postKafkaMessage("customer-services", requestAction, postPayload);
        return "Hello from " + request.getServletPath();
    }
}