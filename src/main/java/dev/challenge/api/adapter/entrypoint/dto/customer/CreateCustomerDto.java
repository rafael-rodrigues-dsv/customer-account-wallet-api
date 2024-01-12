package dev.challenge.api.adapter.entrypoint.dto.customer;

import dev.challenge.api.domain.enumeration.CustomerTypeEnum;
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
public class CreateCustomerDto {

  @NotBlank(message = "O tipo de pessoa é obrigatório")
  private CustomerTypeEnum customerType;

  @NotBlank(message = "O número do documento é obrigatório")
  @Size(min = 11, max = 14, message = "O número do documento deve ter entre 11 e 14 caracteres")
  @Pattern(regexp = "^(\\d{11})|(\\d{14})$", message = "Número do documento inválido")
  private String documentNumber;

  @NotBlank(message = "O nome é obrigatório")
  @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres")
  private String name;

  @NotBlank(message = "O e-mail é obrigatório")
  @Email(message = "Endereço de e-mail inválido")
  @Size(max = 35, message = "O e-mail deve ter no máximo 35 caracteres")
  private String email;

  @NotBlank(message = "O número de telefone é obrigatório")
  @Size(min = 11, max = 11, message = "O número de telefone deve ter 11 caracteres")
  @Pattern(regexp = "^(\\d{11})$", message = "Número de telefone inválido")
  private String phoneNumber;
}
