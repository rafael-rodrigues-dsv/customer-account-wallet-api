package dev.challenge.api.adapter.entrypoint.dto.transaction;

import dev.challenge.api.adapter.entrypoint.dto.customeraccount.CustomerAccountDto;
import dev.challenge.api.domain.enumeration.TransactionReasonEnum;
import dev.challenge.api.domain.enumeration.TransactionTypeEnum;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionDtoTest {

  @Test
  void testBuilder() {
    // Arrange
    CustomerAccountDto accountDto = CustomerAccountDto.builder()
        .id(1L)
        .agency("123")
        .accountNumber("456")
        .build();

    TransactionDto transactionDto = TransactionDto.builder()
        .id(1L)
        .account(accountDto)
        .amount(BigDecimal.TEN)
        .transactionDate(LocalDateTime.now())
        .transactionType(TransactionTypeEnum.CREDIT)
        .transactionReason(TransactionReasonEnum.TRANSFER)
        .build();

    // Assert
    assertThat(transactionDto.getId()).isEqualTo(1L);
    assertThat(transactionDto.getAccount()).isEqualTo(accountDto);
    assertThat(transactionDto.getAmount()).isEqualTo(BigDecimal.TEN);
    assertThat(transactionDto.getTransactionDate()).isNotNull();
    assertThat(transactionDto.getTransactionType()).isEqualTo(TransactionTypeEnum.CREDIT);
    assertThat(transactionDto.getTransactionReason()).isEqualTo(TransactionReasonEnum.TRANSFER);
  }

  @Test
  void testGettersAndSetters() {
    // Arrange
    CustomerAccountDto accountDto = CustomerAccountDto.builder()
        .id(1L)
        .agency("123")
        .accountNumber("456")
        .build();

    TransactionDto transactionDto = new TransactionDto();

    transactionDto.setId(1L);
    transactionDto.setAccount(accountDto);
    transactionDto.setAmount(BigDecimal.TEN);
    transactionDto.setTransactionDate(LocalDateTime.now());
    transactionDto.setTransactionType(TransactionTypeEnum.CREDIT);
    transactionDto.setTransactionReason(TransactionReasonEnum.TRANSFER);

    // Assert
    assertThat(transactionDto.getId()).isEqualTo(1L);
    assertThat(transactionDto.getAccount()).isEqualTo(accountDto);
    assertThat(transactionDto.getAmount()).isEqualTo(BigDecimal.TEN);
    assertThat(transactionDto.getTransactionDate()).isNotNull();
    assertThat(transactionDto.getTransactionType()).isEqualTo(TransactionTypeEnum.CREDIT);
    assertThat(transactionDto.getTransactionReason()).isEqualTo(TransactionReasonEnum.TRANSFER);
  }

  @Test
  void testNoArgsConstructor() {
    // Act
    TransactionDto transactionDto = new TransactionDto();

    // Assert
    assertThat(transactionDto).isNotNull();
  }

  @Test
  void testAllArgsConstructor() {
    // Arrange
    CustomerAccountDto accountDto = CustomerAccountDto.builder()
        .id(1L)
        .agency("123")
        .accountNumber("456")
        .build();

    TransactionDto transactionDto = new TransactionDto(
        1L, accountDto, BigDecimal.TEN, LocalDateTime.now(),
        TransactionTypeEnum.CREDIT, TransactionReasonEnum.TRANSFER);

    // Assert
    assertThat(transactionDto).isNotNull();
    assertThat(transactionDto.getId()).isEqualTo(1L);
    assertThat(transactionDto.getAccount()).isEqualTo(accountDto);
    assertThat(transactionDto.getAmount()).isEqualTo(BigDecimal.TEN);
    assertThat(transactionDto.getTransactionDate()).isNotNull();
    assertThat(transactionDto.getTransactionType()).isEqualTo(TransactionTypeEnum.CREDIT);
    assertThat(transactionDto.getTransactionReason()).isEqualTo(TransactionReasonEnum.TRANSFER);
  }
}
