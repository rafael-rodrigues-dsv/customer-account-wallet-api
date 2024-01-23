package dev.challenge.api.adapter.entrypoint.command.transfer;

import dev.challenge.api.adapter.entrypoint.command.ServiceCommand;
import dev.challenge.api.adapter.entrypoint.dto.transfer.CancelTransferDto;
import dev.challenge.api.adapter.entrypoint.dto.transfer.TransferDto;
import dev.challenge.api.adapter.entrypoint.mapper.CustomMapper;
import dev.challenge.api.domain.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CancelTransferCommand implements ServiceCommand<CancelTransferDto, TransferDto> {

  private final TransferService service;
  private final CustomMapper customMapper;

  @Override
  public TransferDto execute(CancelTransferDto cancelTransferDto) {
    return customMapper.map(service.cancelTransfer(cancelTransferDto.getId()),
        TransferDto.class);
  }
}