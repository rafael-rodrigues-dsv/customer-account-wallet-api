package dev.challenge.api.adapter.entrypoint.dto.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class UpdateCustomerDto {

  @Getter(onMethod = @__(@JsonIgnore))
  @Setter(onMethod = @__(@JsonIgnore))
  private Long id;

  @NotBlank(message = "O nome é obrigatório")
  @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres")
  private String name;

  @NotBlank(message = "O endereço de e-mail é obrigatório")
  @Email(message = "Endereço de e-mail inválido")
  @Size(max = 35, message = "O e-mail deve ter no máximo 35 caracteres")
  private String email;

  @NotBlank(message = "O número de telefone é obrigatório")
  @Size(min = 11, max = 11, message = "O número de telefone deve ter 11 caracteres")
  @Pattern(regexp = "^(\\d{11})$", message = "Número de telefone inválido")
  private String phoneNumber;
}
