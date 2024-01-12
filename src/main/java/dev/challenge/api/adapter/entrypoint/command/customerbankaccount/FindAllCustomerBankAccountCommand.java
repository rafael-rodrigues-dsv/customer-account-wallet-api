package dev.challenge.api.adapter.entrypoint.command.customerbankaccount;

import dev.challenge.api.adapter.entrypoint.command.ServiceCommand;
import dev.challenge.api.adapter.entrypoint.dto.customerbankaccount.CustomerBankAccountDto;
import dev.challenge.api.adapter.entrypoint.mapper.CustomMapper;
import dev.challenge.api.domain.CustomerBankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FindAllCustomerBankAccountCommand implements ServiceCommand<Long, List<CustomerBankAccountDto>> {

  private final CustomerBankAccountService service;
  private final CustomMapper customMapper;

  @Override
  public List<CustomerBankAccountDto> execute(Long customerId) {
    return customMapper.map(service.findAllByCustomerId(customerId), List.class);
  }
}
