package com.gzu.demo2;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

@WebListener
public class RequestLoggerListener implements ServletRequestListener {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        long startTime = System.currentTimeMillis();
        String clientIp = request.getRemoteAddr();
        String requestMethod = request.getMethod();
        String requestURI = request.getRequestURI();
        String queryString = request.getQueryString();
        String userAgent = request.getHeader("User-Agent");

        String logMessage = String.format("%s - Request started. IP: %s, Method: %s, URI: %s, Query String: %s, User-Agent: %s",
                dateFormat.format(new Date()), clientIp, requestMethod, requestURI, queryString, userAgent);
        System.out.println(logMessage);

        request.setAttribute("startTime", startTime);
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        long startTime = (long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long processingTime = endTime - startTime;

        String clientIp = request.getRemoteAddr();
        String requestMethod = request.getMethod();
        String requestURI = request.getRequestURI();
        String queryString = request.getQueryString();
        String userAgent = request.getHeader("User-Agent");

        String logMessage = String.format("%s - Request ended. IP: %s, Method: %s, URI: %s, Query String: %s, User-Agent: %s, Processing Time: %d ms",
                dateFormat.format(new Date()), clientIp, requestMethod, requestURI, queryString, userAgent, processingTime);
        System.out.println(logMessage);
    }
}