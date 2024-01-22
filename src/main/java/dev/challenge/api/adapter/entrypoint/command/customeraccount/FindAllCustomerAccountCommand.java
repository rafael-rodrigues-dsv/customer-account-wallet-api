package dev.challenge.api.adapter.entrypoint.command.customeraccount;

import dev.challenge.api.adapter.entrypoint.command.ServiceCommand;
import dev.challenge.api.adapter.entrypoint.dto.customeraccount.CustomerAccountDto;
import dev.challenge.api.adapter.entrypoint.mapper.CustomMapper;
import dev.challenge.api.domain.CustomerAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FindAllCustomerAccountCommand implements ServiceCommand<Long, List<CustomerAccountDto>> {

  private final CustomerAccountService service;
  private final CustomMapper customMapper;

  @Override
  public List<CustomerAccountDto> execute(Long customerId) {
    return customMapper.map(service.findAllByCustomerId(customerId), List.class);
  }
}
