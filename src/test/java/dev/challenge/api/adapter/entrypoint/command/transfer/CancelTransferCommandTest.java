package dev.challenge.api.adapter.entrypoint.command.transfer;

import dev.challenge.api.adapter.entrypoint.dto.transfer.CancelTransferDto;
import dev.challenge.api.adapter.entrypoint.dto.transfer.TransferDto;
import dev.challenge.api.adapter.entrypoint.mapper.CustomMapper;
import dev.challenge.api.domain.TransferService;
import dev.challenge.api.domain.model.TransferModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CancelTransferCommandTest {

  @Mock
  private TransferService transferService;

  @Mock
  private CustomMapper customMapper;

  @InjectMocks
  private CancelTransferCommand cancelTransferCommand;

  @Test
  void testExecute() {
    // Arrange
    CancelTransferDto cancelTransferDto = new CancelTransferDto();
    cancelTransferDto.setId(1L);

    TransferModel expectedTransferModel = new TransferModel();
    expectedTransferModel.setId(1L);

    TransferDto expectedTransferDto = new TransferDto();
    expectedTransferDto.setId(1L);

    when(transferService.cancelTransfer(1L)).thenReturn(expectedTransferModel);
    when(customMapper.map(expectedTransferModel, TransferDto.class)).thenReturn(expectedTransferDto);

    // Act
    TransferDto result = cancelTransferCommand.execute(cancelTransferDto);

    // Assert
    assertEquals(expectedTransferDto, result);
  }
}
