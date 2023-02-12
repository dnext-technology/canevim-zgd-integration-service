package com.zorgundostu.integration.domain.identity.client.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
@Slf4j
public class ZgdClientInterceptor implements ClientInterceptor {

    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_TYPE_VALUE_APPLICATION_SOAP_XML_CHARSET_UTF_8 = "application/soap+xml; charset=utf-8";

    @Override
    public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
        log.debug("### SOAP REQUEST ###");
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            messageContext.getRequest().writeTo(buffer);
            String payload = buffer.toString(java.nio.charset.StandardCharsets.UTF_8.name());
            log.debug(payload);
        } catch (IOException e) {
            throw new WebServiceClientException("Can not write the SOAP request into the out stream", e) {
                private static final long serialVersionUID = -7118480620416458069L;
            };
        }

        return true;
    }


    @Override
    public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
        log.debug("### SOAP RESPONSE ###");
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            messageContext.getResponse().writeTo(buffer);
            String payload = buffer.toString(java.nio.charset.StandardCharsets.UTF_8.name());
            log.debug(payload);
        } catch (IOException e) {
            throw new WebServiceClientException("Can not write the SOAP response into the out stream", e) {
                private static final long serialVersionUID = -7118480620416458069L;
            };
        }

        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
        log.debug("### SOAP FAULT ###");
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            messageContext.getResponse().writeTo(buffer);
            String payload = buffer.toString(java.nio.charset.StandardCharsets.UTF_8.name());
            log.error(payload);
            throw new RuntimeException("Exception returned by server =>" + payload);
        } catch (IOException | RuntimeException e) {
            throw new WebServiceClientException("Exception occurred => " + e.getMessage()) {
                private static final long serialVersionUID = 3538336091916808141L;
            };
        }

    }

    @Override
    public void afterCompletion(MessageContext messageContext, Exception e) throws WebServiceClientException {

    }
}
