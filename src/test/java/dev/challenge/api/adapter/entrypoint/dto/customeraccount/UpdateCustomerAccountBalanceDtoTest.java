package dev.challenge.api.adapter.entrypoint.dto.customeraccount;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UpdateCustomerAccountBalanceDtoTest {

  private static Validator validator;

  @BeforeAll
  static void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void testValidUpdateCustomerAccountBalanceDto() {
    // Arrange
    UpdateCustomerAccountBalanceDto updateCustomerAccountBalanceDto = UpdateCustomerAccountBalanceDto.builder()
        .id(1L)
        .customerId(1L)
        .balance(BigDecimal.TEN)
        .build();

    // Act
    Set<ConstraintViolation<UpdateCustomerAccountBalanceDto>> violations = validator.validate(updateCustomerAccountBalanceDto);

    // Assert
    assertThat(violations).isEmpty();
  }

  @Test
  void testInvalidUpdateCustomerAccountBalanceDto() {
    // Arrange
    UpdateCustomerAccountBalanceDto createCustomerAccountDto = UpdateCustomerAccountBalanceDto.builder()
        .id(null)
        .customerId(null)
        .balance(null)
        .build();

    // Act
    Set<ConstraintViolation<UpdateCustomerAccountBalanceDto>> violations = validator.validate(createCustomerAccountDto);

    // Assert
    assertThat(violations).hasSize(1); // Number of expected violations
  }

  @Test
  void testGettersAndSetters() {
    // Arrange
    UpdateCustomerAccountBalanceDto updateCustomerAccountBalanceDto = new UpdateCustomerAccountBalanceDto();

    // Act
    updateCustomerAccountBalanceDto.setId(1L);
    updateCustomerAccountBalanceDto.setCustomerId(1L);
    updateCustomerAccountBalanceDto.setBalance(BigDecimal.TEN);

    // Assert
    assertThat(updateCustomerAccountBalanceDto.getId()).isEqualTo(1L);
    assertThat(updateCustomerAccountBalanceDto.getCustomerId()).isEqualTo(1L);
    assertThat(updateCustomerAccountBalanceDto.getBalance()).isEqualTo(BigDecimal.TEN);
  }

  @Test
  void testNoArgsConstructor() {
    // Act
    UpdateCustomerAccountBalanceDto createCustomerAccountDto = new UpdateCustomerAccountBalanceDto();

    // Assert
    assertThat(createCustomerAccountDto).isNotNull();
  }

  @Test
  void testAllArgsConstructor() {
    // Arrange
    UpdateCustomerAccountBalanceDto createCustomerAccountDto = new UpdateCustomerAccountBalanceDto(
        1L,
        1L,
        BigDecimal.TEN
    );

    // Assert
    assertThat(createCustomerAccountDto).isNotNull();
    assertThat(createCustomerAccountDto.getId()).isEqualTo(1L);
    assertThat(createCustomerAccountDto.getCustomerId()).isEqualTo(1L);
    assertThat(createCustomerAccountDto.getBalance()).isEqualTo(BigDecimal.TEN);
  }
}
