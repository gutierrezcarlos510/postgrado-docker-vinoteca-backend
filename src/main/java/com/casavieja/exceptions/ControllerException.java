package com.casavieja.exceptions;

import com.casavieja.platform.data.DataResponse;
import com.casavieja.exceptions.models.DataErrorValidation;
import com.casavieja.exceptions.models.ResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Controlador global para el manejo de las excepciones
 */
@ControllerAdvice
public class ControllerException {

    private static final Logger logger = LoggerFactory.getLogger(ControllerException.class);

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<DataResponse> handleConstraintValidation(ConstraintViolationException e) {
        DataErrorValidation lista = new DataErrorValidation();
        for (ConstraintViolation fieldError : e.getConstraintViolations()) {
            lista.addDataError(fieldError.getPropertyPath().toString(), fieldError.getMessage());
        }
        logger.error("ERROR_ENCONTRADO1: " + e.getMessage());
        e.printStackTrace();
        return new DataResponse<>(lista, "DATOS INVALIDOS").getResult(HttpStatus.NOT_ACCEPTABLE);
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DataResponse> handleValidationError(MethodArgumentNotValidException e) {
        DataErrorValidation lista = new DataErrorValidation();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            lista.addDataError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        logger.error("ERROR_ENCONTRADO2: " + e.getMessage());
        e.printStackTrace();
        return new DataResponse<>(lista, "DATOS INVALIDOS").getResult(HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler({IllegalArgumentException.class, DataAccessException.class, RuntimeException.class})
    public ResponseEntity<DataResponse> exception(Exception e) {
        logger.error("ERROR_ENCONTRADO3: " + e.getMessage());
        e.printStackTrace();
        return new DataResponse<>(e.getMessage()).getResult(HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler({NullPointerException.class, ArrayIndexOutOfBoundsException.class, IOException.class, InvalidDataAccessApiUsageException.class})
    public ResponseEntity<DataResponse> handleException(Exception e) {
        logger.error("ERROR_ENCONTRADO4: " + e.getMessage());
        e.printStackTrace();
        return new DataResponse<>(e.getMessage())
                .getResult(HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(value = ResponseException.class)
    public ResponseEntity exception(ResponseException e) {
        DataResponse response = e.getResponse();
        logger.error("ERROR_ENCONTRADO5: " + e.getMessage());
        e.printStackTrace();
        return response.getResult(HttpStatus.BAD_REQUEST);
    }
    @ResponseBody
    @ExceptionHandler({JpaSystemException.class, SQLException.class})
    public ResponseEntity<DataResponse> psqlException(Exception e) {
        logger.error("ERROR_ENCONTRADO PSQL: " + e.getMessage());
        e.printStackTrace();
        return new DataResponse<>(e.getMessage()).getResult(HttpStatus.BAD_REQUEST);
    }
}
