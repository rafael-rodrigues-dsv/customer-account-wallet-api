package dev.challenge.api.domain.impl;

import dev.challenge.api.adapter.database.repository.TransactionRepository;
import dev.challenge.api.domain.TransactionService;
import dev.challenge.api.domain.model.TransactionModel;
import dev.challenge.api.domain.model.TransferModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionServiceImpl implements TransactionService {

  private final TransactionRepository transactionRepository;

  @Override
  public TransactionModel add(TransactionModel transaction) {
    return transactionRepository.save(TransactionModel.builder()
        .account(transaction.getAccount())
        .amount(transaction.getAmount())
        .transactionDate(LocalDateTime.now())
        .transactionType(transaction.getTransactionType())
        .transactionReason(transaction.getTransactionReason())
        .transfer(transaction.getTransfer())
        .build());
  }

  @Override
  public List<TransactionModel> findAllByTransferId(Long transferId) {
    Example<TransactionModel> filter = Example.of(TransactionModel.builder()
        .transfer(TransferModel.builder().id(transferId).build())
        .build());

    return transactionRepository.findAll(filter);
  }
}
