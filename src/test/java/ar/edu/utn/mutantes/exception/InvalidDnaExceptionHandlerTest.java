package ar.edu.utn.mutantes.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InvalidDnaExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void testHandleInvalidDnaException() {

        InvalidDnaException ex =
                new InvalidDnaException("ADN inválido de prueba");

        ResponseEntity<Map<String, Object>> response =
                handler.handleInvalidDna(ex);

        assertEquals(400, response.getStatusCode().value());
        assertTrue(response.getBody().containsKey("error"));
        assertEquals("Invalid DNA", response.getBody().get("error"));
        assertEquals("ADN inválido de prueba", response.getBody().get("message"));
    }

    @Test
    void testExceptionMessage() {
        InvalidDnaException ex = new InvalidDnaException("Mensaje prueba");

        assertEquals("Mensaje prueba", ex.getMessage());
    }

    @Test
    void testHandleInvalidDnaExceptionWithoutMessage() {

        InvalidDnaException ex = new InvalidDnaException(null);

        ResponseEntity<Map<String, Object>> response =
                handler.handleInvalidDna(ex);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("Invalid DNA", response.getBody().get("error"));
        assertNull(response.getBody().get("message"));
    }

    @Test
    void testInvalidDnaExceptionConstructor() {
        InvalidDnaException ex = new InvalidDnaException("Mensaje");
        assertEquals("Mensaje", ex.getMessage());
    }
}
