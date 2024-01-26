package dev.challenge.api.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DomainRuleExceptionTest {

  @Test
  void testExceptionMessage() {
    String errorMessage = "This is an error message";
    DomainRuleException exception = new DomainRuleException(errorMessage);
    assertEquals(errorMessage, exception.getMessage());
  }

  @Test
  void testExceptionMessageWithCause() {
    String errorMessage = "This is an error message";
    Throwable cause = new RuntimeException("Cause of the exception");
    DomainRuleException exception = new DomainRuleException(errorMessage, cause);

    assertEquals(errorMessage, exception.getMessage());
    assertEquals(cause, exception.getCause());
  }
}
