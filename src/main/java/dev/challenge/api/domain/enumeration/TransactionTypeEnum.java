package dev.challenge.api.domain.enumeration;

import lombok.Getter;

@Getter
public enum TransactionTypeEnum {
  CREDIT("Credit"), DEBIT("Debit");

  private final String description;

  TransactionTypeEnum(String description) {
    this.description = description;
  }
}
