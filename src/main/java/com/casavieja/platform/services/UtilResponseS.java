package com.casavieja.platform.services;

import com.casavieja.platform.data.DataResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface UtilResponseS {
    ResponseEntity<DataResponse> getExceptionService(Exception e);

    ResponseEntity<DataResponse> getExceptionController(Exception e);

    ResponseEntity<DataResponse> getErrorValidation(BindingResult result);
}
