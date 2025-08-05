package com.garage.backend.shared.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String method = httpRequest.getMethod();
        String uri = httpRequest.getRequestURI();
        String queryString = httpRequest.getQueryString();
        String fullUrl = queryString != null ? uri + "?" + queryString : uri;
        
        // Log request
        logger.info("REQUEST: {} {}", method, fullUrl);
        
        try {
            chain.doFilter(request, response);
            
            // Log response
            int statusCode = httpResponse.getStatus();
            logger.info("RESPONSE: {} {} - Status: {}", method, fullUrl, statusCode);
            
        } catch (Exception e) {
            // Log exception with full stack trace
            logger.error("EXCEPTION: {} {} - Error: {}", method, fullUrl, e.getMessage(), e);
            throw e;
        }
    }
}
