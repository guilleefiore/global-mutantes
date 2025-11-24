package ar.edu.utn.mutantes.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InvalidDnaExceptionHandlerTest {

    private final InvalidDnaExceptionHandler handler = new InvalidDnaExceptionHandler();

    @Test
    void testHandleInvalidDnaException() {

        InvalidDnaException ex =
                new InvalidDnaException("ADN inválido de prueba");

        ResponseEntity<Object> response =
                handler.handleInvalidDnaException(ex);

        // 1. Código HTTP correcto
        assertEquals(400, response.getStatusCode().value());

        // 2. El body debe ser un Map
        assertTrue(response.getBody() instanceof Map);

        Map<?, ?> body = (Map<?, ?>) response.getBody();

        // 3. Verificar campos
        assertEquals("Invalid DNA", body.get("error"));
        assertEquals("ADN inválido de prueba", body.get("message"));
        assertNotNull(body.get("timestamp"));
    }
}
