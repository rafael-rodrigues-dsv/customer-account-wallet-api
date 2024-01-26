package dev.challenge.api.domain.enumeration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerAccountStatusEnumTest {

  @Test
  void testEnumValues() {
    assertEquals("Active", CustomerAccountStatusEnum.ACTIVE.getDescription());
    assertEquals("Blocked", CustomerAccountStatusEnum.BLOCKED.getDescription());
    assertEquals("Disabled", CustomerAccountStatusEnum.DISABLED.getDescription());
  }
}
