package dev.challenge.api.adapter.entrypoint.command.customeraddress;

import dev.challenge.api.adapter.entrypoint.command.ServiceCommand;
import dev.challenge.api.adapter.entrypoint.dto.customeraddress.CustomerAddressDto;
import dev.challenge.api.adapter.entrypoint.mapper.CustomMapper;
import dev.challenge.api.domain.CustomerAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FindByIdCustomerAddressCommand implements ServiceCommand<Long, CustomerAddressDto> {

  private final CustomerAddressService service;
  private final CustomMapper customMapper;

  @Override
  public CustomerAddressDto execute(Long id) {
    return customMapper.map(service.findById(id), CustomerAddressDto.class);
  }
}

