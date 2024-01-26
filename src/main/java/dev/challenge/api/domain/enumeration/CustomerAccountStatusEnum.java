package dev.challenge.api.domain.enumeration;

import lombok.Getter;

@Getter
public enum CustomerAccountStatusEnum {
  ACTIVE("Active"), BLOCKED("Blocked"), DISABLED("Disabled");

  private final String description;

  CustomerAccountStatusEnum(String description) {
    this.description = description;
  }
}
