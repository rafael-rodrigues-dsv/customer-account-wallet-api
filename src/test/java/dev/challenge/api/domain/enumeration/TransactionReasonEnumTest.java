package dev.challenge.api.domain.enumeration;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionReasonEnumTest {

  @Test
  void testEnumValues() {
    assertEquals("Transfer", TransactionReasonEnum.TRANSFER.getDescription());
    assertEquals("Refund", TransactionReasonEnum.REFUND.getDescription());
  }
}
