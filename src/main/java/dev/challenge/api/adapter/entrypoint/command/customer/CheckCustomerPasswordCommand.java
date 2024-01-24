package dev.challenge.api.adapter.entrypoint.command.customer;

import dev.challenge.api.adapter.entrypoint.command.ServiceCommand;
import dev.challenge.api.adapter.entrypoint.dto.customer.CheckCustomerPasswordDto;
import dev.challenge.api.domain.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CheckCustomerPasswordCommand implements ServiceCommand<CheckCustomerPasswordDto, Boolean> {

  private final CustomerService service;

  @Override
  public Boolean execute(CheckCustomerPasswordDto customerPasswordDto) {
    return service.checkPassword(customerPasswordDto.getId(), customerPasswordDto.getPassword());
  }
}

