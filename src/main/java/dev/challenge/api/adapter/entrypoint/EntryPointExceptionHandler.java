package dev.challenge.api.adapter.entrypoint;

import dev.challenge.api.exception.DomainRuleException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class EntryPointExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<List<Map<String, String>>> handleValidationException(MethodArgumentNotValidException e) {
    List<Map<String, String>> errors = e.getBindingResult().getFieldErrors().stream()
        .map(fieldError -> {
          Map<String, String> error = new HashMap<>();
          error.put("Field", fieldError.getField());
          error.put("Message", fieldError.getDefaultMessage());
          return error;
        })
        .collect(Collectors.toList());

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(DomainRuleException.class)
  public ResponseEntity<Map<String, String>> handleBusinessException(DomainRuleException e) {
    Map<String, String> error = Map.of("Message", e.getMessage());
    return ResponseEntity.badRequest().body(error);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleException(Exception e) {
    return new ResponseEntity<>("Erro ao processar a solicitação: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
