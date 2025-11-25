package ar.edu.utn.mutantes.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InvalidDnaExceptionTest {

    @Test
    void testInvalidDnaExceptionStoresMessage() {

        InvalidDnaException ex = new InvalidDnaException("Mensaje de prueba");

        assertEquals("Mensaje de prueba", ex.getMessage());
    }
}
