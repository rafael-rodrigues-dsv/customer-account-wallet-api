package dev.challenge.api.adapter.entrypoint.command.customeraddress;

import dev.challenge.api.adapter.entrypoint.command.ServiceCommand;
import dev.challenge.api.adapter.entrypoint.dto.customeraddress.CustomerAddressDto;
import dev.challenge.api.adapter.entrypoint.mapper.CustomMapper;
import dev.challenge.api.domain.CustomerAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FindAllCustomerAddressCommand implements ServiceCommand<Long, List<CustomerAddressDto>> {

  private final CustomerAddressService service;
  private final CustomMapper customMapper;

  @Override
  public List<CustomerAddressDto> execute(Long customerId) {
    return customMapper.map(service.findAllByCustomerId(customerId), List.class);
  }
}
