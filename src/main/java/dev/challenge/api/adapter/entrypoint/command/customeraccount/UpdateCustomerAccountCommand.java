package dev.challenge.api.adapter.entrypoint.command.customeraccount;

import dev.challenge.api.adapter.entrypoint.command.ServiceCommand;
import dev.challenge.api.adapter.entrypoint.dto.customeraccount.CustomerAccountDto;
import dev.challenge.api.adapter.entrypoint.dto.customeraccount.UpdateCustomerAccountDto;
import dev.challenge.api.adapter.entrypoint.mapper.CustomMapper;
import dev.challenge.api.domain.CustomerAccountService;
import dev.challenge.api.domain.model.CustomerAccountModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UpdateCustomerAccountCommand implements ServiceCommand<UpdateCustomerAccountDto, CustomerAccountDto> {

  private final CustomerAccountService service;
  private final CustomMapper customMapper;

  @Override
  public CustomerAccountDto execute(UpdateCustomerAccountDto updateAccountDto) {
    return customMapper.map(service.update(
            updateAccountDto.getId(),
            updateAccountDto.getCustomerId(),
            customMapper.map(updateAccountDto, CustomerAccountModel.class)),
        CustomerAccountDto.class);
  }
}
