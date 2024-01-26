package dev.challenge.api.domain.impl;

import dev.challenge.api.adapter.database.repository.TransactionRepository;
import dev.challenge.api.domain.enumeration.TransactionReasonEnum;
import dev.challenge.api.domain.enumeration.TransactionTypeEnum;
import dev.challenge.api.domain.model.CustomerAccountModel;
import dev.challenge.api.domain.model.TransactionModel;
import dev.challenge.api.domain.model.TransferModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

  @Mock
  private TransactionRepository transactionRepository;

  @InjectMocks
  private TransactionServiceImpl transactionService;

  @Test
  void testAdd() {
    // Arrange
    TransactionModel inputTransaction = createTransactionModel();

    when(transactionRepository.save(any())).thenReturn(inputTransaction);

    // Act
    TransactionModel result = transactionService.add(inputTransaction);

    // Assert
    assertNotNull(result);
    assertEquals(inputTransaction.getAccount(), result.getAccount());
    assertEquals(inputTransaction.getAmount(), result.getAmount());
    assertEquals(inputTransaction.getTransactionType(), result.getTransactionType());
    assertEquals(inputTransaction.getTransactionReason(), result.getTransactionReason());
    assertEquals(inputTransaction.getTransfer(), result.getTransfer());
    verify(transactionRepository, times(1)).save(any());
  }

  @Test
  void testFindAllByTransferId() {
    // Arrange
    Long transferId = 1L;
    TransactionModel transaction = createTransactionModel();
    List<TransactionModel> transactions = Collections.singletonList(transaction);

    Example<TransactionModel> example = Example.of(
        TransactionModel.builder()
            .transfer(TransferModel.builder().id(transferId).build())
            .build()
    );

    when(transactionRepository.findAll(any(Example.class))).thenReturn(transactions);

    // Act
    List<TransactionModel> result = transactionService.findAllByTransferId(transferId);

    // Assert
    assertNotNull(result);
    assertEquals(transactions.size(), result.size());
  }


  private TransactionModel createTransactionModel() {
    return TransactionModel.builder()
        .account(CustomerAccountModel.builder().build())
        .amount(BigDecimal.TEN)
        .transactionDate(LocalDateTime.now())
        .transactionType(TransactionTypeEnum.CREDIT)
        .transactionReason(TransactionReasonEnum.TRANSFER)
        .transfer(TransferModel.builder().id(1L).build())
        .build();
  }
}
