package dev.challenge.api.adapter.entrypoint.command.customerbankaccount;

import dev.challenge.api.adapter.entrypoint.command.ServiceCommand;
import dev.challenge.api.adapter.entrypoint.dto.customeraddress.CustomerAddressDto;
import dev.challenge.api.adapter.entrypoint.dto.customerbankaccount.CustomerBankAccountDto;
import dev.challenge.api.adapter.entrypoint.dto.filter.FindByIdAndCustomerIdFilterDto;
import dev.challenge.api.adapter.entrypoint.mapper.CustomMapper;
import dev.challenge.api.domain.CustomerAddressService;
import dev.challenge.api.domain.CustomerBankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FindByIdCustomerBankAccountCommand implements ServiceCommand<FindByIdAndCustomerIdFilterDto, CustomerBankAccountDto> {

  private final CustomerBankAccountService service;
  private final CustomMapper customMapper;

  @Override
  public CustomerBankAccountDto execute(FindByIdAndCustomerIdFilterDto filterCustomerBankAccountDto) {
    return customMapper.map(service.findByIdAndCustomerId(
        filterCustomerBankAccountDto.getId(),
        filterCustomerBankAccountDto.getCustomerId()), CustomerBankAccountDto.class);
  }
}
