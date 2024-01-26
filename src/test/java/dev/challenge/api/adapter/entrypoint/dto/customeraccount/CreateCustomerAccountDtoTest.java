package dev.challenge.api.adapter.entrypoint.dto.customeraccount;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CreateCustomerAccountDtoTest {

  private static Validator validator;

  @BeforeAll
  static void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void testValidCreateCustomerAccountDto() {
    // Arrange
    CreateCustomerAccountDto createCustomerAccountDto = CreateCustomerAccountDto.builder()
        .customerId(1L)
        .agency("123ABC")
        .accountNumber("789XYZ")
        .build();

    // Act
    Set<ConstraintViolation<CreateCustomerAccountDto>> violations = validator.validate(createCustomerAccountDto);

    // Assert
    assertThat(violations).isEmpty();
  }

  @Test
  void testInvalidCreateCustomerAccountDto() {
    // Arrange
    CreateCustomerAccountDto createCustomerAccountDto = CreateCustomerAccountDto.builder()
        .customerId(null) // Null customerId
        .agency("") // Blank agency
        .accountNumber("invalid_account_number") // Invalid account number format
        .build();

    // Act
    Set<ConstraintViolation<CreateCustomerAccountDto>> violations = validator.validate(createCustomerAccountDto);

    // Assert
    assertThat(violations).hasSize(3); // Number of expected violations
  }

  @Test
  void testGettersAndSetters() {
    // Arrange
    CreateCustomerAccountDto createCustomerAccountDto = new CreateCustomerAccountDto();

    // Act
    createCustomerAccountDto.setCustomerId(1L);
    createCustomerAccountDto.setAgency("123ABC");
    createCustomerAccountDto.setAccountNumber("789XYZ");

    // Assert
    assertThat(createCustomerAccountDto.getCustomerId()).isEqualTo(1L);
    assertThat(createCustomerAccountDto.getAgency()).isEqualTo("123ABC");
    assertThat(createCustomerAccountDto.getAccountNumber()).isEqualTo("789XYZ");
  }

  @Test
  void testNoArgsConstructor() {
    // Act
    CreateCustomerAccountDto createCustomerAccountDto = new CreateCustomerAccountDto();

    // Assert
    assertThat(createCustomerAccountDto).isNotNull();
  }

  @Test
  void testAllArgsConstructor() {
    // Arrange
    CreateCustomerAccountDto createCustomerAccountDto = new CreateCustomerAccountDto(
        1L,
        "123ABC",
        "789XYZ"
    );

    // Assert
    assertThat(createCustomerAccountDto).isNotNull();
    assertThat(createCustomerAccountDto.getCustomerId()).isEqualTo(1L);
    assertThat(createCustomerAccountDto.getAgency()).isEqualTo("123ABC");
    assertThat(createCustomerAccountDto.getAccountNumber()).isEqualTo("789XYZ");
  }
}
