package com.java.acme;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    protected String getRequestAction(HttpServletRequest request) {

        String segments[] = request.getServletPath().split("/");

        if (segments.length > 0) {
            return segments[segments.length - 1];
        } else {
            return "";
        }
    }

    protected String isNull(String stringValue, String valueIfNull) {

        if (stringValue == null) {
            return valueIfNull;
        } else {
            return stringValue;
        }
    }

    protected boolean hasValue(String stringValue) {

        if (stringValue == null || stringValue.trim().length() == 0) {
            return false;
        } else {
            return true;
        }
    }
}
