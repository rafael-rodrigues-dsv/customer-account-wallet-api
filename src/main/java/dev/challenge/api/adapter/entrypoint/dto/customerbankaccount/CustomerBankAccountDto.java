package dev.challenge.api.adapter.entrypoint.dto.customerbankaccount;

import dev.challenge.api.domain.model.CustomerModel;
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
public class CustomerBankAccountDto {
  private Long id;
  private CustomerModel customer;
  private String agency;
  private String accountNumber;
  private BigDecimal balance;
  private Boolean isActive;
}
