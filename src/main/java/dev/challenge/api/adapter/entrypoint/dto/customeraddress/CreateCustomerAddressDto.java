package dev.challenge.api.adapter.entrypoint.dto.customeraddress;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.challenge.api.domain.enumeration.CustomerAddressTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class CreateCustomerAddressDto {

  @Getter(onMethod = @__(@JsonIgnore))
  @Setter(onMethod = @__(@JsonIgnore))
  private Long customerId;

  @NotBlank(message = "Street is mandatory")
  @Size(max = 200, message = "Street must have at most 200 characters")
  private String street;

  @NotBlank(message = "City is mandatory")
  @Size(max = 100, message = "City must have at most 100 characters")
  private String city;

  @NotBlank(message = "State is mandatory")
  @Size(max = 30, message = "State must have at most 30 characters")
  private String state;

  @NotBlank(message = "Zip code is mandatory")
  @Size(max = 10, message = "Zip code must have at most 10 characters")
  @Pattern(regexp = "\\d{10}", message = "Invalid zip code. Should contain only numbers.")
  private String zipCode;

  @NotNull(message = "Address type is mandatory")
  private CustomerAddressTypeEnum addressType;
}
