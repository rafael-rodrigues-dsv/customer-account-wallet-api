package dev.challenge.api.adapter.entrypoint.command.customeraccount;

import dev.challenge.api.adapter.entrypoint.command.ServiceCommand;
import dev.challenge.api.adapter.entrypoint.dto.customeraccount.CustomerAccountDto;
import dev.challenge.api.adapter.entrypoint.dto.customeraccount.UpdateCustomerAccountDto;
import dev.challenge.api.adapter.entrypoint.mapper.CustomMapper;
import dev.challenge.api.domain.CustomerAddressService;
import dev.challenge.api.domain.model.CustomerAddressModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UpdateCustomerAccountCommand implements ServiceCommand<UpdateCustomerAccountDto, CustomerAccountDto> {

  private final CustomerAddressService service;
  private final CustomMapper customMapper;

  @Override
  public CustomerAccountDto execute(UpdateCustomerAccountDto updateAddressDto) {
    return customMapper.map(service.update(
            updateAddressDto.getId(),
            updateAddressDto.getCustomerId(),
            customMapper.map(updateAddressDto, CustomerAddressModel.class)),
        CustomerAccountDto.class);
  }
}
