package dev.challenge.api.adapter.entrypoint.dto.customeraddress;

import dev.challenge.api.domain.enumeration.CustomerAddressTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CustomerAddressDto {
  private Long id;
  private String street;
  private String city;
  private String state;
  private String zipCode;
  private Boolean isDefault;
  private CustomerAddressTypeEnum addressType;
}
