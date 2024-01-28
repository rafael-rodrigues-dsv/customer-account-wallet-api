package dev.challenge.api.adapter.entrypoint;

import dev.challenge.api.adapter.entrypoint.dto.ErrorResponseDto;
import dev.challenge.api.exception.DomainRuleException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class EntryPointExceptionHandlerTest {

  @Test
  void testHandleValidationException() {
    // Arrange
    BindingResult bindingResult = mock(BindingResult.class);  // Você precisa criar um mock para BindingResult
    MethodParameter methodParameter = mock(MethodParameter.class);  // Você precisa criar um mock para MethodParameter

    MethodArgumentNotValidException exception = new MethodArgumentNotValidException(
        methodParameter, bindingResult);

    // Act
    ResponseEntity<ErrorResponseDto> responseEntity = new EntryPointExceptionHandler().handleValidationException(exception);

    // Assert
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(responseEntity.getBody()).isNotNull();
  }


  @Test
  void testHandleBusinessException() {
    // Arrange
    DomainRuleException exception = new DomainRuleException("Test Business Exception");

    // Act
    ResponseEntity<ErrorResponseDto> responseEntity = new EntryPointExceptionHandler().handleBusinessException(exception);

    // Assert
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(responseEntity.getBody()).isNotNull();
  }

  @Test
  void testHandleEntityNotFoundException() {
    // Arrange
    EntityNotFoundException exception = new EntityNotFoundException("Test Entity Not Found Exception");

    // Act
    ResponseEntity<ErrorResponseDto> responseEntity = new EntryPointExceptionHandler().handleEntityNotFoundException(exception);

    // Assert
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(responseEntity.getBody()).isNotNull();
  }

  @Test
  void testHandleException() {
    // Arrange
    Exception exception = new Exception("Test Exception");

    // Act
    ResponseEntity<ErrorResponseDto> responseEntity = new EntryPointExceptionHandler().handleException(exception);

    // Assert
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    assertThat(responseEntity.getBody()).isNotNull();
  }
}
