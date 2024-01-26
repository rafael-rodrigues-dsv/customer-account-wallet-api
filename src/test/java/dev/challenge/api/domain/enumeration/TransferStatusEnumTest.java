package dev.challenge.api.domain.enumeration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransferStatusEnumTest {

  @Test
  void testEnumValues() {
    assertEquals("Completed", TransferStatusEnum.COMPLETED.getDescription());
    assertEquals("Declined", TransferStatusEnum.DECLINED.getDescription());
    assertEquals("Canceled", TransferStatusEnum.CANCELED.getDescription());
  }
}
