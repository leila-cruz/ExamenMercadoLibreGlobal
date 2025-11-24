package org.example.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MutantDetectorTest {

    private MutantDetector detector;

    @BeforeEach
    void setUp() {
        detector = new MutantDetector();
    }

    @Test
    @DisplayName("MUTANTE: Detecta múltiples secuencias horizontales")
    void testMutantHorizontal() {
        String[] dna = {
                "AAAA",   // Secuencia 1
                "CCCC",   // Secuencia 2
                "TCAG",
                "GGTC"
        };
        assertTrue(detector.isMutant(dna));
    }
    @Test
    @DisplayName("MUTANTE: Detecta múltiples secuencias verticales")
    void testMutantVertical() {
        String[] dna = {"ATGC", "ATGC", "ATGC", "ATGC"};
        assertTrue(detector.isMutant(dna));
    }

    @Test
    @DisplayName("MUTANTE: Detecta secuencias diagonales")
    void testMutantDiagonals() {
        String[] dna = {"AGGT", "GATG", "GTAG", "TGGA"};
        assertTrue(detector.isMutant(dna));
    }

    @Test
    @DisplayName("MUTANTE: Caso mixto (1 Horizontal + 1 Vertical)")
    void testMutantMixed() {
        String[] dna = {
                "AAAAGT", // Horizontal AAAA
                "CCCCGT",
                "GTTGGT",
                "CTTGGT", // Vertical G en col 4
                "ATCGGT",
                "TCCGGT"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    @DisplayName("HUMANO: Sin secuencias")
    void testHumanNoSequence() {
        String[] dna = {"ATGC", "CAGT", "TGCA", "GCAT"};
        assertFalse(detector.isMutant(dna));
    }
    @Test
    @DisplayName("HUMANO: Con SOLO UNA secuencia (Necesita > 1)")
    void testHumanOneSequence() {
        String[] dna = {
                "AAAA", // 1 secuencia
                "CAGT",
                "TGCA",
                "GCAT"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    @DisplayName("ERROR: Retorna false si el ADN es null")
    void testNullDna() {
        assertFalse(detector.isMutant(null));
    }

    @Test
    @DisplayName("ERROR: Retorna false si el array está vacío")
    void testEmptyDna() {
        assertFalse(detector.isMutant(new String[]{}));
    }

    @Test
    @DisplayName("ERROR: Retorna false si la matriz no es cuadrada (NxM)")
    void testNonSquare() {
        String[] dna = {
                "ABCD",
                "EFGH" // 2x4
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    @DisplayName("ERROR: Retorna false con caracteres inválidos")
    void testInvalidCharacters() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TTAT",
                "AGAX" // X no es válido
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    @DisplayName("ERROR: Retorna false si la matriz es muy pequeña (<4x4)")
    void testSmallMatrix() {
        String[] dna = {
                "ATG",
                "CAG",
                "TTA"
        };
        assertFalse(detector.isMutant(dna));
    }


    @Test
    @DisplayName("LÓGICA: No debe contar solapamientos")
    void testOverlappingSequence() {
        String[] dna = {"AAAAA", "CCGGC", "GGCCG", "CCGGC", "GGCCG"};
        assertFalse(detector.isMutant(dna));
    }

    @Test
    @DisplayName("LÓGICA: Cruce de secuencias (Comparten letra)")
    void testCrossSequences() {
        // Horizontal y Vertical compartiendo la esquina (0,0)
        String[] fullDna = {
                "AAAAGT", // Horizontal AAAA
                "ACCCCT", // Vertical A en col 0
                "AGGGGT",
                "ATTTTT",
                "ACCCCA",
                "AGGGGA"
        };
        assertTrue(detector.isMutant(fullDna));
    }

    @Test
    @DisplayName("PERFORMANCE: Matriz Gigante 1000x1000 con Early Termination")
    void testLargeMatrixPerformance() {
        int n = 1000;
        String[] giantDna = new String[n];
        // Llenamos de T (Humano)
        String row = "T".repeat(n);
        Arrays.fill(giantDna, row);

        // Ponemos 2 secuencias al principio para que corte rápido
        giantDna[0] = "AAAA" + "T".repeat(n - 4);
        giantDna[1] = "CCCC" + "T".repeat(n - 4);

        long start = System.currentTimeMillis();
        boolean isMutant = detector.isMutant(giantDna);
        long end = System.currentTimeMillis();

        assertTrue(isMutant);

        System.out.println("Tiempo de ejecución 1000x1000: " + (end - start) + "ms");
        assertTrue((end - start) < 500, "El algoritmo es lento, falló Early Termination");
    }
}