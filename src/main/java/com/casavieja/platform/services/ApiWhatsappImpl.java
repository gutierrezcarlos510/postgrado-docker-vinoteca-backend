package com.casavieja.platform.services;

import com.casavieja.platform.data.RequestSendText;
import com.casavieja.platform.data.ResponseSendText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiWhatsappImpl implements ApiWhatsappS {
//    private static final String  URL_CONEXION = "http://localhost:3000/api/";
    private static final String  URL_CONEXION = "http://sibol.click/wa-api/";
    private static final String WHATSAPP_ID = "client_admintv1836";
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public HttpStatus enviarWhatsapp(String celular, String msg) {
        try {
            RequestSendText obj = new RequestSendText();
            obj.setTo(celular);
            obj.setMessage(msg);
            obj.setClientId(WHATSAPP_ID);
            ResponseEntity<ResponseSendText> response =  restTemplate.postForEntity(URL_CONEXION + "send-message", obj, ResponseSendText.class);
            Thread.sleep(1000);
            return response.getStatusCode();
        } catch (Exception ex) {
            ex.printStackTrace();
            return HttpStatus.BAD_REQUEST;
        }
    }
}