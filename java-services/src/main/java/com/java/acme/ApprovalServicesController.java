package com.java.acme;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/approval-services")
public class ApprovalServicesController extends BaseController {

    private String sapServices = getEnvVariable("SAP_SERVICES", "sap-services");
    private String sapServicesPort = getEnvVariable("SAP_SERVICES_PORT", "8080");
    
    private String getEnvVariable(String _env, String _default){
        String value = System.getenv(_env);
        if ( value == null || value == ""){
            return _default;
        } else {
            return value;
        }
    }

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
        WebConnect.makeWebRequest(sapServices, sapServicesPort, "SAP");
        return "Hello from " + request.getServletPath();
    }

    @RequestMapping("/managerApproval")
    public String managerApproval(HttpServletRequest request) {
        WebConnect.makeWebRequest(sapServices, sapServicesPort, "SAP");
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