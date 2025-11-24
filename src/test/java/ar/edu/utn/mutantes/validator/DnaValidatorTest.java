package ar.edu.utn.mutantes.validator;

import ar.edu.utn.mutantes.exception.InvalidDnaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DnaValidatorTest {

    private DnaValidator validator;

    @BeforeEach
    void setUp() {
        validator = new DnaValidator();
    }

    @Test
    void testValidDna() {
        String[] dna = {"ATGC", "CAGT", "TTAT", "AGAA"};
        assertTrue(validator.isValidDna(dna));
    }

    @Test
    void testEmptyDna() {
        String[] dna = {};
        assertThrows(InvalidDnaException.class, () -> validator.isValidDna(dna));
    }

    @Test
    void testNullDna() {
        assertThrows(InvalidDnaException.class, () -> validator.isValidDna(null));
    }

    @Test
    void testRowNull() {
        String[] dna = {"ATGC", null, "TTGT", "AGAA"};
        assertThrows(InvalidDnaException.class, () -> validator.isValidDna(dna));
    }

    @Test
    void testInvalidCharacters() {
        String[] dna = {"ATGC", "CA9T", "TTAT", "AGAA"};
        assertThrows(InvalidDnaException.class, () -> validator.isValidDna(dna));
    }

    @Test
    void testNotSquare() {
        String[] dna = {"ATGC", "CAG", "TTAT", "AGAA"};
        assertThrows(InvalidDnaException.class, () -> validator.isValidDna(dna));
    }
}
