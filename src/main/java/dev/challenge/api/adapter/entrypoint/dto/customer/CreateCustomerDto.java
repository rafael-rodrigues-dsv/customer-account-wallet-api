package dev.challenge.api.adapter.entrypoint.dto.customer;

import dev.challenge.api.domain.enumeration.CustomerTypeEnum;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import jakarta.validation.constraints.Email;
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
public class CreateCustomerDto {

  @NotNull(message = "Customer type is mandatory")
  private CustomerTypeEnum customerType;

  @NotBlank(message = "Document number is mandatory")
  @Size(min = 11, max = 14, message = "Document number must be between 11 and 14 characters")
  @Pattern(regexp = "^(\\d{11})|(\\d{14})$", message = "Invalid document number")
  private String documentNumber;

  @NotBlank(message = "Name is mandatory")
  @Size(max = 150, message = "Name must be at most 150 characters")
  private String name;

  @NotBlank(message = "Email is mandatory")
  @Email(message = "Invalid email address")
  @Size(max = 35, message = "Email must be at most 35 characters")
  private String email;

  @NotBlank(message = "Phone number is mandatory")
  @Size(min = 11, max = 11, message = "Phone number must be 11 characters")
  @Pattern(regexp = "^(\\d{11})$", message = "Invalid phone number")
  private String phoneNumber;

  @NotNull(message = "Password is mandatory")
  @NotBlank(message = "Password cannot be blank")
  @Size(max = 16, message = "Password must have at most 16 characters")
  @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]*$", message = "Invalid password format. Should contain letters, numbers, and special characters.")
  private String password;
}
