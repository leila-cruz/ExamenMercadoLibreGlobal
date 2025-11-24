package org.example.service;

import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class MutantDetector {

    private static final int SEQUENCE_LENGTH = 4;
    // Uso de Set para validación O(1)
    private static final Set<Character> VALID_BASES = Set.of('A', 'T', 'C', 'G');

    public boolean isMutant(String[] dna) {
        if (!isValidDna(dna)) return false;

        final int n = dna.length;
        int sequenceCount = 0;

        // Optimización: Convertir a char[][] una sola vez (Ahorro 20% tiempo)
        char[][] matrix = new char[n][];
        for (int i = 0; i < n; i++) {
            matrix[i] = dna[i].toCharArray();
        }

        // Recorremos la matriz UNA sola vez
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {

                // Solo buscamos si la celda actual NO es parte de una secuencia ya contada
                // Esto reemplaza la lógica compleja de "neighborDifferent" de la V1

                // 1. Horizontal (→)
                if (col <= n - SEQUENCE_LENGTH) {
                    if (checkHorizontal(matrix, row, col) && !isPreviousHorizontalEqual(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true; // Early Termination 🚀
                    }
                }

                // 2. Vertical (↓)
                if (row <= n - SEQUENCE_LENGTH) {
                    if (checkVertical(matrix, row, col) && !isPreviousVerticalEqual(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true;
                    }
                }

                // 3. Diagonal Descendente (↘)
                if (row <= n - SEQUENCE_LENGTH && col <= n - SEQUENCE_LENGTH) {
                    if (checkDiagonalDesc(matrix, row, col) && !isPreviousDiagonalDescEqual(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true;
                    }
                }

                // 4. Diagonal Ascendente (↗)
                if (row >= SEQUENCE_LENGTH - 1 && col <= n - SEQUENCE_LENGTH) {
                    if (checkDiagonalAsc(matrix, row, col) && !isPreviousDiagonalAscEqual(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true;
                    }
                }
            }
        }
        return false;
    }

    // --- Validaciones ---

    private boolean isValidDna(String[] dna) {
        if (dna == null || dna.length == 0) return false;
        final int n = dna.length;
        if (n < SEQUENCE_LENGTH) return false; // Matriz muy chica para tener secuencias

        for (String row : dna) {
            if (row == null || row.length() != n) return false; // No es cuadrada
            for (char c : row.toCharArray()) {
                if (!VALID_BASES.contains(c)) return false; // Carácter inválido
            }
        }
        return true;
    }

    // --- Métodos de Verificación (Direct Comparison) ---

    private boolean checkHorizontal(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        return matrix[row][col + 1] == base &&
                matrix[row][col + 2] == base &&
                matrix[row][col + 3] == base;
    }

    private boolean checkVertical(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        return matrix[row + 1][col] == base &&
                matrix[row + 2][col] == base &&
                matrix[row + 3][col] == base;
    }

    private boolean checkDiagonalDesc(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        return matrix[row + 1][col + 1] == base &&
                matrix[row + 2][col + 2] == base &&
                matrix[row + 3][col + 3] == base;
    }

    private boolean checkDiagonalAsc(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        return matrix[row - 1][col + 1] == base &&
                matrix[row - 2][col + 2] == base &&
                matrix[row - 3][col + 3] == base;
    }

    // --- Prevención de Solapamiento (AAAAA cuenta como 1) ---
    // Verifica si la celda anterior era igual. Si es así, significa que ya contamos
    // esta secuencia en la iteración anterior, por lo que la ignoramos ahora.

    private boolean isPreviousHorizontalEqual(char[][] matrix, int row, int col) {
        return col > 0 && matrix[row][col] == matrix[row][col - 1];
    }

    private boolean isPreviousVerticalEqual(char[][] matrix, int row, int col) {
        return row > 0 && matrix[row][col] == matrix[row - 1][col];
    }

    private boolean isPreviousDiagonalDescEqual(char[][] matrix, int row, int col) {
        return row > 0 && col > 0 && matrix[row][col] == matrix[row - 1][col - 1];
    }

    private boolean isPreviousDiagonalAscEqual(char[][] matrix, int row, int col) {
        return row < matrix.length - 1 && col > 0 && matrix[row][col] == matrix[row + 1][col - 1];
    }
}