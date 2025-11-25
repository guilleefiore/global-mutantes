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

    // ============================================================
    // MUTANTES: Horizontales / Verticales / Diagonales
    // ============================================================

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
                "AAGCGA",
                "AAGTGC",
                "AATAGT",
                "AATAGG",
                "AATCTA",
                "AACCTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testMutantDiagonal() {
        String[] dna = {
                "ATGCGA",
                "CAGAGA",
                "TTAGGT",
                "AGGAAG",
                "CGCATA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testMutantDiagonalLeft() {
        String[] dna = {
                "ATGAGA",
                "CAGAGA",
                "TTATGA",
                "AGAAAG",
                "CGCATA",
                "TCACTA"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testMutantMultipleDirections() {
        String[] dna = {
                "AAAAGA",
                "CAGTGA",
                "TCATGA",
                "AGTAGG",
                "CGCCTA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testVerticalOnRightBorder() {
        String[] dna = {
                "ATGCAA",
                "CTGCAA",
                "TTGCAA",
                "AGGCAA",
                "CCGCAA",
                "TCGCAA"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testDiagonalRightOnLowerLeftCorner() {
        String[] dna = {
                "ATGCGA",
                "CAGTGA",
                "TTAAGT",  // T en (2,0)
                "ATTAAG",  // T en (3,1)
                "CCTTTA",  // T en (4,2)
                "GACTTG"   // T en (5,3)
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testDiagonalLeftOnLowerRightCorner() {
        String[] dna = {
                "ATGCGA",
                "CAGTGA",
                "TCATGA",
                "AGTAGA",
                "CGCATA",
                "TCAAAG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testMinimumMatrixMutant4x4() {
        String[] dna = {
                "AAAA",
                "CCCC",
                "TTAT",
                "AGAC"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testOverlappingSequences() {
        String[] dna = {
                "AAAAA",
                "AAAAG",
                "TTATG",
                "AGAAA",
                "CCCCT"
        };
        assertTrue(detector.isMutant(dna));
    }

    // ============================================================
    // HUMANOS (NO MUTANTES)
    // ============================================================

    @Test
    void testHuman() {
        String[] dna = {
                "ATGCAT",
                "CAGTGA",
                "TTATGT",
                "AGCTGG",
                "CGTCTA",
                "TCACTG"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testSingleSequenceIsNotMutant() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAAG", // solo 1 secuencia horizontal
                "CACCTA",
                "TCACTG"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testInvalidMatrixNxM() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TTAT" // 3x4 → no mutante
        };
        assertFalse(detector.isMutant(dna));
    }
}
