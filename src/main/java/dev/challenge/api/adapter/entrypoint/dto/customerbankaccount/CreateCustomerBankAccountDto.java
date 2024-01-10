package dev.challenge.api.adapter.entrypoint.dto.customerbankaccount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CreateCustomerBankAccountDto {
  private Long customerId;
  private String agency;
  private String accountNumber;
  private BigDecimal balance;
}
