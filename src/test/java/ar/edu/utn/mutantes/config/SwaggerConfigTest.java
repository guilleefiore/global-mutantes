package ar.edu.utn.mutantes.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SwaggerConfigTest {

    @Test
    void testClassExists() {
        SwaggerConfig config = new SwaggerConfig();
        assertNotNull(config);
        assertTrue(config.getClass().isAnnotationPresent(
                io.swagger.v3.oas.annotations.OpenAPIDefinition.class
        ));
    }
}
