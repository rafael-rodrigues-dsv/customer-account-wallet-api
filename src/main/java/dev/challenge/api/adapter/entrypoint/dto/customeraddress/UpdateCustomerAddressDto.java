package dev.challenge.api.adapter.entrypoint.dto.customeraddress;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class UpdateCustomerAddressDto {
  @Getter(onMethod = @__(@JsonIgnore))
  @Setter(onMethod = @__(@JsonIgnore))
  private Long id;
  @Getter(onMethod = @__(@JsonIgnore))
  @Setter(onMethod = @__(@JsonIgnore))
  private Long customerId;
  private String street;
  private String city;
  private String state;
  private String zipCode;
  private CustomerAddressTypeEnum addressType;
}
