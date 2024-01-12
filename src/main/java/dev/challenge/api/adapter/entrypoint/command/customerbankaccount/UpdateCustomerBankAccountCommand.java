package dev.challenge.api.adapter.entrypoint.command.customerbankaccount;

import dev.challenge.api.adapter.entrypoint.command.ServiceCommand;
import dev.challenge.api.adapter.entrypoint.dto.customerbankaccount.CustomerBankAccountDto;
import dev.challenge.api.adapter.entrypoint.dto.customerbankaccount.UpdateCustomerBankAccountDto;
import dev.challenge.api.adapter.entrypoint.mapper.CustomMapper;
import dev.challenge.api.domain.CustomerAddressService;
import dev.challenge.api.domain.model.CustomerAddressModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UpdateCustomerBankAccountCommand implements ServiceCommand<UpdateCustomerBankAccountDto, CustomerBankAccountDto> {

  private final CustomerAddressService service;
  private final CustomMapper customMapper;

  @Override
  public CustomerBankAccountDto execute(UpdateCustomerBankAccountDto updateCustomerAddressDto) {
    return customMapper.map(service.update(
            updateCustomerAddressDto.getId(),
            updateCustomerAddressDto.getCustomerId(),
            customMapper.map(updateCustomerAddressDto, CustomerAddressModel.class)),
        CustomerBankAccountDto.class);
  }
}
