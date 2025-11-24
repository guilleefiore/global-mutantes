package ar.edu.utn.mutantes.validator;

import ar.edu.utn.mutantes.exception.InvalidDnaException;
import org.springframework.stereotype.Component;

@Component
public class DnaValidator {

    public boolean isValidDna(String[] dna) {

        // 1. Validar nulo o vacío
        if (dna == null) {
            throw new InvalidDnaException("El ADN no puede ser null.");
        }

        if (dna.length == 0) {
            throw new InvalidDnaException("El ADN no debe estar vacío.");
        }

        int n = dna.length;

        // 2. Validar cada fila
        for (String row : dna) {

            if (row == null) {
                throw new InvalidDnaException("Ninguna fila del ADN puede ser null.");
            }

            if (row.length() != n) {
                throw new InvalidDnaException(
                        "El ADN debe ser una matriz NxN. Se detectó una fila con longitud incorrecta."
                );
            }

            // 3. Validar caracteres
            for (char c : row.toCharArray()) {
                if (c != 'A' && c != 'T' && c != 'C' && c != 'G') {

                    throw new InvalidDnaException(
                            "El ADN contiene caracteres inválidos. Solo se permiten A, T, C y G. Encontrado: '" + c + "'"
                    );
                }
            }
        }

        return true;
    }
}
