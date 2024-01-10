package dev.challenge.api.domain.enumeration;

import lombok.Getter;

@Getter
public enum CustomerAddressTypeEnum {
  RESIDENCIAL("Residencial"), COMERCIAL("Comercial");

  private final String description;

  CustomerAddressTypeEnum(String description) {
    this.description = description;
  }
}
