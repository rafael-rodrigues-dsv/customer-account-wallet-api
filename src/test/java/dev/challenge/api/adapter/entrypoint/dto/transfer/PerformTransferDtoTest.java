package dev.challenge.api.adapter.entrypoint.dto.transfer;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class PerformTransferDtoTest {

  private static Validator validator;

  @BeforeAll
  static void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void testValidPerformTransferDto() {
    // Arrange
    PerformTransferDto performTransferDto = PerformTransferDto.builder()
        .debitAccountId(1L)
        .creditAccountId(2L)
        .amount(new BigDecimal("100.00"))
        .build();

    // Act
    Set<ConstraintViolation<PerformTransferDto>> violations = validator.validate(performTransferDto);

    // Assert
    assertThat(violations).isEmpty();
  }

  @Test
  void testInvalidPerformTransferDto() {
    // Arrange
    PerformTransferDto performTransferDto = PerformTransferDto.builder()
        .debitAccountId(null) // Null debitAccountId
        .creditAccountId(null) // Null creditAccountId
        .amount(new BigDecimal("-100.00")) // Negative amount
        .build();

    // Act
    Set<ConstraintViolation<PerformTransferDto>> violations = validator.validate(performTransferDto);

    // Assert
    assertThat(violations).hasSize(3); // Number of expected violations
  }

  @Test
  void testGettersAndSetters() {
    // Arrange
    PerformTransferDto performTransferDto = new PerformTransferDto();

    // Act
    performTransferDto.setDebitAccountId(1L);
    performTransferDto.setCreditAccountId(2L);
    performTransferDto.setAmount(new BigDecimal("100.00"));

    // Assert
    assertThat(performTransferDto.getDebitAccountId()).isEqualTo(1L);
    assertThat(performTransferDto.getCreditAccountId()).isEqualTo(2L);
    assertThat(performTransferDto.getAmount()).isEqualTo(new BigDecimal("100.00"));
  }

  @Test
  void testNoArgsConstructor() {
    // Act
    PerformTransferDto performTransferDto = new PerformTransferDto();

    // Assert
    assertThat(performTransferDto).isNotNull();
  }

  @Test
  void testAllArgsConstructor() {
    // Arrange
    PerformTransferDto performTransferDto = new PerformTransferDto(1L, 2L, new BigDecimal("100.00"));

    // Assert
    assertThat(performTransferDto).isNotNull();
    assertThat(performTransferDto.getDebitAccountId()).isEqualTo(1L);
    assertThat(performTransferDto.getCreditAccountId()).isEqualTo(2L);
    assertThat(performTransferDto.getAmount()).isEqualTo(new BigDecimal("100.00"));
  }
}
