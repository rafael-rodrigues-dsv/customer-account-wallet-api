package dev.challenge.api.domain.enumeration;

public enum CustomerTypeEnum {
  PESSOA_FISICA("Pessoa Física"), PESSOA_JURIDICA("Pessoa Jurídica");
  private final String description;

  CustomerTypeEnum(String description) {
    this.description = description;
  }
}
