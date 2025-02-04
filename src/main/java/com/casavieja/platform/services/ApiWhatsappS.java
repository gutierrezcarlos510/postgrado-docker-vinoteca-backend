package com.casavieja.platform.services;

import org.springframework.http.HttpStatus;

public interface ApiWhatsappS {
    HttpStatus enviarWhatsapp(String celular, String msg);
}
