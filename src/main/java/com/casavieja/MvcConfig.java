package com.casavieja;

import java.nio.file.Paths;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.casavieja.utils.MyConstants;

@Configuration
public class MvcConfig implements WebMvcConfigurer{
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		WebMvcConfigurer.super.addResourceHandlers(registry);
		String resourcePathCliente = Paths.get(MyConstants.RUTA_CONTROLLER_PRODUCTO).toAbsolutePath().toUri().toString();
		registry.addResourceHandler(MyConstants.RUTA_VIEW_PRODUCTO +"**")
				.addResourceLocations(resourcePathCliente);
	}
	/**
	 * Controlador y vistas particulares
	 */
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/error_403").setViewName("/error/403");
	}
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
