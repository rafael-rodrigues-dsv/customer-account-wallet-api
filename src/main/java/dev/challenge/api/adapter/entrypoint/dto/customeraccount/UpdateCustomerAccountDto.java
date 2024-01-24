package dev.challenge.api.adapter.entrypoint.dto.customeraccount;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class UpdateCustomerAccountDto {

  @Getter(onMethod = @__(@JsonIgnore))
  @Setter(onMethod = @__(@JsonIgnore))
  private Long id;

  @NotNull(message = "Agency is mandatory")
  @NotBlank(message = "Agency cannot be blank")
  @Size(max = 6, message = "Agency must have at most 6 characters")
  @Pattern(regexp = "^[0-9a-zA-Z]*$", message = "Invalid agency format. Should contain only numbers or letters.")
  private String agency;

  @NotNull(message = "Account number is mandatory")
  @NotBlank(message = "Account number cannot be blank")
  @Size(max = 6, message = "Account number must have at most 6 characters")
  @Pattern(regexp = "^[0-9a-zA-Z]*$", message = "Invalid account number format. Should contain only numbers or letters.")
  private String accountNumber;
}
