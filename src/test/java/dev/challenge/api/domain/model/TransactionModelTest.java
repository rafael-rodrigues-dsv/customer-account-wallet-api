package dev.challenge.api.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.challenge.api.domain.enumeration.TransactionReasonEnum;
import dev.challenge.api.domain.enumeration.TransactionTypeEnum;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TransactionModelTest {

  @Test
  void testBuilder() {
    CustomerAccountModel account = CustomerAccountModel.builder().id(1L).build();
    TransferModel transfer = TransferModel.builder().id(2L).build();

    TransactionModel transaction = TransactionModel.builder()
        .id(1L)
        .account(account)
        .transfer(transfer)
        .amount(BigDecimal.TEN)
        .transactionDate(LocalDateTime.now())
        .transactionType(TransactionTypeEnum.CREDIT)
        .transactionReason(TransactionReasonEnum.TRANSFER)
        .build();

    assertNotNull(transaction);
    assertTransactionFields(transaction);
  }

  @Test
  void testGettersAndSetters() {
    CustomerAccountModel account = CustomerAccountModel.builder().id(1L).build();
    TransferModel transfer = TransferModel.builder().id(2L).build();

    TransactionModel transaction = new TransactionModel();
    transaction.setId(1L);
    transaction.setAccount(account);
    transaction.setTransfer(transfer);
    transaction.setAmount(BigDecimal.TEN);
    transaction.setTransactionDate(LocalDateTime.now());
    transaction.setTransactionType(TransactionTypeEnum.CREDIT);
    transaction.setTransactionReason(TransactionReasonEnum.TRANSFER);

    assertTransactionFields(transaction);
  }

  @Test
  void testNoArgsConstructor() {
    TransactionModel transaction = new TransactionModel();
    assertNotNull(transaction);
  }

  private void assertTransactionFields(TransactionModel transaction) {
    assertEquals(1L, transaction.getId());
    assertNotNull(transaction.getAccount());
    assertNotNull(transaction.getTransfer());
    assertEquals(BigDecimal.TEN, transaction.getAmount());
    assertNotNull(transaction.getTransactionDate());
    assertEquals(TransactionTypeEnum.CREDIT, transaction.getTransactionType());
    assertEquals(TransactionReasonEnum.TRANSFER, transaction.getTransactionReason());
  }
}
