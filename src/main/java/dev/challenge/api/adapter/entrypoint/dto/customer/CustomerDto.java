package dev.challenge.api.adapter.entrypoint.dto.customer;

import dev.challenge.api.adapter.entrypoint.dto.customeraddress.CustomerAddressDto;
import dev.challenge.api.adapter.entrypoint.dto.customerbankaccount.CustomerBankAccountDto;
import dev.challenge.api.domain.enumeration.CustomerTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CustomerDto {
  private Long customerId;
  private CustomerTypeEnum customerType;
  private String documentNumber;
  private String name;
  private String email;
  private String phoneNumber;
  private Set<CustomerAddressDto> customerAddresses;
  private Set<CustomerBankAccountDto> customerBankAccounts;
}
