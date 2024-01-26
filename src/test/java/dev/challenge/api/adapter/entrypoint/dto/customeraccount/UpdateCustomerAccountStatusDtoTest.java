package dev.challenge.api.adapter.entrypoint.dto.customeraccount;

import dev.challenge.api.domain.enumeration.CustomerAccountStatusEnum;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UpdateCustomerAccountStatusDtoTest {

  private static Validator validator;

  @BeforeAll
  static void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void testValidUpdateCustomerAccountStatusDto() {
    // Arrange
    UpdateCustomerAccountStatusDto updateCustomerAccountBalanceDto = UpdateCustomerAccountStatusDto.builder()
        .id(1L)
        .customerId(1L)
        .accountStatus(CustomerAccountStatusEnum.ACTIVE)
        .build();

    // Act
    Set<ConstraintViolation<UpdateCustomerAccountStatusDto>> violations = validator.validate(updateCustomerAccountBalanceDto);

    // Assert
    assertThat(violations).isEmpty();
  }

  @Test
  void testInvalidUpdateCustomerAccountStatusDto() {
    // Arrange
    UpdateCustomerAccountStatusDto createCustomerAccountDto = UpdateCustomerAccountStatusDto.builder()
        .id(null)
        .customerId(null)
        .accountStatus(null)
        .build();

    // Act
    Set<ConstraintViolation<UpdateCustomerAccountStatusDto>> violations = validator.validate(createCustomerAccountDto);

    // Assert
    assertThat(violations).hasSize(1); // Number of expected violations
  }

  @Test
  void testGettersAndSetters() {
    // Arrange
    UpdateCustomerAccountStatusDto updateCustomerAccountStatusDto = new UpdateCustomerAccountStatusDto();

    // Act
    updateCustomerAccountStatusDto.setId(1L);
    updateCustomerAccountStatusDto.setCustomerId(1L);
    updateCustomerAccountStatusDto.setAccountStatus(CustomerAccountStatusEnum.ACTIVE);

    // Assert
    assertThat(updateCustomerAccountStatusDto.getId()).isEqualTo(1L);
    assertThat(updateCustomerAccountStatusDto.getCustomerId()).isEqualTo(1L);
    assertThat(updateCustomerAccountStatusDto.getAccountStatus()).isEqualTo(CustomerAccountStatusEnum.ACTIVE);
  }

  @Test
  void testNoArgsConstructor() {
    // Act
    UpdateCustomerAccountStatusDto createCustomerAccountDto = new UpdateCustomerAccountStatusDto();

    // Assert
    assertThat(createCustomerAccountDto).isNotNull();
  }

  @Test
  void testAllArgsConstructor() {
    // Arrange
    UpdateCustomerAccountStatusDto updateCustomerAccountStatusDto = new UpdateCustomerAccountStatusDto(
        1L,
        1L,
        CustomerAccountStatusEnum.ACTIVE
    );

    // Assert
    assertThat(updateCustomerAccountStatusDto).isNotNull();
    assertThat(updateCustomerAccountStatusDto.getId()).isEqualTo(1L);
    assertThat(updateCustomerAccountStatusDto.getCustomerId()).isEqualTo(1L);
    assertThat(updateCustomerAccountStatusDto.getAccountStatus()).isEqualTo(CustomerAccountStatusEnum.ACTIVE);
  }

}