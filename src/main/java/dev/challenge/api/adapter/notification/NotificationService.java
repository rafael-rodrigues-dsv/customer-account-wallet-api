package dev.challenge.api.adapter.notification;

import dev.challenge.api.domain.enumeration.TransactionReasonEnum;

import java.math.BigDecimal;

public interface NotificationService {
  void sendNotification(String email, BigDecimal amount, Long transferId, TransactionReasonEnum transactionReasonEnum);
}
