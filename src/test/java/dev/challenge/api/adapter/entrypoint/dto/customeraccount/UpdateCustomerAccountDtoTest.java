package dev.challenge.api.adapter.entrypoint.dto.customeraccount;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UpdateCustomerAccountDtoTest {

  private static Validator validator;

  @BeforeAll
  static void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void testValidUpdateCustomerAccountDto() {
    // Arrange
    UpdateCustomerAccountDto updateCustomerAccountDto = UpdateCustomerAccountDto.builder()
        .id(1L)
        .customerId(2L)
        .agency("123ABC")
        .accountNumber("987XYZ")
        .build();

    // Act
    Set<ConstraintViolation<UpdateCustomerAccountDto>> violations = validator.validate(updateCustomerAccountDto);

    // Assert
    assertThat(violations).isEmpty();
  }

  @Test
  void testInvalidUpdateCustomerAccountDto() {
    // Arrange
    UpdateCustomerAccountDto updateCustomerAccountDto = UpdateCustomerAccountDto.builder()
        .id(null) // Null id
        .customerId(null) // Null customerId
        .agency("") // Blank agency
        .accountNumber("12345678901234567890") // Invalid length
        .build();

    // Act
    Set<ConstraintViolation<UpdateCustomerAccountDto>> violations = validator.validate(updateCustomerAccountDto);

    // Assert
    assertThat(violations).hasSize(2); // Number of expected violations
  }

  @Test
  void testGettersAndSetters() {
    // Arrange
    UpdateCustomerAccountDto updateCustomerAccountDto = new UpdateCustomerAccountDto();

    // Act
    updateCustomerAccountDto.setId(1L);
    updateCustomerAccountDto.setCustomerId(2L);
    updateCustomerAccountDto.setAgency("123ABC");
    updateCustomerAccountDto.setAccountNumber("987XYZ");

    // Assert
    assertThat(updateCustomerAccountDto.getId()).isEqualTo(1L);
    assertThat(updateCustomerAccountDto.getCustomerId()).isEqualTo(2L);
    assertThat(updateCustomerAccountDto.getAgency()).isEqualTo("123ABC");
    assertThat(updateCustomerAccountDto.getAccountNumber()).isEqualTo("987XYZ");
  }

  @Test
  void testNoArgsConstructor() {
    // Act
    UpdateCustomerAccountDto updateCustomerAccountDto = new UpdateCustomerAccountDto();

    // Assert
    assertThat(updateCustomerAccountDto).isNotNull();
  }

  @Test
  void testAllArgsConstructor() {
    // Arrange
    UpdateCustomerAccountDto updateCustomerAccountDto = new UpdateCustomerAccountDto(1L, 2L, "123ABC", "987XYZ");

    // Assert
    assertThat(updateCustomerAccountDto).isNotNull();
    assertThat(updateCustomerAccountDto.getId()).isEqualTo(1L);
    assertThat(updateCustomerAccountDto.getCustomerId()).isEqualTo(2L);
    assertThat(updateCustomerAccountDto.getAgency()).isEqualTo("123ABC");
    assertThat(updateCustomerAccountDto.getAccountNumber()).isEqualTo("987XYZ");
  }
}
