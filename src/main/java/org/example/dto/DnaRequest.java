package org.example.dto;

import lombok.Data; // Importar Lombok
import jakarta.validation.constraints.NotNull; // Validaciones
import jakarta.validation.constraints.NotEmpty;
import org.example.validation.ValidDnaSequence; // Tu validación custom

@Data // <--- ¡Esto genera automáticamente los Getters y Setters!
public class DnaRequest {

    @NotNull(message = "El ADN no puede ser null")
    @NotEmpty(message = "El ADN no puede estar vacío")
    @ValidDnaSequence // Validación de matriz cuadrada y caracteres
    private String[] dna;
}