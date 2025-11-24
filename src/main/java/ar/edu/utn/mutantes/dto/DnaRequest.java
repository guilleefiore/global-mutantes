package ar.edu.utn.mutantes.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DnaRequest {

    @NotNull(message = "El campo dna no puede ser nulo")
    @NotEmpty(message = "El campo dna no puede estar vacío")
    private String[] dna;
}
