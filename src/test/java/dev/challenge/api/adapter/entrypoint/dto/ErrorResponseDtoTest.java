package dev.challenge.api.adapter.entrypoint.dto;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class ErrorResponseDtoTest {

  @Test
  void testBuilder() {
    // Arrange
    List<String> errors = Arrays.asList("Error 1", "Error 2");

    ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
        .errors(errors)
        .build();

    // Assert
    assertThat(errorResponseDto.getErrors()).isEqualTo(errors);
  }

  @Test
  void testGettersAndSetters() {
    // Arrange
    List<String> errors = Arrays.asList("Error 1", "Error 2");

    ErrorResponseDto errorResponseDto = new ErrorResponseDto();

    errorResponseDto.setErrors(errors);

    // Assert
    assertThat(errorResponseDto.getErrors()).isEqualTo(errors);
  }

  @Test
  void testNoArgsConstructor() {
    // Act
    ErrorResponseDto errorResponseDto = new ErrorResponseDto();

    // Assert
    assertThat(errorResponseDto).isNotNull();
    assertThat(errorResponseDto.getErrors()).isNull();
  }

  @Test
  void testAllArgsConstructor() {
    // Arrange
    List<String> errors = Arrays.asList("Error 1", "Error 2");

    ErrorResponseDto errorResponseDto = new ErrorResponseDto(errors);

    // Assert
    assertThat(errorResponseDto).isNotNull();
    assertThat(errorResponseDto.getErrors()).isEqualTo(errors);
  }
}
