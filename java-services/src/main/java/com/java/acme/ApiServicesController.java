package com.java.acme;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api-services")
public class ApiServicesController extends BaseController {

    @RequestMapping(value = { "", "/" })
    public String home() {
        return "Hello from api-services:home()";
    }

    @RequestMapping("/getFlightDetails")
    public String getFlightDetails(HttpServletRequest request) {
        String requestAction = getRequestAction(request);
        WebConnect.makeWebRequest("flight-services", "8080", "flight-services/" + requestAction);
        return "Hello from " + request.getServletPath();
    }

    @RequestMapping("/searchForFlights")
    public String searchForFlights(HttpServletRequest request) {
        String requestAction = getRequestAction(request);
        WebConnect.makeWebRequest("flight-services", "8080", "flight-services/" + requestAction);
        return "Hello from " + request.getServletPath();
    }

    @RequestMapping("/getPromotions")
    public String getPromotions(HttpServletRequest request) {
        String requestAction = getRequestAction(request);
        WebConnect.makeWebRequest("flight-services", "8080", "flight-services/" + requestAction);
        return "Hello from " + request.getServletPath();
    }

    @RequestMapping("/getAirportDetails")
    public String getAirportDetails(HttpServletRequest request) {
        String requestAction = getRequestAction(request);
        WebConnect.makeWebRequest("flight-services", "8080", "flight-services/" + requestAction);
        return "Hello from " + request.getServletPath();
    }

    @RequestMapping("/chooseSeat")
    public String chooseSeat(HttpServletRequest request) {
        String requestAction = getRequestAction(request);
        WebConnect.makeWebRequest("flight-services", "8080", "flight-services/" + requestAction);
        return "Hello from " + request.getServletPath();
    }

    @RequestMapping("/bookFlight")
    public String bookFlight(@RequestBody String postPayload, HttpServletRequest request) {
        String requestAction = getRequestAction(request);
        WebConnect.postWebRequest("customer-services", "8080", "customer-services/" + requestAction, postPayload);
        return "Hello from " + request.getServletPath();
    }

    @RequestMapping("/reserveFlight")
    public String reserveFlight(@RequestBody String postPayload, HttpServletRequest request) {
        String requestAction = getRequestAction(request);
        WebConnect.postWebRequest("customer-services", "8080", "customer-services/" + requestAction, postPayload);
        return "Hello from " + request.getServletPath();
    }

    @RequestMapping("/managerApproval")
    public String managerApproval(@RequestBody String postPayload, HttpServletRequest request) {
        String requestAction = getRequestAction(request);
        WebConnect.postWebRequest("customer-services", "8080", "customer-services/" + requestAction, postPayload);
        return "Hello from " + request.getServletPath();
    }

    @RequestMapping("/paymentIssued")
    public String paymentIssued(@RequestBody String postPayload, HttpServletRequest request) {
        String requestAction = getRequestAction(request);
        WebConnect.postWebRequest("customer-services", "8080", "customer-services/" + requestAction, postPayload);
        return "Hello from " + request.getServletPath();
    }

    @RequestMapping("/ticketIssued")
    public String ticketIssued(@RequestBody String postPayload, HttpServletRequest request) {
        String requestAction = getRequestAction(request);
        WebConnect.postWebRequest("customer-services", "8080", "customer-services/" + requestAction, postPayload);
        return "Hello from " + request.getServletPath();
    }

    @RequestMapping("/checkout")
    public String checkout(@RequestBody String postPayload, HttpServletRequest request) {
        String requestAction = getRequestAction(request);
        WebConnect.postWebRequest("customer-services", "8080", "customer-services/" + requestAction, postPayload);
        return "Hello from " + request.getServletPath();
    }
}