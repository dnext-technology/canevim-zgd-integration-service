package com.zorgundostu.integration.domain.identity.client;

import com.zorgundostu.integration.config.gen.TCKimlikNoDogrula;
import com.zorgundostu.integration.config.gen.TCKimlikNoDogrulaError;
import com.zorgundostu.integration.config.gen.TCKimlikNoDogrulaResponse;
import com.zorgundostu.integration.domain.identity.model.Header;
import com.zorgundostu.integration.domain.identity.model.identity.IdentityDto;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.SoapHeaderElement;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.namespace.QName;
import javax.xml.transform.dom.DOMSource;
import java.util.Iterator;

@Slf4j
public class IdentityClient extends WebServiceGatewaySupport {

    private final static QName Header_QNAME = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Header", "SOAP-ENV");
    private final static QName _Source_QNAME = new QName("http://group.vodafone.com/contract/vho/header/v1", "Source", "ek1");
    private final static QName _System_QNAME = new QName("http://group.vodafone.com/contract/vho/header/v1", "System", "ek1");

    public boolean verifyIdentityNumber(IdentityDto identityDto) {
        try {
            TCKimlikNoDogrula tcKimlikNoDogrulaRequest = new TCKimlikNoDogrula();
            tcKimlikNoDogrulaRequest.setAd(identityDto.name());
            tcKimlikNoDogrulaRequest.setSoyad(identityDto.surname());
            tcKimlikNoDogrulaRequest.setDogumYili(Integer.parseInt(identityDto.birthYear().trim()));
            tcKimlikNoDogrulaRequest.setTCKimlikNo(Long.parseLong(identityDto.identityNumber().trim()));

            JAXBContext jaxbContext = JAXBContext.newInstance(TCKimlikNoDogrula.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(tcKimlikNoDogrulaRequest, System.out);

            WebServiceTemplate webServiceTemplate = getWebServiceTemplate();

            TCKimlikNoDogrulaResponse verifyIdentityNumberResponse =
                    (TCKimlikNoDogrulaResponse) webServiceTemplate.marshalSendAndReceive(tcKimlikNoDogrulaRequest
                            , new SoapRequestHeaderModifier()
                    );
                return false;
//            return verifyIdentityNumberResponse.isTCKimlikNoDogrulaResult();
        } catch (JAXBException e) {
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
