package dev.challenge.api.adapter.entrypoint.dto.customer;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CheckCustomerPasswordDtoTest {

  private static Validator validator;

  @BeforeAll
  static void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void testValidCheckCustomerPasswordDto() {
    // Arrange
    CheckCustomerPasswordDto checkCustomerPasswordDto = CheckCustomerPasswordDto.builder()
        .id(1L)
        .password("ValidPassword123!")
        .build();

    // Act
    Set<ConstraintViolation<CheckCustomerPasswordDto>> violations = validator.validate(checkCustomerPasswordDto);

    // Assert
    assertThat(violations).isEmpty();
  }

  @Test
  void testInvalidCheckCustomerPasswordDto() {
    // Arrange
    CheckCustomerPasswordDto checkCustomerPasswordDto = CheckCustomerPasswordDto.builder()
        .id(null)
        .password("") // Blank password
        .build();

    // Act
    Set<ConstraintViolation<CheckCustomerPasswordDto>> violations = validator.validate(checkCustomerPasswordDto);

    // Assert
    assertThat(violations).hasSize(1); // Number of expected violations
  }

  @Test
  void testGettersAndSetters() {
    // Arrange
    CheckCustomerPasswordDto checkCustomerPasswordDto = new CheckCustomerPasswordDto();

    // Act
    checkCustomerPasswordDto.setId(1L);
    checkCustomerPasswordDto.setPassword("ValidPassword123!");

    // Assert
    assertThat(checkCustomerPasswordDto.getId()).isEqualTo(1L);
    assertThat(checkCustomerPasswordDto.getPassword()).isEqualTo("ValidPassword123!");
  }

  @Test
  void testNoArgsConstructor() {
    // Act
    CheckCustomerPasswordDto checkCustomerPasswordDto = new CheckCustomerPasswordDto();

    // Assert
    assertThat(checkCustomerPasswordDto).isNotNull();
  }

  @Test
  void testAllArgsConstructor() {
    // Arrange
    CheckCustomerPasswordDto checkCustomerPasswordDto = new CheckCustomerPasswordDto(
        1L,
        "ValidPassword123!"
    );

    // Assert
    assertThat(checkCustomerPasswordDto).isNotNull();
    assertThat(checkCustomerPasswordDto.getId()).isEqualTo(1L);
    assertThat(checkCustomerPasswordDto.getPassword()).isEqualTo("ValidPassword123!");
  }
}
