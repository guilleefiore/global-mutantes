package ar.edu.utn.mutantes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ---------------------------------------------------------
    // 1. ADN inválido → 400 BAD REQUEST
    // ---------------------------------------------------------
    @ExceptionHandler(InvalidDnaException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidDna(InvalidDnaException ex) {

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("error", "Invalid DNA");
        body.put("message", ex.getMessage());   // puede ser null, y está ok

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body);
    }

    // ---------------------------------------------------------
    // 2. IllegalArgumentException → 400 BAD REQUEST
    // ---------------------------------------------------------
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("error", "Bad Request");
        body.put("message", ex.getMessage());  // puede ser null también

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body);
    }
}
