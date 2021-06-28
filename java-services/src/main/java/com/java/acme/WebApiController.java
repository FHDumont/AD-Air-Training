package com.java.acme;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import org.bson.Document;
import org.apache.log4j.Logger;

@RestController
@RequestMapping("/")
public class WebApiController extends BaseController {

    Logger logger = Logger.getLogger(WebApiController.class);

    @RequestMapping(value = { "", "/" })
    public String home() {
        return "Hello from web-api:home()";
    }

    @RequestMapping("/login/*")
    public String login(@RequestBody String postPayload, HttpServletRequest request) {
        Document doc = Document.parse(postPayload);
        JsonUtil.parseLoginInfo(doc);
        WebConnect.makeWebRequest("auth-services", "8080", "auth-services/authenticate");
        return "Hello from " + request.getServletPath();
    }

    @RequestMapping("/web-api/getFlightDetails")
    public String getFlightDetails(HttpServletRequest request) {
        String requestAction = getRequestAction(request);
        WebConnect.makeWebRequest("auth-services", "8080", "auth-services/authenticate");
        WebConnect.makeWebRequest("api-services", "8080", "api-services/" + requestAction);
        return "Hello from " + request.getServletPath();
    }

    @RequestMapping("/web-api/searchForFlights")
    public String searchForFlights(HttpServletRequest request) {
        String requestAction = getRequestAction(request);
        WebConnect.makeWebRequest("auth-services", "8080", "auth-services/authenticate");
        WebConnect.makeWebRequest("api-services", "8080", "api-services/" + requestAction);
        return "Hello from " + request.getServletPath();
    }

    @RequestMapping("/web-api/getPromotions")
    public String getPromotions(HttpServletRequest request) {
        String requestAction = getRequestAction(request);
        WebConnect.makeWebRequest("auth-services", "8080", "auth-services/authenticate");
        WebConnect.makeWebRequest("api-services", "8080", "api-services/" + requestAction);
        return "Hello from " + request.getServletPath();
    }

    @RequestMapping("/web-api/getAirportDetails")
    public String getAirportDetails(HttpServletRequest request) {
        String requestAction = getRequestAction(request);
        WebConnect.makeWebRequest("auth-services", "8080", "auth-services/authenticate");
        WebConnect.makeWebRequest("api-services", "8080", "api-services/" + requestAction);
        return "Hello from " + request.getServletPath();
    }

    @RequestMapping("/web-api/chooseSeat")
    public String chooseSeat(HttpServletRequest request) {
        String requestAction = getRequestAction(request);
        WebConnect.makeWebRequest("auth-services", "8080", "auth-services/authenticate");
        WebConnect.makeWebRequest("api-services", "8080", "api-services/" + requestAction);
        return "Hello from " + request.getServletPath();
    }

    @RequestMapping("/web-api/bookFlight")
    public String bookFlight(@RequestBody String postPayload, HttpServletRequest request) {
        String requestAction = getRequestAction(request);
        Document doc = Document.parse(postPayload);
        JsonUtil.parseFlightInfo(doc);
        WebConnect.makeWebRequest("auth-services", "8080", "auth-services/authenticate");
        WebConnect.postWebRequest("api-services", "8080", "api-services/" + requestAction, postPayload);
        return "Hello from " + request.getServletPath();
    }

    @RequestMapping("/web-api/reserveFlight")
    public String reserveFlight(@RequestBody String postPayload, HttpServletRequest request) {
        String requestAction = getRequestAction(request);
        Document doc = Document.parse(postPayload);
        JsonUtil.parseTicketInfo(doc);
        WebConnect.makeWebRequest("auth-services", "8080", "auth-services/authenticate");
        WebConnect.postWebRequest("api-services", "8080", "api-services/" + requestAction, postPayload);
        return "Hello from " + request.getServletPath();
    }

    @RequestMapping("/web-api/managerApproval")
    public String managerApproval(@RequestBody String postPayload, HttpServletRequest request) {
        String requestAction = getRequestAction(request);
        Document doc = Document.parse(postPayload);
        JsonUtil.parseTicketInfo(doc);
        WebConnect.makeWebRequest("auth-services", "8080", "auth-services/authenticate");
        WebConnect.postWebRequest("api-services", "8080", "api-services/" + requestAction, postPayload);
        return "Hello from " + request.getServletPath();
    }

    @RequestMapping("/web-api/paymentIssued")
    public String paymentIssued(@RequestBody String postPayload, HttpServletRequest request) {
        String requestAction = getRequestAction(request);
        Document doc = Document.parse(postPayload);
        JsonUtil.parseTicketInfo(doc);
        WebConnect.makeWebRequest("auth-services", "8080", "auth-services/authenticate");
        WebConnect.postWebRequest("api-services", "8080", "api-services/" + requestAction, postPayload);
        return "Hello from " + request.getServletPath();
    }

    @RequestMapping("/web-api/ticketIssued")
    public String ticketIssued(@RequestBody String postPayload, HttpServletRequest request) {
        String requestAction = getRequestAction(request);
        Document doc = Document.parse(postPayload);
        JsonUtil.parseTicketInfo(doc);
        WebConnect.makeWebRequest("auth-services", "8080", "auth-services/authenticate");
        WebConnect.postWebRequest("api-services", "8080", "api-services/" + requestAction, postPayload);
        return "Hello from " + request.getServletPath();
    }

    @RequestMapping("/web-api/checkout")
    public String checkout(@RequestBody String postPayload, HttpServletRequest request) {
        String requestAction = getRequestAction(request);
        Document doc = Document.parse(postPayload);
        JsonUtil.parseTicketInfo(doc);
        WebConnect.makeWebRequest("auth-services", "8080", "auth-services/authenticate");
        WebConnect.postWebRequest("api-services", "8080", "api-services/" + requestAction, postPayload);
        return "Hello from " + request.getServletPath();
    }
}