package dev.challenge.api.adapter.entrypoint.command.transfer;

import dev.challenge.api.adapter.entrypoint.command.ServiceCommand;
import dev.challenge.api.adapter.entrypoint.dto.customeraddress.CustomerAddressDto;
import dev.challenge.api.adapter.entrypoint.dto.transfer.PerformTransferDto;
import dev.challenge.api.adapter.entrypoint.dto.transfer.TransferDto;
import dev.challenge.api.adapter.entrypoint.mapper.CustomMapper;
import dev.challenge.api.domain.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PerformTransferCommand implements ServiceCommand<PerformTransferDto, TransferDto> {

  private final TransferService service;
  private final CustomMapper customMapper;

  @Override
  public TransferDto execute(PerformTransferDto performTransferDto) {
    return customMapper.map(service.performTransfer(performTransferDto.getDebitAccountId(),
        performTransferDto.getCreditAccountId(),
        performTransferDto.getAmount()), TransferDto.class);
  }
}

