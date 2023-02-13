package com.canevim.integration.client;

import static com.canevim.integration.client.IntegrationHttpClientConstants.AUTHORIZATION_KEY;
import static com.canevim.integration.client.IntegrationHttpClientConstants.BEARER_KEY;
import static com.canevim.integration.client.IntegrationHttpClientConstants.CORRELATION_ID;
import static com.canevim.integration.client.IntegrationHttpClientConstants.CORRELATION_ID_DATE_FORMAT;
import static com.canevim.integration.client.IntegrationHttpClientConstants.CORRELATION_ID_PREFIX;
import static com.canevim.integration.client.IntegrationHttpClientConstants.TOKEN_KEY;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;


@Component
public class RequestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
            Enumeration<String> headerNames = httpRequest.getHeaderNames();
            if (headerNames != null) {
                String token = httpRequest.getHeader(AUTHORIZATION_KEY);
                MDC.put(TOKEN_KEY, BEARER_KEY + token);
            }
            MDC.put(CORRELATION_ID, generateCorrelationId());
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDC.remove(TOKEN_KEY);
            MDC.remove(CORRELATION_ID);
        }
    }

    private static String generateCorrelationId() {
        var formatter = DateTimeFormatter.ofPattern(CORRELATION_ID_DATE_FORMAT);
        var time = LocalDateTime.now();
        return CORRELATION_ID_PREFIX + time.format(formatter);
    }
}
