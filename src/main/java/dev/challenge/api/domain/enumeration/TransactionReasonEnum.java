package dev.challenge.api.domain.enumeration;

import lombok.Getter;

@Getter
public enum TransactionReasonEnum {
  TRANSFER("Transfer"), REFUND("Refund");

  private final String description;

  TransactionReasonEnum(String description) {
    this.description = description;
  }
}
