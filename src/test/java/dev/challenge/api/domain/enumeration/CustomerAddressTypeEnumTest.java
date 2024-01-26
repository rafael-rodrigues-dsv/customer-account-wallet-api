package dev.challenge.api.domain.enumeration;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerAddressTypeEnumTest {

  @Test
  void testEnumValues() {
    assertEquals("Residential", CustomerAddressTypeEnum.RESIDENTIAL.getDescription());
    assertEquals("Commercial", CustomerAddressTypeEnum.COMMERCIAL.getDescription());
  }
}
