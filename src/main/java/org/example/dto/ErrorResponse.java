package org.example.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder // <--- Nos ayuda a construir el objeto más fácil
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Detalle del error en la operación")
public class ErrorResponse {

    @Schema(description = "Fecha y hora del error", example = "2025-11-24T10:30:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    @Schema(description = "Código de estado HTTP", example = "400")
    private int status;

    @Schema(description = "Tipo de error", example = "Bad Request")
    private String error;

    @Schema(description = "Mensaje detallado", example = "La secuencia de ADN contiene caracteres inválidos")
    private String message;

    @Schema(description = "Ruta de la solicitud", example = "/mutant/")
    private String path;
}