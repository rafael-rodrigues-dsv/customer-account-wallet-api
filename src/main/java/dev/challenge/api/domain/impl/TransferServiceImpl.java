package dev.challenge.api.domain.impl;

import dev.challenge.api.adapter.database.repository.TransferRepository;
import dev.challenge.api.domain.CustomerAccountService;
import dev.challenge.api.domain.TransactionService;
import dev.challenge.api.domain.TransferService;
import dev.challenge.api.domain.enumeration.TransactionReasonEnum;
import dev.challenge.api.domain.enumeration.TransactionTypeEnum;
import dev.challenge.api.domain.enumeration.TransferStatusEnum;
import dev.challenge.api.domain.model.CustomerAccountModel;
import dev.challenge.api.domain.model.TransactionModel;
import dev.challenge.api.domain.model.TransferModel;
import dev.challenge.api.exception.BusinessException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransferServiceImpl implements TransferService {

  private final TransferRepository transferRepository;
  private final CustomerAccountService customerAccountService;
  private final TransactionService transactionService;

  @Override
  public TransferModel performTransfer(Long debitAccountId, Long creditAccountId, BigDecimal amount) {
    CustomerAccountModel debitAccount = customerAccountService.findById(debitAccountId);
    CustomerAccountModel creditAccount = customerAccountService.findById(creditAccountId);

    if (debitAccount.getBalance().doubleValue() < amount.doubleValue()) {
      throw new BusinessException("Insufficient balance in the debit account");
    }

    customerAccountService.updateBalance(debitAccount.getId(), debitAccount.getBalance().subtract(amount));
    customerAccountService.updateBalance(creditAccount.getId(), creditAccount.getBalance().add(amount));

    TransferModel createdTransfer = transferRepository.save(TransferModel.builder()
        .debitAccount(debitAccount)
        .creditAccount(creditAccount)
        .amount(amount)
        .transferDate(LocalDateTime.now())
        .transferStatus(TransferStatusEnum.COMPLETED)
        .build());

    transactionService.add(TransactionModel.builder()
        .account(debitAccount)
        .amount(amount.negate())
        .transactionType(TransactionTypeEnum.DEBIT)
        .transactionReason(TransactionReasonEnum.TRANSFER)
        .transfer(createdTransfer)
        .build());

    transactionService.add(TransactionModel.builder()
        .account(creditAccount)
        .amount(amount)
        .transactionType(TransactionTypeEnum.CREDIT)
        .transactionReason(TransactionReasonEnum.TRANSFER)
        .transfer(createdTransfer)
        .build());

    createdTransfer.setTransactions(transactionService.findAllByTransferId(createdTransfer.getId())
        .stream()
        .collect(Collectors.toSet()));

    return createdTransfer;
  }

  @Override
  public TransferModel cancelTransfer(Long id) {
    TransferModel transferToCancel = findById(id);

    if (!transferToCancel.getTransferStatus().equals(TransferStatusEnum.COMPLETED)) {
      throw new BusinessException("Transfer already canceled or declined");
    }

    CustomerAccountModel debitAccount = customerAccountService.findById(transferToCancel.getDebitAccount().getId());
    CustomerAccountModel creditAccount = customerAccountService.findById(transferToCancel.getCreditAccount().getId());
    BigDecimal amount = transferToCancel.getAmount();

    customerAccountService.updateBalance(debitAccount.getId(), debitAccount.getBalance().add(amount));
    customerAccountService.updateBalance(creditAccount.getId(), creditAccount.getBalance().subtract(amount));

    transferToCancel.setTransferStatus(TransferStatusEnum.CANCELED);

    TransferModel canceledTransfer = transferRepository.save(transferToCancel);

    transactionService.add(TransactionModel.builder()
        .account(creditAccount)
        .amount(amount.negate())
        .transactionType(TransactionTypeEnum.DEBIT)
        .transactionReason(TransactionReasonEnum.REFUND)
        .transfer(transferToCancel)
        .build());

    transactionService.add(TransactionModel.builder()
        .account(debitAccount)
        .amount(amount)
        .transactionType(TransactionTypeEnum.CREDIT)
        .transactionReason(TransactionReasonEnum.REFUND)
        .transfer(transferToCancel)
        .build());

    canceledTransfer.setTransactions(transactionService.findAllByTransferId(canceledTransfer.getId())
        .stream()
        .collect(Collectors.toSet()));

    return canceledTransfer;
  }

  @Override
  public TransferModel findById(Long id) {
    return transferRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Transfer not found with id " + id));
  }
}
