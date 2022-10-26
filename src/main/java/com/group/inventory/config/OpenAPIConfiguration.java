package com.group.inventory.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {
    @Bean
    public OpenAPI getOpenApi() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearerAuth",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info()
                        .title("Insurance Operation Service")
                        .description("Service for Education Purpose")
                        .version("v1.0")
                        .license(new License().name("NO LICENSE").url("https://github.com/nqlam961997"))
                        .contact(new Contact()
                                .email("nqlam961997@gmail.com")
                                .name("Nguyen Quang Lam")
                                .url("https://github.com/nqlam961997")))
                .externalDocs(new ExternalDocumentation()
                        .description("Spring Documentation")
                        .url("https://docs.spring.io/spring-framework/docs/current/reference/html/"));
    }
}
