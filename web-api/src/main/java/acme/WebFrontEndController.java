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

@Controller
public class WebFrontEndController {

	Logger logger = Logger.getLogger(WebFrontEndController.class);

	@RequestMapping("/home")
	public String welcome(HttpServletRequest request) {
		String requestAction = getRequestAction(request);
		return "hello";
	}

	@RequestMapping(value = {"","/"})
    public String home(HttpServletRequest request) {
    	String requestAction = getRequestAction(request);
        return "welcome";
    }

	@RequestMapping("/login/*")
	public String login(@RequestBody String postPayload, HttpServletRequest request) {
		String requestAction = getRequestAction(request);
		Document doc = Document.parse(postPayload);
		JsonUtil.parseLoginInfo(doc);
		WebConnect.makeWebRequest("auth-services", "8080", "auth-services/authenticate");
		return "welcome";
	}
	
	@RequestMapping("/login")
	public String login(HttpServletRequest request) {
		String requestAction = getRequestAction(request);
		WebConnect.makeWebRequest("auth-services", "8080", "auth-services/authenticate");
		return "welcome";
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