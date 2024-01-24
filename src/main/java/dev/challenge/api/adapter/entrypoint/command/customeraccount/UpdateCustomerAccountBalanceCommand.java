package dev.challenge.api.adapter.entrypoint.command.customeraccount;

import dev.challenge.api.adapter.entrypoint.command.ServiceCommand;
import dev.challenge.api.adapter.entrypoint.dto.customeraccount.CustomerAccountDto;
import dev.challenge.api.adapter.entrypoint.dto.customeraccount.UpdateCustomerAccountBalanceDto;
import dev.challenge.api.adapter.entrypoint.mapper.CustomMapper;
import dev.challenge.api.domain.CustomerAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UpdateCustomerAccountBalanceCommand implements ServiceCommand<UpdateCustomerAccountBalanceDto, CustomerAccountDto> {

  private final CustomerAccountService service;
  private final CustomMapper customMapper;

  @Override
  public CustomerAccountDto execute(UpdateCustomerAccountBalanceDto updateAccountDto) {
    return customMapper.map(service.updateBalance(
            updateAccountDto.getId(),
            updateAccountDto.getBalance()),
        CustomerAccountDto.class);
  }
}
