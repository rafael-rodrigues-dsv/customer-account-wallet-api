package dev.challenge.api.adapter.entrypoint.dto.customer;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UpdateCustomerDtoTest {

  private static Validator validator;

  @BeforeAll
  static void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void testValidUpdateCustomerDto() {
    // Arrange
    UpdateCustomerDto updateCustomerDto = UpdateCustomerDto.builder()
        .id(1L)
        .name("John Doe")
        .email("john.doe@example.com")
        .phoneNumber("12345678901")
        .build();

    // Act
    Set<ConstraintViolation<UpdateCustomerDto>> violations = validator.validate(updateCustomerDto);

    // Assert
    assertThat(violations).isEmpty();
  }

  @Test
  void testInvalidUpdateCustomerDto() {
    // Arrange
    UpdateCustomerDto updateCustomerDto = UpdateCustomerDto.builder()
        .id(null)
        .name("") // Blank name
        .email("invalid-email") // Invalid email format
        .phoneNumber("123") // Invalid length
        .build();

    // Act
    Set<ConstraintViolation<UpdateCustomerDto>> violations = validator.validate(updateCustomerDto);

    // Assert
    assertThat(violations).hasSize(4); // Number of expected violations
  }

  @Test
  void testGettersAndSetters() {
    // Arrange
    UpdateCustomerDto updateCustomerDto = new UpdateCustomerDto();

    // Act
    updateCustomerDto.setId(1L);
    updateCustomerDto.setName("John Doe");
    updateCustomerDto.setEmail("john.doe@example.com");
    updateCustomerDto.setPhoneNumber("12345678901");

    // Assert
    assertThat(updateCustomerDto.getId()).isEqualTo(1L);
    assertThat(updateCustomerDto.getName()).isEqualTo("John Doe");
    assertThat(updateCustomerDto.getEmail()).isEqualTo("john.doe@example.com");
    assertThat(updateCustomerDto.getPhoneNumber()).isEqualTo("12345678901");
  }

  @Test
  void testNoArgsConstructor() {
    // Act
    UpdateCustomerDto updateCustomerDto = new UpdateCustomerDto();

    // Assert
    assertThat(updateCustomerDto).isNotNull();
  }

  @Test
  void testAllArgsConstructor() {
    // Arrange
    UpdateCustomerDto updateCustomerDto = new UpdateCustomerDto(
        1L,
        "John Doe",
        "john.doe@example.com",
        "12345678901"
    );

    // Assert
    assertThat(updateCustomerDto).isNotNull();
    assertThat(updateCustomerDto.getId()).isEqualTo(1L);
    assertThat(updateCustomerDto.getName()).isEqualTo("John Doe");
    assertThat(updateCustomerDto.getEmail()).isEqualTo("john.doe@example.com");
    assertThat(updateCustomerDto.getPhoneNumber()).isEqualTo("12345678901");
  }
}
