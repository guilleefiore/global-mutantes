package ar.edu.utn.mutantes.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class IllegalArgumentExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void testHandleIllegalArgument() {

        IllegalArgumentException ex =
                new IllegalArgumentException("Parametro inválido");

        ResponseEntity<Map<String, Object>> response =
                handler.handleIllegalArgument(ex);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("Bad Request", response.getBody().get("error"));
        assertEquals("Parametro inválido", response.getBody().get("message"));
        assertTrue(response.getBody().containsKey("timestamp"));
    }
}
