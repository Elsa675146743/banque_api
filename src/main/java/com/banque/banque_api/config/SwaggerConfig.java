package com.banque.banque_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Système de Transactions Bancaires — API")
                        .version("1.0.0")
                        .description("API REST de gestion de comptes bancaires — Spring Boot / MySQL"))
                .servers(List.of(
                        new Server().url("https://banque-api.onrender.com").description("Production"),
                        new Server().url("http://localhost:8080").description("Local")));
    }
}