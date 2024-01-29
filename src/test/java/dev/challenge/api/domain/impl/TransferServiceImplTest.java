package dev.challenge.api.domain.impl;

import dev.challenge.api.adapter.database.repository.TransferRepository;
import dev.challenge.api.adapter.notification.NotificationService;
import dev.challenge.api.domain.CustomerAccountService;
import dev.challenge.api.domain.TransactionService;
import dev.challenge.api.domain.enumeration.CustomerAccountStatusEnum;
import dev.challenge.api.domain.enumeration.TransactionReasonEnum;
import dev.challenge.api.domain.enumeration.TransferStatusEnum;
import dev.challenge.api.domain.model.CustomerAccountModel;
import dev.challenge.api.domain.model.CustomerModel;
import dev.challenge.api.domain.model.TransferModel;
import dev.challenge.api.exception.DomainRuleException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransferServiceImplTest {

  @Mock
  private TransferRepository transferRepository;

  @Mock
  private CustomerAccountService customerAccountService;

  @Mock
  private TransactionService transactionService;

  @Mock
  private NotificationService notificationService;

  @InjectMocks
  private TransferServiceImpl transferService;

  @Test
  void testPerformTransfer_Success() {
    Long debitAccountId = 1L;
    Long creditAccountId = 2L;
    BigDecimal amount = BigDecimal.TEN;

    CustomerAccountModel debitAccount = CustomerAccountModel.builder()
        .id(debitAccountId)
        .balance(BigDecimal.valueOf(1000))
        .accountStatus(CustomerAccountStatusEnum.ACTIVE)
        .customer(CustomerModel.builder()
            .id(1L)
            .name("John Doe")
            .email("john.doe@example.com")
            .build())
        .build();

    CustomerAccountModel creditAccount = CustomerAccountModel.builder()
        .id(creditAccountId)
        .balance(BigDecimal.valueOf(0))
        .accountStatus(CustomerAccountStatusEnum.ACTIVE)
        .customer(CustomerModel.builder()
            .id(1L)
            .name("John Doe")
            .email("john.doe@example.com")
            .build())
        .build();

    TransferModel transferCreated = TransferModel.builder()
        .id(1L)
        .creditAccount(creditAccount)
        .debitAccount(debitAccount)
        .amount(BigDecimal.TEN)
        .transferStatus(TransferStatusEnum.COMPLETED)
        .build();

    when(customerAccountService.findById(debitAccountId)).thenReturn(debitAccount);
    when(customerAccountService.findById(creditAccountId)).thenReturn(creditAccount);
    when(transferRepository.save(any())).thenReturn(transferCreated);

    TransferModel result = transferService.performTransfer(debitAccountId, creditAccountId, amount);

    assertNotNull(result);
    assertEquals(debitAccount, result.getDebitAccount());
    assertEquals(creditAccount, result.getCreditAccount());
    assertEquals(amount, result.getAmount());
    assertEquals(TransferStatusEnum.COMPLETED, result.getTransferStatus());
    verify(customerAccountService, times(2)).updateBalance(any(), any(), any());
    verify(transactionService, times(2)).add(any());
    verify(transferRepository, times(1)).save(any());
    verify(notificationService, times(2)).sendNotification(any(String.class), any(BigDecimal.class), any(Long.class), any(TransactionReasonEnum.class));
  }

  @Test
  void testPerformTransfer_DebitAccountIsNotActive() {
    Long debitAccountId = 1L;
    Long creditAccountId = 2L;
    BigDecimal amount = BigDecimal.valueOf(100);

    CustomerAccountModel debitAccount = CustomerAccountModel.builder()
        .id(debitAccountId)
        .balance(BigDecimal.valueOf(10))
        .accountStatus(CustomerAccountStatusEnum.DISABLED)
        .customer(CustomerModel.builder().id(1L).build())
        .build();

    CustomerAccountModel creditAccount = CustomerAccountModel.builder()
        .id(creditAccountId)
        .balance(BigDecimal.valueOf(0))
        .accountStatus(CustomerAccountStatusEnum.ACTIVE)
        .customer(CustomerModel.builder().id(1L).build())
        .build();

    when(customerAccountService.findById(debitAccountId)).thenReturn(debitAccount);
    when(customerAccountService.findById(creditAccountId)).thenReturn(creditAccount);

    assertThrows(DomainRuleException.class, () -> transferService.performTransfer(debitAccountId, creditAccountId, amount));

    verify(customerAccountService, never()).updateBalance(any(), any(), any());
    verify(transactionService, never()).add(any());
    verify(transferRepository, never()).save(any());
  }

  @Test
  void testPerformTransfer_CreditAccountIsNotActive() {
    Long debitAccountId = 1L;
    Long creditAccountId = 2L;
    BigDecimal amount = BigDecimal.valueOf(100);

    CustomerAccountModel debitAccount = CustomerAccountModel.builder()
        .id(debitAccountId)
        .balance(BigDecimal.valueOf(10))
        .accountStatus(CustomerAccountStatusEnum.ACTIVE)
        .customer(CustomerModel.builder().id(1L).build())
        .build();

    CustomerAccountModel creditAccount = CustomerAccountModel.builder()
        .id(creditAccountId)
        .balance(BigDecimal.valueOf(0))
        .accountStatus(CustomerAccountStatusEnum.DISABLED)
        .customer(CustomerModel.builder().id(1L).build())
        .build();

    when(customerAccountService.findById(debitAccountId)).thenReturn(debitAccount);
    when(customerAccountService.findById(creditAccountId)).thenReturn(creditAccount);

    assertThrows(DomainRuleException.class, () -> transferService.performTransfer(debitAccountId, creditAccountId, amount));

    verify(customerAccountService, never()).updateBalance(any(), any(), any());
    verify(transactionService, never()).add(any());
    verify(transferRepository, never()).save(any());
  }

  @Test
  void testPerformTransfer_InsufficientFunds() {
    Long debitAccountId = 1L;
    Long creditAccountId = 2L;
    BigDecimal amount = BigDecimal.valueOf(100);

    CustomerAccountModel debitAccount = CustomerAccountModel.builder()
        .id(debitAccountId)
        .balance(BigDecimal.valueOf(10))
        .accountStatus(CustomerAccountStatusEnum.ACTIVE)
        .customer(CustomerModel.builder().id(1L).build())
        .build();

    CustomerAccountModel creditAccount = CustomerAccountModel.builder()
        .id(creditAccountId)
        .balance(BigDecimal.valueOf(0))
        .accountStatus(CustomerAccountStatusEnum.ACTIVE)
        .customer(CustomerModel.builder().id(1L).build())
        .build();

    when(customerAccountService.findById(debitAccountId)).thenReturn(debitAccount);
    when(customerAccountService.findById(creditAccountId)).thenReturn(creditAccount);

    assertThrows(DomainRuleException.class, () -> transferService.performTransfer(debitAccountId, creditAccountId, amount));

    verify(customerAccountService, never()).updateBalance(any(), any(), any());
    verify(transactionService, never()).add(any());
    verify(transferRepository, never()).save(any());
  }


  @Test
  void testCancelTransfer_Success() {
    Long transferId = 1L;
    BigDecimal amount = BigDecimal.TEN;

    CustomerAccountModel debitAccount = CustomerAccountModel.builder()
        .id(1L)
        .balance(BigDecimal.valueOf(0))
        .accountStatus(CustomerAccountStatusEnum.ACTIVE)
        .customer(CustomerModel.builder()
            .id(1L)
            .name("John Doe")
            .email("john.doe@example.com")
            .build())
        .build();

    CustomerAccountModel creditAccount = CustomerAccountModel.builder()
        .id(2L)
        .balance(BigDecimal.valueOf(100))
        .accountStatus(CustomerAccountStatusEnum.ACTIVE)
        .customer(CustomerModel.builder()
            .id(1L)
            .name("John Doe")
            .email("john.doe@example.com")
            .build())
        .build();

    TransferModel completedTransfer = TransferModel.builder()
        .id(1L)
        .debitAccount(debitAccount)
        .creditAccount(creditAccount)
        .amount(amount)
        .transferDate(LocalDateTime.now())
        .transferStatus(TransferStatusEnum.COMPLETED)
        .build();

    when(transferRepository.findById(transferId)).thenReturn(Optional.of(completedTransfer));
    when(customerAccountService.findById(debitAccount.getId())).thenReturn(debitAccount);
    when(customerAccountService.findById(creditAccount.getId())).thenReturn(creditAccount);
    when(transferRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

    TransferModel canceledTransfer = transferService.cancelTransfer(transferId);

    assertNotNull(canceledTransfer);
    assertEquals(TransferStatusEnum.CANCELED, canceledTransfer.getTransferStatus());
    verify(customerAccountService, times(2)).updateBalance(any(), any(), any());
    verify(transactionService, times(2)).add(any());
    verify(transferRepository, times(1)).save(any());
    verify(notificationService, times(2)).sendNotification(any(String.class), any(BigDecimal.class), any(Long.class), any(TransactionReasonEnum.class));
  }

  @Test
  void testCancelTransfer_AlreadyCanceled() {
    Long transferId = 1L;

    TransferModel canceledTransfer = TransferModel.builder()
        .id(1L)
        .transferDate(LocalDateTime.now())
        .transferStatus(TransferStatusEnum.CANCELED)
        .build();

    when(transferRepository.findById(transferId)).thenReturn(Optional.of(canceledTransfer));

    assertThrows(DomainRuleException.class, () -> transferService.cancelTransfer(transferId));

    verify(customerAccountService, never()).updateBalance(any(), any(), any());
    verify(transactionService, never()).add(any());
    verify(transferRepository, never()).save(any());
  }
}
