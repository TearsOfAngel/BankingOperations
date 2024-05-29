package ru.vcarstein.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Bank operation system",
                description = "Simple banking operations, including transferring money between accounts", version = "1.0.0",
                contact = @Contact(
                        name = "Yashenev Yuriy",
                        email = "yurij.yashenev.00@gmail.com",
                        url = "https://t.me/VCarstein"
                )
        )
)
public class OpenApiConfig {
}
