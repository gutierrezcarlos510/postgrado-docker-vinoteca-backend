package com.casavieja.platform.rest;

import com.casavieja.platform.data.DataResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InicioRest {
	@GetMapping("/inicio")
	public ResponseEntity<DataResponse> saveRegister(){
		return new DataResponse("Hola").getResult(HttpStatus.OK);
	}
}