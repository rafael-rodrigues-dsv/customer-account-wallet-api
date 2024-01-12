package dev.challenge.api.adapter.entrypoint.command.customeraddress;

import dev.challenge.api.adapter.entrypoint.command.ServiceCommand;
import dev.challenge.api.adapter.entrypoint.dto.customeraddress.CreateCustomerAddressDto;
import dev.challenge.api.adapter.entrypoint.dto.customeraddress.CustomerAddressDto;
import dev.challenge.api.adapter.entrypoint.mapper.CustomMapper;
import dev.challenge.api.domain.CustomerAddressService;
import dev.challenge.api.domain.model.CustomerAddressModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CreateCustomerAddressCommand implements ServiceCommand<CreateCustomerAddressDto, CustomerAddressDto> {

  private final CustomerAddressService service;
  private final CustomMapper customMapper;

  @Override
  public CustomerAddressDto execute(CreateCustomerAddressDto createCustomerAddressDto) {
    return customMapper.map(service.add(createCustomerAddressDto.getCustomerId(),
        customMapper.map(createCustomerAddressDto, CustomerAddressModel.class)), CustomerAddressDto.class);
  }
}
