package org.example.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j // 1. Agregamos Logging para ver errores en la consola de Render
public class GlobalExceptionHandler {

    /**
     * Maneja errores de validación (@Valid, @Validated)
     * Ejemplo: ADN con caracteres inválidos o vacío.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        // Recolectamos todos los mensajes de error de los campos
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        log.warn("Error de validación: {}", errorMessage); // Logueamos como advertencia

        return buildResponse(HttpStatus.BAD_REQUEST, errorMessage, request.getRequestURI());
    }

    /**
     * Maneja JSON mal formado o body vacío
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        log.warn("Error en lectura del mensaje HTTP: {}", ex.getMessage());
        return buildResponse(HttpStatus.BAD_REQUEST, "Malformed JSON request or missing body", request.getRequestURI());
    }

    /**
     * Maneja error custom al calcular hash
     */
    @ExceptionHandler(DnaHashCalculationException.class)
    public ResponseEntity<ErrorResponse> handleDnaHashCalculationException(
            DnaHashCalculationException ex,
            HttpServletRequest request) {

        log.error("Error calculando hash de ADN", ex); // Logueamos el error completo (Stacktrace)
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error processing DNA sequence", request.getRequestURI());
    }

    /**
     * Maneja cualquier otra excepción no capturada (Catch-all)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        // 3. Seguridad: Logueamos el error real PERO no se lo mostramos al usuario
        log.error("Error inesperado en el servidor", ex);

        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected internal error occurred", request.getRequestURI());
    }

    /**
     * Método auxiliar para construir la respuesta y no repetir código.
     * Usa el Builder que agregamos al DTO.
     */
    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String message, String path) {
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(path)
                .build();

        return new ResponseEntity<>(error, status);
    }
}