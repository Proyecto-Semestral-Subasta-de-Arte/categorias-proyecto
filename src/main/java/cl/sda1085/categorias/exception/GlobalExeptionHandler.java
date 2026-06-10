package cl.sda1085.categorias.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExeptionHandler {

    //Error 404: Recurso no encontrado
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("TIMESTAMP", LocalDateTime.now());
        error.put("ESTADO", HttpStatus.NOT_FOUND.value());
        error.put("ERROR", "Categoría no encontrada.");
        error.put("MENSAJE", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    //Error 400: Errores de validación (Nombre vacío, descripción muy larga, etc.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> respuesta = new HashMap<>();
        Map<String, String> errores = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String campo = ((FieldError) error).getField();
            String mensaje = error.getDefaultMessage();
            errores.put(campo, mensaje);
        });

        respuesta.put("TIMESTAMP", LocalDateTime.now());
        respuesta.put("ESTADO", HttpStatus.BAD_REQUEST.value());
        respuesta.put("ERRORES", errores);

        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }

    //Error 500: Error interno del servidor
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("TIMESTAMP", LocalDateTime.now());
        error.put("ESTADO", HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.put("ERROR", "Error interno en el microservicio de categorías.");
        error.put("MENSAJE", "Ocurrió un problema inesperado. Por favor, intente más tarde.");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
