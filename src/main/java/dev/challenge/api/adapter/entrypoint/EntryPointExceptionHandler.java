package dev.challenge.api.adapter.entrypoint;

import dev.challenge.api.adapter.entrypoint.dto.ErrorResponseDto;
import dev.challenge.api.exception.DomainRuleException;
import jakarta.persistence.EntityNotFoundException;
import lombok.Generated;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class EntryPointExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException e) {
    List<String> errors = e.getBindingResult().getFieldErrors().stream()
        .map(fieldError -> fieldError.getDefaultMessage())
        .collect(Collectors.toList());

    return showMessageErrors(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(DomainRuleException.class)
  public ResponseEntity<ErrorResponseDto> handleBusinessException(DomainRuleException e) {
    return showMessageErrors(List.of(e.getMessage()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorResponseDto> handleEntityNotFoundException(EntityNotFoundException e) {
    return showMessageErrors(List.of(e.getMessage()), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponseDto> handleException(Exception e) {
    return showMessageErrors(List.of("Erro ao processar a solicitação: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ResponseEntity<ErrorResponseDto> showMessageErrors(List<String> errorList, HttpStatus statusCode) {
    ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
        .errors(errorList)
        .build();
    return new ResponseEntity<>(errorResponseDto, statusCode);
  }
}
