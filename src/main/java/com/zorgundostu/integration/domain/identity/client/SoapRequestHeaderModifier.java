package com.zorgundostu.integration.domain.identity.client;

import jakarta.xml.soap.MimeHeaders;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.soap.saaj.SaajSoapMessage;

import java.util.Base64;

public class SoapRequestHeaderModifier implements WebServiceMessageCallback {
    private final String userName = "user";
    private final String passWd = "passwd";

    @Override
    public void doWithMessage(WebServiceMessage message) {
        if (message instanceof SaajSoapMessage) {
            SaajSoapMessage soapMessage = (SaajSoapMessage) message;
            MimeHeaders mimeHeader = soapMessage.getSaajMessage().getMimeHeaders();
            mimeHeader.setHeader("SOAPAction", "http://tckimlik.nvi.gov.tr/WS/TCKimlikNoDogrula");
            mimeHeader.setHeader("Content-Type", "text/xml; charset=utf-8");
            mimeHeader.setHeader("Host", "tckimlik.nvi.gov.tr");
        }
    }

    private String getB64Auth(String login, String pass) {
        String source = login + ":" + pass;
        String retunVal = "Basic " + Base64.getUrlEncoder().encodeToString(source.getBytes());
        return retunVal;
    }
}
