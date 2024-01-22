package dev.challenge.api.adapter.entrypoint.command.customeraddress;

import dev.challenge.api.adapter.entrypoint.command.ServiceCommand;
import dev.challenge.api.adapter.entrypoint.dto.customeraddress.CustomerAddressDto;
import dev.challenge.api.adapter.entrypoint.dto.customeraddress.UpdateCustomerAddressDto;
import dev.challenge.api.adapter.entrypoint.mapper.CustomMapper;
import dev.challenge.api.domain.CustomerAddressService;
import dev.challenge.api.domain.model.CustomerAddressModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UpdateCustomerAddressCommand implements ServiceCommand<UpdateCustomerAddressDto, CustomerAddressDto> {

  private final CustomerAddressService service;
  private final CustomMapper customMapper;

  @Override
  public CustomerAddressDto execute(UpdateCustomerAddressDto updateAddressDto) {
    return customMapper.map(service.update(
            updateAddressDto.getId(),
            updateAddressDto.getCustomerId(),
            customMapper.map(updateAddressDto, CustomerAddressModel.class)),
        CustomerAddressDto.class);
  }
}

