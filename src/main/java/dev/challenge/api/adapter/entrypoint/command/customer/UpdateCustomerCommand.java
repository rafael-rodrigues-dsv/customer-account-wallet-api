package dev.challenge.api.adapter.entrypoint.command.customer;

import dev.challenge.api.adapter.entrypoint.command.ServiceCommand;
import dev.challenge.api.adapter.entrypoint.dto.customer.CustomerDto;
import dev.challenge.api.adapter.entrypoint.dto.customer.UpdateCustomerDto;
import dev.challenge.api.adapter.entrypoint.mapper.CustomMapper;
import dev.challenge.api.domain.CustomerService;
import dev.challenge.api.domain.model.CustomerModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UpdateCustomerCommand implements ServiceCommand<UpdateCustomerDto, CustomerDto> {

  private final CustomerService service;
  private final CustomMapper customMapper;

  @Override
  public CustomerDto execute(UpdateCustomerDto updateCustomerDto) {
    return customMapper.map(service.update(updateCustomerDto.getId(),
        customMapper.map(updateCustomerDto, CustomerModel.class)), CustomerDto.class);
  }
}
