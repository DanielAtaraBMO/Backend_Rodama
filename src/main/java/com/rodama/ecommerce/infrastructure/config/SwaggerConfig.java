package com.rodama.ecommerce.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("Rodama Ecommerce API")
                        .version("1.0.0")
                        .description("API REST para la plataforma ecommerce Rodama. Incluye gestión de productos, usuarios, pedidos y pagos con autenticación JWT.")
                        .contact(new Contact()
                                .name("Rodama Team")
                                .email("admin@rodama.com")
                        )
                )
                .tags(List.of(
                        new Tag().name("Autenticación").description("Endpoints para login y generación de tokens JWT"),
                        new Tag().name("Productos").description("Gestión del catálogo de productos"),
                        new Tag().name("Usuarios").description("Gestión de usuarios y clientes"),
                        new Tag().name("Pedidos").description("Gestión de pedidos de compra"),
                        new Tag().name("Pagos").description("Registro y consulta de pagos")
                ))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Ingresa el token JWT obtenido en /api/auth/login. Formato: Bearer {token}")
                        )
                );
    }
}