package dev.challenge.api.adapter.entrypoint.dto.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class UpdateCustomerDto {
  @Getter(onMethod = @__(@JsonIgnore))
  @Setter(onMethod = @__(@JsonIgnore))
  private Long customerId;
  private String name;
  private String email;
  private String phoneNumber;
}
