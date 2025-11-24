package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import org.example.validation.ValidDnaSequence;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para verificar si un ADN es mutante")
public class DnaRequest {

    @Schema(
            description = "Array de Strings que representa la secuencia de ADN (NxN)",
            example = "[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]"
    )
    @NotNull(message = "El ADN no puede ser null")
    @NotEmpty(message = "El ADN no puede estar vacío")
    @ValidDnaSequence
    private String[] dna;
}