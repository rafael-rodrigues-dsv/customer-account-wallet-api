package dev.challenge.api.adapter.entrypoint.command.transfer;

import dev.challenge.api.adapter.entrypoint.dto.transfer.PerformTransferDto;
import dev.challenge.api.adapter.entrypoint.dto.transfer.TransferDto;
import dev.challenge.api.adapter.entrypoint.mapper.CustomMapper;
import dev.challenge.api.domain.TransferService;
import dev.challenge.api.domain.model.TransferModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PerformTransferCommandTest {

  @Mock
  private TransferService transferService;

  @Mock
  private CustomMapper customMapper;

  @InjectMocks
  private PerformTransferCommand performTransferCommand;

  @Test
  void testExecute() {
    // Arrange
    PerformTransferDto performTransferDto = new PerformTransferDto();
    performTransferDto.setDebitAccountId(1L);
    performTransferDto.setCreditAccountId(2L);
    performTransferDto.setAmount(BigDecimal.TEN);

    TransferModel expectedTransferModel = new TransferModel();
    expectedTransferModel.setId(1L);

    TransferDto expectedTransferDto = new TransferDto();
    expectedTransferDto.setId(1L);

    when(transferService.performTransfer(1L, 2L, BigDecimal.TEN)).thenReturn(expectedTransferModel);
    when(customMapper.map(expectedTransferModel, TransferDto.class)).thenReturn(expectedTransferDto);

    // Act
    TransferDto result = performTransferCommand.execute(performTransferDto);

    // Assert
    assertEquals(expectedTransferDto, result);
  }
}
