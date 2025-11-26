package ar.edu.utn.mutantes.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Mutantes API",
                version = "1.0",
                description = "API para detección de ADN mutante y estadísticas"
        )
)
public class SwaggerConfig {}
