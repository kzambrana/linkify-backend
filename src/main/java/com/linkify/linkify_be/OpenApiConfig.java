package com.linkify.linkify_be;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Linkify API", version = "1.0", description = "API for managing profile links in Linkify"))
public class OpenApiConfig {
}
