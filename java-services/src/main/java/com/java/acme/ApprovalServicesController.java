package com.java.acme;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/approval-services")
public class ApprovalServicesController extends BaseController {

    @RequestMapping(value = { "", "/" })
    public String home() {
        return "Hello from approval-services:home()";
    }

    @RequestMapping("/bookFlight")
    public String bookFlight(HttpServletRequest request) {
        return "Hello from " + request.getServletPath();
    }

    @RequestMapping("/reserveFlight")
    public String reserveFlight(HttpServletRequest request) {
        WebConnect.makeWebRequest("sap-services", "8080", "SAP");
        return "Hello from " + request.getServletPath();
    }

    @RequestMapping("/managerApproval")
    public String managerApproval(HttpServletRequest request) {
        WebConnect.makeWebRequest("sap-services", "8080", "SAP");
        return "Hello from " + request.getServletPath();
    }

    @RequestMapping("/paymentIssued")
    public String paymentIssued(HttpServletRequest request) {
        return "Hello from " + request.getServletPath();
    }

    @RequestMapping("/checkout")
    public String checkout(HttpServletRequest request) {
        return "Hello from " + request.getServletPath();
    }
}