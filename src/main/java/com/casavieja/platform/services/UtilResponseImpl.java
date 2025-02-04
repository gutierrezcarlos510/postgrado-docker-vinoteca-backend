package com.casavieja.platform.services;

import com.casavieja.platform.data.DataResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UtilResponseImpl implements UtilResponseS {
    @Override
    public ResponseEntity<DataResponse> getExceptionService(Exception e) {
        e.printStackTrace();
        return new DataResponse<String>("Error en servicio:"+e.getMessage())
                .getResult(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    public ResponseEntity<DataResponse> getExceptionController(Exception e) {
        e.printStackTrace();
        return new DataResponse<String>("Error en controlador:"+e.getMessage())
                .getResult(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<DataResponse> getErrorValidation(BindingResult result) {
        List<String> errors = result.getFieldErrors().stream().map(it->"Campo: "+it.getField()+" -> "+it.getDefaultMessage()).collect(Collectors.toList());
        return new DataResponse<>(errors, "Error al validar los datos").getResult(HttpStatus.BAD_REQUEST);
    }
}
