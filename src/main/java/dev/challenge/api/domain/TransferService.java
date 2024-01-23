package dev.challenge.api.domain;

import dev.challenge.api.domain.model.TransferModel;

import java.math.BigDecimal;

public interface TransferService {
  TransferModel performTransfer(Long debitAccountId, Long creditAccountId, BigDecimal amount);
  TransferModel cancelTransfer(Long id);
  TransferModel findById(Long id);
}
