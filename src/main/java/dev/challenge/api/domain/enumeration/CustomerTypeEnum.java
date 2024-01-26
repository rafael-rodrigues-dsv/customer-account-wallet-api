package dev.challenge.api.domain.enumeration;

import lombok.Getter;

@Getter
public enum CustomerTypeEnum {
  NATURAL_PERSON("Natural Person"), LEGAL_PERSON("Legal Person");

  private final String description;

  CustomerTypeEnum(String description) {
    this.description = description;
  }
}
