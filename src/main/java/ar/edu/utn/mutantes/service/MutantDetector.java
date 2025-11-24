package ar.edu.utn.mutantes.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MutantDetector {

    private static final int SEQ = 4;

    public boolean isMutant(String[] dna) {
        log.debug("Analizando ADN de tamaño {}x{}", dna.length, dna[0].length());

        int count = 0;
        count += checkHorizontal(dna);
        count += checkVertical(dna);
        count += checkDiagonalRight(dna);
        count += checkDiagonalLeft(dna);

        log.debug("Secuencias encontradas: {}", count);
        return count >= 2;
    }

    // -------------------
    // HORIZONTAL
    // -------------------
    private int checkHorizontal(String[] dna) {
        int found = 0;

        for (int row = 0; row < dna.length; row++) {
            String line = dna[row];
            for (int col = 0; col <= line.length() - SEQ; col++) {

                if (isSequence(line.charAt(col), line, col)) {
                    found++;
                    log.debug("↔ Secuencia horizontal encontrada en fila {} col {}", row, col);
                }
            }
        }
        return found;
    }

    // -------------------
    // VERTICAL
    // -------------------
    private int checkVertical(String[] dna) {
        int found = 0;

        for (int col = 0; col < dna.length; col++) {
            for (int row = 0; row <= dna.length - SEQ; row++) {

                char c = dna[row].charAt(col);

                if (dna[row + 1].charAt(col) == c &&
                        dna[row + 2].charAt(col) == c &&
                        dna[row + 3].charAt(col) == c) {

                    found++;
                    log.debug("↕ Secuencia vertical encontrada en fila {} col {}", row, col);
                }
            }
        }
        return found;
    }

    // -------------------
    // DIAGONAL DERECHA
    // -------------------
    private int checkDiagonalRight(String[] dna) {
        int found = 0;

        for (int row = 0; row <= dna.length - SEQ; row++) {
            for (int col = 0; col <= dna.length - SEQ; col++) {

                char c = dna[row].charAt(col);

                if (dna[row + 1].charAt(col + 1) == c &&
                        dna[row + 2].charAt(col + 2) == c &&
                        dna[row + 3].charAt(col + 3) == c) {

                    found++;
                    log.debug("↘ Secuencia diagonal ↘ encontrada en fila {} col {}", row, col);
                }
            }
        }
        return found;
    }

    // -------------------
    // DIAGONAL IZQUIERDA
    // -------------------
    private int checkDiagonalLeft(String[] dna) {
        int found = 0;

        for (int row = 0; row <= dna.length - SEQ; row++) {
            for (int col = SEQ - 1; col < dna.length; col++) {

                char c = dna[row].charAt(col);

                if (dna[row + 1].charAt(col - 1) == c &&
                        dna[row + 2].charAt(col - 2) == c &&
                        dna[row + 3].charAt(col - 3) == c) {

                    found++;
                    log.debug("↙ Secuencia diagonal ↙ encontrada en fila {} col {}", row, col);
                }
            }
        }
        return found;
    }

    // -------------------
    // Helper horizontal
    // -------------------
    private boolean isSequence(char c, String line, int col) {
        return line.charAt(col + 1) == c &&
                line.charAt(col + 2) == c &&
                line.charAt(col + 3) == c;
    }
}
