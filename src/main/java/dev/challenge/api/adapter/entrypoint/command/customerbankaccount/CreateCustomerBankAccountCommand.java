package dev.challenge.api.adapter.entrypoint.command.customerbankaccount;

import dev.challenge.api.adapter.entrypoint.command.ServiceCommand;
import dev.challenge.api.adapter.entrypoint.dto.customerbankaccount.CreateCustomerBankAccountDto;
import dev.challenge.api.adapter.entrypoint.dto.customerbankaccount.CustomerBankAccountDto;
import dev.challenge.api.adapter.entrypoint.mapper.CustomMapper;
import dev.challenge.api.domain.CustomerBankAccountService;
import dev.challenge.api.domain.model.CustomerBankAccountModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CreateCustomerBankAccountCommand implements ServiceCommand<CreateCustomerBankAccountDto, CustomerBankAccountDto> {

  private final CustomerBankAccountService service;
  private final CustomMapper customMapper;

  @Override
  public CustomerBankAccountDto execute(CreateCustomerBankAccountDto customerBankAccountDto) {
    return customMapper.map(service.add(customerBankAccountDto.getCustomerId(),
        customMapper.map(customerBankAccountDto, CustomerBankAccountModel.class)), CustomerBankAccountDto.class);
  }
}
