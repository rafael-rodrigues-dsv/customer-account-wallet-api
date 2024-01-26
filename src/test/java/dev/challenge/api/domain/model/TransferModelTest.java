package dev.challenge.api.domain.model;

import dev.challenge.api.domain.enumeration.TransferStatusEnum;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TransferModelTest {

  @Test
  void testBuilder() {
    CustomerAccountModel debitAccount = CustomerAccountModel.builder().id(1L).build();
    CustomerAccountModel creditAccount = CustomerAccountModel.builder().id(2L).build();

    TransferModel transfer = TransferModel.builder()
        .id(1L)
        .debitAccount(debitAccount)
        .creditAccount(creditAccount)
        .amount(BigDecimal.TEN)
        .transferDate(LocalDateTime.now())
        .transferStatus(TransferStatusEnum.COMPLETED)
        .transactions(Set.of(TransactionModel.builder().id(3L).build()))
        .build();

    assertNotNull(transfer);
    assertTransferFields(transfer);
  }

  @Test
  void testGettersAndSetters() {
    CustomerAccountModel debitAccount = CustomerAccountModel.builder().id(1L).build();
    CustomerAccountModel creditAccount = CustomerAccountModel.builder().id(2L).build();

    TransferModel transfer = new TransferModel();
    transfer.setId(1L);
    transfer.setDebitAccount(debitAccount);
    transfer.setCreditAccount(creditAccount);
    transfer.setAmount(BigDecimal.TEN);
    transfer.setTransferDate(LocalDateTime.now());
    transfer.setTransferStatus(TransferStatusEnum.COMPLETED);
    transfer.setTransactions(Set.of(TransactionModel.builder().id(3L).build()));

    assertTransferFields(transfer);
  }

  @Test
  void testNoArgsConstructor() {
    TransferModel transfer = new TransferModel();
    assertNotNull(transfer);
  }

  private void assertTransferFields(TransferModel transfer) {
    assertEquals(1L, transfer.getId());
    assertNotNull(transfer.getDebitAccount());
    assertNotNull(transfer.getCreditAccount());
    assertEquals(BigDecimal.TEN, transfer.getAmount());
    assertNotNull(transfer.getTransferDate());
    assertEquals(TransferStatusEnum.COMPLETED, transfer.getTransferStatus());
    assertNotNull(transfer.getTransactions());
  }
}
