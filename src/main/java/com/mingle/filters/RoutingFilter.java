package com.mingle.filters;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class RoutingFilter implements Filter {
 
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String requestURI = req.getRequestURI().toLowerCase();
        if(requestURI.startsWith("/api")) {
            chain.doFilter(request, response);
            return;
        }
        
        if(requestURI.startsWith("/uploads")) {
            chain.doFilter(request, response);
            return;
        }
        
        if(requestURI.endsWith("js")||requestURI.endsWith("css")||requestURI.endsWith("json")||requestURI.endsWith("jpg")||requestURI.endsWith("jpeg")||requestURI.endsWith("webp")||requestURI.endsWith("png")||requestURI.endsWith("svg")||requestURI.endsWith("gif")) {
            chain.doFilter(request, response);
            return;
        }

        // all requests not api or static will be forwarded to index page. 
        request.getRequestDispatcher("/").forward(request, response);
    }

}