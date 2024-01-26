package dev.challenge.api.adapter.entrypoint.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ServiceCommandTest implements ServiceCommand<Object, Object> {

  @Test
  void testExecuteWithSingleParameter() {
    UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
        () -> execute(new Object()));
    assertEquals("Operação não suportada.", exception.getMessage());
  }
}
