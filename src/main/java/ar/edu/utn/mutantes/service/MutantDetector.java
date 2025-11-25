package ar.edu.utn.mutantes.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MutantDetector {

    private static final int SEQ = 4;

    public boolean isMutant(String[] dna) {
        int n = dna.length;

        // Convertir a matriz char[][] para máxima eficiencia
        char[][] m = new char[n][n];
        for (int i = 0; i < n; i++) {
            m[i] = dna[i].toCharArray();
        }

        int count = 0;

        // Recorremos TODA la matriz una sola vez (O(N²))
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {

                char c = m[row][col];

                // ----------------------------
                // 1. Horizontal →
                // ----------------------------
                if (col <= n - SEQ &&
                        c == m[row][col + 1] &&
                        c == m[row][col + 2] &&
                        c == m[row][col + 3]) {

                    count++;
                    log.debug("↔ Horizontal en ({},{})", row, col);
                    if (count >= 2) return true;
                }

                // ----------------------------
                // 2. Vertical ↓
                // ----------------------------
                if (row <= n - SEQ &&
                        c == m[row + 1][col] &&
                        c == m[row + 2][col] &&
                        c == m[row + 3][col]) {

                    count++;
                    log.debug("↕ Vertical en ({},{})", row, col);
                    if (count >= 2) return true;
                }

                // ----------------------------
                // 3. Diagonal derecha ↘
                // ----------------------------
                if (row <= n - SEQ && col <= n - SEQ &&
                        c == m[row + 1][col + 1] &&
                        c == m[row + 2][col + 2] &&
                        c == m[row + 3][col + 3]) {

                    count++;
                    log.debug("↘ Diagonal ↘ en ({},{})", row, col);
                    if (count >= 2) return true;
                }

                // ----------------------------
                // 4. Diagonal izquierda ↙
                // ----------------------------
                if (row <= n - SEQ && col >= SEQ - 1 &&
                        c == m[row + 1][col - 1] &&
                        c == m[row + 2][col - 2] &&
                        c == m[row + 3][col - 3]) {

                    count++;
                    log.debug("↙ Diagonal ↙ en ({},{})", row, col);
                    if (count >= 2) return true;
                }
            }
        }

        return false;
    }
}
