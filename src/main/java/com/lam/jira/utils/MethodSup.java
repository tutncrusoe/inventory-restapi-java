package com.lam.jira.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;

public class MethodSup {
    public static String getURLBase(HttpServletRequest request) throws MalformedURLException {

        URL requestURL = new URL(request.getRequestURL().toString());
        String port = requestURL.getPort() == -1 ? "" : ":" + requestURL.getPort();
        return requestURL.getProtocol() + "://" + requestURL.getHost() + port;

    }
}
