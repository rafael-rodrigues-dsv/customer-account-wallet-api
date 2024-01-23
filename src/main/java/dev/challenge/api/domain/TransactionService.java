package dev.challenge.api.domain;

import dev.challenge.api.domain.model.TransactionModel;

import java.util.List;

public interface TransactionService {
  TransactionModel add(TransactionModel transaction);
  List<TransactionModel> findAllByTransferId(Long transferId);
}
