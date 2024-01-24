package dev.challenge.api.domain.enumeration;

public enum CustomerAccountStatusEnum {
  ACTIVE("Active"), BLOCKED("Blocked"), DISABLED("Disabled");

  private final String description;

  CustomerAccountStatusEnum(String description) {
    this.description = description;
  }
}
