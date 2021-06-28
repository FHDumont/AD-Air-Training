package com.java.acme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Value;
import javax.servlet.http.HttpServletRequest;
import org.bson.Document;
import java.util.Random;
import org.apache.log4j.Logger;
import java.util.Map;

  
@RestController
@RequestMapping("/web-api/flights")
public class WebApiController {

    @RequestMapping(value = {"","/"})
    public String home() {
        return "Hello from web-api:home()";
    }

	@RequestMapping("/getFlightDetails")
	public String getFlightDetails(HttpServletRequest request) {
		String requestAction = getRequestAction(request);
		WebConnect.makeWebRequest("auth-services", "8080", "auth-services/authenticate");
		WebConnect.makeWebRequest("api-services", "8080", "api-services/" + requestAction);
		return "Hello from " + request.getServletPath();
	}

	@RequestMapping("/searchForFlights")
	public String searchForFlights(HttpServletRequest request) {
		String requestAction = getRequestAction(request);
		WebConnect.makeWebRequest("auth-services", "8080", "auth-services/authenticate");
		WebConnect.makeWebRequest("api-services", "8080", "api-services/" + requestAction);
		return "Hello from " + request.getServletPath();
	}

	@RequestMapping("/getPromotions")
	public String getPromotions(HttpServletRequest request) {
		String requestAction = getRequestAction(request);
		WebConnect.makeWebRequest("auth-services", "8080", "auth-services/authenticate");
		WebConnect.makeWebRequest("api-services", "8080", "api-services/" + requestAction);
		return "Hello from " + request.getServletPath();
	}

	@RequestMapping("/getAirportDetails")
	public String getAirportDetails(HttpServletRequest request) {
		String requestAction = getRequestAction(request);
		WebConnect.makeWebRequest("auth-services", "8080", "auth-services/authenticate");
		WebConnect.makeWebRequest("api-services", "8080", "api-services/" + requestAction);
		return "Hello from " + request.getServletPath();
	}

	@RequestMapping("/chooseSeat")
	public String chooseSeat(HttpServletRequest request) {
		String requestAction = getRequestAction(request);
		WebConnect.makeWebRequest("auth-services", "8080", "auth-services/authenticate");
		WebConnect.makeWebRequest("api-services", "8080", "api-services/" + requestAction);
		return "Hello from " + request.getServletPath();
	}
	
	@RequestMapping("/reserveFlight")
	public String reserveFlight(@RequestBody String postPayload, HttpServletRequest request) {
		String requestAction = getRequestAction(request);
		Document doc = Document.parse(postPayload);
		JsonUtil.parseTicketInfo(doc);
		WebConnect.makeWebRequest("auth-services", "8080", "auth-services/authenticate");
		WebConnect.postWebRequest("api-services", "8080", "api-services/" + requestAction, postPayload);
		return "Hello from " + request.getServletPath();
	}

	@RequestMapping("/managerApproval")
	public String managerApproval(@RequestBody String postPayload, HttpServletRequest request) {
		String requestAction = getRequestAction(request);
		Document doc = Document.parse(postPayload);
		JsonUtil.parseTicketInfo(doc);
		WebConnect.makeWebRequest("auth-services", "8080", "auth-services/authenticate");
		WebConnect.postWebRequest("api-services", "8080", "api-services/" + requestAction, postPayload);
		return "Hello from " + request.getServletPath();
	}
  
	@RequestMapping("/paymentIssued")
	public String paymentIssued(@RequestBody String postPayload, HttpServletRequest request) {
		String requestAction = getRequestAction(request);
		Document doc = Document.parse(postPayload);
		JsonUtil.parseTicketInfo(doc);
		WebConnect.makeWebRequest("auth-services", "8080", "auth-services/authenticate");
		WebConnect.postWebRequest("api-services", "8080", "api-services/" + requestAction, postPayload);
		return "Hello from " + request.getServletPath();
	}
	
	@RequestMapping("/ticketIssued")
	public String ticketIssued(@RequestBody String postPayload, HttpServletRequest request) {
		String requestAction = getRequestAction(request);
		Document doc = Document.parse(postPayload);
		JsonUtil.parseTicketInfo(doc);
		WebConnect.makeWebRequest("auth-services", "8080", "auth-services/authenticate");
		WebConnect.postWebRequest("api-services", "8080", "api-services/" + requestAction, postPayload);
		return "Hello from " + request.getServletPath();
	}

	@RequestMapping("/bookFlight")
	public String bookFlight(@RequestBody String postPayload, HttpServletRequest request) {
		String requestAction = getRequestAction(request);
		Document doc = Document.parse(postPayload);
		JsonUtil.parseFlightInfo(doc);
		WebConnect.makeWebRequest("auth-services", "8080", "auth-services/authenticate");
		WebConnect.postWebRequest("api-services", "8080", "api-services/" + requestAction, postPayload);
		return "Hello from " + request.getServletPath();
	}

	@RequestMapping("/checkout")
	public String checkout(@RequestBody String postPayload, HttpServletRequest request) {
		String requestAction = getRequestAction(request);
		Document doc = Document.parse(postPayload);
		JsonUtil.parseFlightInfo(doc);
		WebConnect.makeWebRequest("auth-services", "8080", "auth-services/authenticate");
		WebConnect.postWebRequest("api-services", "8080", "api-services/" + requestAction, postPayload);
		return "Hello from " + request.getServletPath();
	}

    private String getRequestAction(HttpServletRequest request) {

        System.out.println("request.getServletPath(): " + request.getServletPath());
        String segments[] = request.getServletPath().split("/");

        if (segments.length > 0) {
            return segments[segments.length - 1];
        }
        else {
            return "";
        }
    }
}