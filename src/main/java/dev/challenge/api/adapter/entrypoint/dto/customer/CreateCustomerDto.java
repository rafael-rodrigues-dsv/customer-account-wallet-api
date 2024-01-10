package dev.challenge.api.adapter.entrypoint.dto.customer;

import dev.challenge.api.domain.enumeration.CustomerTypeEnum;
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
public class CreateCustomerDto {
  private CustomerTypeEnum customerType;
  private String documentNumber;
  private String name;
  private String email;
  private String phoneNumber;
}
