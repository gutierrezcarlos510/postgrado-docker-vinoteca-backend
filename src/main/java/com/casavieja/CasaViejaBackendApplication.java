package com.casavieja;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class CasaViejaBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(CasaViejaBackendApplication.class, args);
	}



}
