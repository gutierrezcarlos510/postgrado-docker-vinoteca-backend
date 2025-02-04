package com.casavieja.platform.services;

import com.casavieja.platform.data.DataResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

public interface CodeVerifyS {

    @Transactional
    ResponseEntity<DataResponse> generarCodigo(String celular);
}
