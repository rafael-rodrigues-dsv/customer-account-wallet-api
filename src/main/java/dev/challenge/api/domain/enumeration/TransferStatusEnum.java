package dev.challenge.api.domain.enumeration;

import lombok.Getter;

@Getter
public enum TransferStatusEnum {
  COMPLETED("Completed"), DECLINED("Declined"), CANCELED("Canceled");

  private final String description;

  TransferStatusEnum(String description) {
    this.description = description;
  }
}
