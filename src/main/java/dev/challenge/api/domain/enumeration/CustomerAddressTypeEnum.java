package dev.challenge.api.domain.enumeration;

import lombok.Getter;

@Getter
public enum CustomerAddressTypeEnum {
  RESIDENTIAL("Residential"), COMMERCIAL("Commercial");

  private final String description;

  CustomerAddressTypeEnum(String description) {
    this.description = description;
  }
}
