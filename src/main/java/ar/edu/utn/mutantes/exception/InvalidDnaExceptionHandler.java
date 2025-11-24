package ar.edu.utn.mutantes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class InvalidDnaExceptionHandler {

    @ExceptionHandler(InvalidDnaException.class)
    public ResponseEntity<Object> handleInvalidDnaException(InvalidDnaException ex) {

        Map<String, Object> body = Map.of(
                "error", "Invalid DNA",
                "message", ex.getMessage(),
                "timestamp", LocalDateTime.now().toString()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
