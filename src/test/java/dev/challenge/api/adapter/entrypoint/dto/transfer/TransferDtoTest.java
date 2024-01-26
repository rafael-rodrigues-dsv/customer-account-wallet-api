package dev.challenge.api.adapter.entrypoint.dto.transfer;

import dev.challenge.api.adapter.entrypoint.dto.customeraccount.CustomerAccountDto;
import dev.challenge.api.adapter.entrypoint.dto.transaction.TransactionDto;
import dev.challenge.api.domain.enumeration.TransferStatusEnum;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class TransferDtoTest {

  @Test
  void testBuilder() {
    // Arrange
    CustomerAccountDto debitAccountDto = CustomerAccountDto.builder()
        .id(1L)
        .agency("123")
        .accountNumber("456")
        .build();

    CustomerAccountDto creditAccountDto = CustomerAccountDto.builder()
        .id(2L)
        .agency("789")
        .accountNumber("012")
        .build();

    Set<TransactionDto> transactions = new HashSet<>();

    TransferDto transferDto = TransferDto.builder()
        .id(1L)
        .debitAccount(debitAccountDto)
        .creditAccount(creditAccountDto)
        .amount(BigDecimal.TEN)
        .transferDate(LocalDateTime.now())
        .transferStatus(TransferStatusEnum.COMPLETED)
        .transactions(transactions)
        .build();

    // Assert
    assertThat(transferDto.getId()).isEqualTo(1L);
    assertThat(transferDto.getDebitAccount()).isEqualTo(debitAccountDto);
    assertThat(transferDto.getCreditAccount()).isEqualTo(creditAccountDto);
    assertThat(transferDto.getAmount()).isEqualTo(BigDecimal.TEN);
    assertThat(transferDto.getTransferDate()).isNotNull();
    assertThat(transferDto.getTransferStatus()).isEqualTo(TransferStatusEnum.COMPLETED);
    assertThat(transferDto.getTransactions()).isEqualTo(transactions);
  }

  @Test
  void testGettersAndSetters() {
    // Arrange
    CustomerAccountDto debitAccountDto = CustomerAccountDto.builder()
        .id(1L)
        .agency("123")
        .accountNumber("456")
        .build();

    CustomerAccountDto creditAccountDto = CustomerAccountDto.builder()
        .id(2L)
        .agency("789")
        .accountNumber("012")
        .build();

    Set<TransactionDto> transactions = new HashSet<>();

    TransferDto transferDto = new TransferDto();

    transferDto.setId(1L);
    transferDto.setDebitAccount(debitAccountDto);
    transferDto.setCreditAccount(creditAccountDto);
    transferDto.setAmount(BigDecimal.TEN);
    transferDto.setTransferDate(LocalDateTime.now());
    transferDto.setTransferStatus(TransferStatusEnum.COMPLETED);
    transferDto.setTransactions(transactions);

    // Assert
    assertThat(transferDto.getId()).isEqualTo(1L);
    assertThat(transferDto.getDebitAccount()).isEqualTo(debitAccountDto);
    assertThat(transferDto.getCreditAccount()).isEqualTo(creditAccountDto);
    assertThat(transferDto.getAmount()).isEqualTo(BigDecimal.TEN);
    assertThat(transferDto.getTransferDate()).isNotNull();
    assertThat(transferDto.getTransferStatus()).isEqualTo(TransferStatusEnum.COMPLETED);
    assertThat(transferDto.getTransactions()).isEqualTo(transactions);
  }

  @Test
  void testNoArgsConstructor() {
    // Act
    TransferDto transferDto = new TransferDto();

    // Assert
    assertThat(transferDto).isNotNull();
  }

  @Test
  void testAllArgsConstructor() {
    // Arrange
    CustomerAccountDto debitAccountDto = CustomerAccountDto.builder()
        .id(1L)
        .agency("123")
        .accountNumber("456")
        .build();

    CustomerAccountDto creditAccountDto = CustomerAccountDto.builder()
        .id(2L)
        .agency("789")
        .accountNumber("012")
        .build();

    Set<TransactionDto> transactions = new HashSet<>();

    TransferDto transferDto = new TransferDto(
        1L, debitAccountDto, creditAccountDto, BigDecimal.TEN,
        LocalDateTime.now(), TransferStatusEnum.COMPLETED, transactions);

    // Assert
    assertThat(transferDto).isNotNull();
    assertThat(transferDto.getId()).isEqualTo(1L);
    assertThat(transferDto.getDebitAccount()).isEqualTo(debitAccountDto);
    assertThat(transferDto.getCreditAccount()).isEqualTo(creditAccountDto);
    assertThat(transferDto.getAmount()).isEqualTo(BigDecimal.TEN);
    assertThat(transferDto.getTransferDate()).isNotNull();
    assertThat(transferDto.getTransferStatus()).isEqualTo(TransferStatusEnum.COMPLETED);
    assertThat(transferDto.getTransactions()).isEqualTo(transactions);
  }
}
