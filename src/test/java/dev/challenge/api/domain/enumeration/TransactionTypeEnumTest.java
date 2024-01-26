package dev.challenge.api.domain.enumeration;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionTypeEnumTest {

  @Test
  void testEnumValues() {
    assertEquals("Credit", TransactionTypeEnum.CREDIT.getDescription());
    assertEquals("Debit", TransactionTypeEnum.DEBIT.getDescription());
  }
}
