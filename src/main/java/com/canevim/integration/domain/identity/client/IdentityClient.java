package com.canevim.integration.domain.identity.client;

import java.io.IOException;
import java.util.Iterator;

import javax.xml.transform.dom.DOMSource;

import org.springframework.stereotype.Service;
import org.springframework.ws.soap.SoapHeaderElement;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.canevim.integration.config.SoapClientConfig;
import com.canevim.integration.domain.identity.model.Header;
import com.canevim.integration.domain.identity.model.identity.IdentityDto;
import com.canevim.integration.domain.identity.model.identity.TCKimlikNoDogrula;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Slf4j
@Service
@AllArgsConstructor
public class IdentityClient {

    private final SoapClientConfig soapClientConfig;
    public boolean verifyIdentityNumber(IdentityDto identityDto) {

        TCKimlikNoDogrula tcKimlikNoDogrulaRequest = new TCKimlikNoDogrula();
        tcKimlikNoDogrulaRequest.setAd(identityDto.name());
        tcKimlikNoDogrulaRequest.setSoyad(identityDto.surname());
        tcKimlikNoDogrulaRequest.setDogumYili(Integer.parseInt(identityDto.birthYear().trim()));
        tcKimlikNoDogrulaRequest.setTcKimlikNo(Long.parseLong(identityDto.identityNumber().trim()));

        String responseBody = sendOkHttpRequest(tcKimlikNoDogrulaRequest);
        return handleResponse(responseBody);

    }

    private boolean handleResponse(String responseBody) {
        if (responseBody == null || responseBody.contains("soap:Fault") || !responseBody.contains("<TCKimlikNoDogrulaResult>")
                || !responseBody.contains("</TCKimlikNoDogrulaResult>")) {
            return false;
        }
        String result = responseBody.substring(responseBody.indexOf("<TCKimlikNoDogrulaResult>") + 25, responseBody.indexOf("</TCKimlikNoDogrulaResult>"));
        if (result == null || result.isEmpty()) {
            return false;
        }
        if (result.equals("true") || result.equals("false")) {
            return Boolean.parseBoolean(result);
        }
        return false;
    }

    private String sendOkHttpRequest(TCKimlikNoDogrula tcKimlikNoDogrulaRequest) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            MediaType mediaType = MediaType.parse("text/xml; charset=utf-8");
            StringBuilder message = new StringBuilder();
            message.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                    "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                    "  <soap:Body>\n" +
                    "    <TCKimlikNoDogrula xmlns=\"http://tckimlik.nvi.gov.tr/WS\">\n" +
                    "      <TCKimlikNo>");
            message.append(tcKimlikNoDogrulaRequest.getTcKimlikNo());
            message.append("</TCKimlikNo>\n" +
                    "      <Ad>");
            message.append(tcKimlikNoDogrulaRequest.getAd());
            message.append("</Ad>\n" +
                    "      <Soyad>");
            message.append(tcKimlikNoDogrulaRequest.getSoyad());
            message.append("</Soyad>\n" +
                    "      <DogumYili>");
            message.append(tcKimlikNoDogrulaRequest.getDogumYili());
            message.append("</DogumYili>\n" +
                    "    </TCKimlikNoDogrula>\n" +
                    "  </soap:Body>\n" +
                    "</soap:Envelope> ");
            RequestBody body = RequestBody.create(mediaType, message.toString());
            Request request = new Request.Builder().url(soapClientConfig.getUrl()).method("POST", body).addHeader("Content-Type", "text/xml; charset=utf-8").addHeader("SOAPAction", "http://tckimlik.nvi.gov.tr/WS/TCKimlikNoDogrula").addHeader("Host", "tckimlik.nvi.gov.tr").addHeader("Cookie", "TS0193588c=01e4b30442edcea2748d7b071d9023fa73ac48293e12d529c463088dd409e9bc94eaa2357ad87d9ea232fbf3d92c10d36ed72c0555").build();
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            log.info("response: {}", responseBody);
            return responseBody;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setHeaderObj(Iterator<SoapHeaderElement> headerElements, Header headerObj) {
        DOMSource bodyDomSource = (DOMSource) headerElements.next().getSource();
        Node resultStatus = bodyDomSource.getNode();
        NodeList resultStatusItems = resultStatus.getChildNodes();
        for (int i = 0; i < resultStatusItems.getLength(); i++) {
            Node item = resultStatusItems.item(i);
            if (item.getFirstChild() != null) {
                String value = item.getFirstChild().getNodeValue();
                if (item.getNodeName().equalsIgnoreCase("errorcode")) {
                    headerObj.setErrorCode(value);
                }
                if (item.getNodeName().equalsIgnoreCase("description")) {
                    headerObj.setDescription(value);
                }
                if (item.getNodeName().equalsIgnoreCase("message")) {
                    headerObj.setMessage(value);
                }
            }

        }
    }

}
