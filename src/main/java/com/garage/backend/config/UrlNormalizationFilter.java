package com.garage.backend.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
public class UrlNormalizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        
        // Normalize trailing slashes
        if (requestURI.length() > 1 && requestURI.endsWith("/")) {
            String normalizedURI = requestURI.substring(0, requestURI.length() - 1);
            
            // Create a wrapper with the normalized URI
            HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(httpRequest) {
                @Override
                public String getRequestURI() {
                    return normalizedURI;
                }
                
                @Override
                public String getServletPath() {
                    String servletPath = super.getServletPath();
                    if (servletPath.length() > 1 && servletPath.endsWith("/")) {
                        return servletPath.substring(0, servletPath.length() - 1);
                    }
                    return servletPath;
                }
            };
            
            chain.doFilter(wrapper, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization not needed
    }

    @Override
    public void destroy() {
        // Cleanup not needed
    }
}
