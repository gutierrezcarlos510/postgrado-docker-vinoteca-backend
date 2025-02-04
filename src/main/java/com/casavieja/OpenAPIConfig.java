package com.casavieja;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {

    @Value("${platform.openapi.dev-url}")
    private String devUrl;

    @Value("${platform.openapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in Production environment");

        Contact contact = new Contact();
        contact.setEmail("gutierrezcarlos510@gmail.com");
        contact.setName("cgutierrez");
        contact.setUrl("https://www.sibol.net");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Documentacion API")
                .version("1.0")
                .contact(contact)
                .description("Esta API expone los EndPoint para conocimiento del equipo de desarrollo.").termsOfService("https://www.sibol.net/terminos")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(Arrays.asList(devServer, prodServer));
    }
}