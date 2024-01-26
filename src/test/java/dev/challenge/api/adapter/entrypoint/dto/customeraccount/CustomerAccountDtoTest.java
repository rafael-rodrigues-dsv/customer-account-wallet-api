package dev.challenge.api.adapter.entrypoint.dto.customeraccount;

import dev.challenge.api.domain.enumeration.CustomerAccountStatusEnum;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerAccountDtoTest {

  @Test
  void testBuilder() {
    // Arrange
    CustomerAccountDto customerAccountDto = CustomerAccountDto.builder()
        .id(1L)
        .agency("123ABC")
        .accountNumber("987XYZ")
        .balance(BigDecimal.ZERO)
        .isDefault(true)
        .accountStatus(CustomerAccountStatusEnum.ACTIVE)
        .build();

    // Assert
    assertThat(customerAccountDto.getId()).isEqualTo(1L);
    assertThat(customerAccountDto.getAgency()).isEqualTo("123ABC");
    assertThat(customerAccountDto.getAccountNumber()).isEqualTo("987XYZ");
    assertThat(customerAccountDto.getBalance()).isEqualTo(BigDecimal.ZERO);
    assertThat(customerAccountDto.getIsDefault()).isEqualTo(true);
    assertThat(customerAccountDto.getAccountStatus()).isEqualTo(CustomerAccountStatusEnum.ACTIVE);
  }

  @Test
  void testGettersAndSetters() {
    // Arrange
    CustomerAccountDto customerAccountDto = new CustomerAccountDto();

    // Act
    customerAccountDto.setId(1L);
    customerAccountDto.setAgency("123ABC");
    customerAccountDto.setAccountNumber("987XYZ");
    customerAccountDto.setBalance(BigDecimal.ZERO);
    customerAccountDto.setIsDefault(true);
    customerAccountDto.setAccountStatus(CustomerAccountStatusEnum.ACTIVE);

    // Assert
    assertThat(customerAccountDto.getId()).isEqualTo(1L);
    assertThat(customerAccountDto.getAgency()).isEqualTo("123ABC");
    assertThat(customerAccountDto.getAccountNumber()).isEqualTo("987XYZ");
    assertThat(customerAccountDto.getBalance()).isEqualTo(BigDecimal.ZERO);
    assertThat(customerAccountDto.getIsDefault()).isEqualTo(true);
    assertThat(customerAccountDto.getAccountStatus()).isEqualTo(CustomerAccountStatusEnum.ACTIVE);
  }

  @Test
  void testNoArgsConstructor() {
    // Act
    CustomerAccountDto customerAccountDto = new CustomerAccountDto();

    // Assert
    assertThat(customerAccountDto).isNotNull();
  }

  @Test
  void testAllArgsConstructor() {
    // Arrange
    CustomerAccountDto customerAccountDto = new CustomerAccountDto(
        1L, "123ABC", "987XYZ", BigDecimal.ZERO, true, CustomerAccountStatusEnum.ACTIVE);

    // Assert
    assertThat(customerAccountDto).isNotNull();
    assertThat(customerAccountDto.getId()).isEqualTo(1L);
    assertThat(customerAccountDto.getAgency()).isEqualTo("123ABC");
    assertThat(customerAccountDto.getAccountNumber()).isEqualTo("987XYZ");
    assertThat(customerAccountDto.getBalance()).isEqualTo(BigDecimal.ZERO);
    assertThat(customerAccountDto.getIsDefault()).isEqualTo(true);
    assertThat(customerAccountDto.getAccountStatus()).isEqualTo(CustomerAccountStatusEnum.ACTIVE);
  }
}
