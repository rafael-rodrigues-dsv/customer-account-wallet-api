package dev.challenge.api.domain.enumeration;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerTypeEnumTest {

  @Test
  void testEnumValues() {
    assertEquals("Natural Person", CustomerTypeEnum.NATURAL_PERSON.getDescription());
    assertEquals("Legal Person", CustomerTypeEnum.LEGAL_PERSON.getDescription());
  }
}
