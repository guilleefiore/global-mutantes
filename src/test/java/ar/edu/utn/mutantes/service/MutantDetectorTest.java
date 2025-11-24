package ar.edu.utn.mutantes.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MutantDetectorTest {

    private MutantDetector detector;

    @BeforeEach
    void setUp() {
        detector = new MutantDetector();
    }

    @Test
    void testMutantHorizontal() {
        String[] dna = {
                "AAAACT",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testMutantVertical() {
        String[] dna = {
                "ATGCGA",
                "ATGTGC",
                "ATATGT",
                "ATAAGG",
                "ATCCTA",
                "ATACGG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testMutantDiagonal() {
        String[] dna = {
                "ATGCGA",
                "CAGTAC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testHuman() {
        String[] dna = {
                "ATGCAT",
                "TACGTA",
                "CGTACG",
                "GCATGC",
                "ATGCAT",
                "TACGTA"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testInvalidMatrixNxM() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TTAT" // 3x4 → no mutante, y MutantDetector NO valida NxN
        };
        assertFalse(detector.isMutant(dna));
    }
}
