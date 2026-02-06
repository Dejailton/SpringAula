package com.deloitte.springaula.config;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gerenciamento de Produtos")
                        .version("1.0.0")
                        .description("API REST para gerenciamento de produtos. " +
                                "Esta API permite operações CRUD completas (Create, Read, Update, Delete) " +
                                "em produtos, com validações personalizadas e tratamento global de exceções.")
                        .contact(new Contact()
                                .name("Dejailton da Silva")
                                .email("devdejailton@gmail.com"))
                        .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0"))
                );
    }
}