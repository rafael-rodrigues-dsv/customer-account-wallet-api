package dev.challenge.api.adapter.entrypoint.dto.customerbankaccount;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class UpdateBankAccountDto {
  @Getter(onMethod = @__(@JsonIgnore))
  @Setter(onMethod = @__(@JsonIgnore))
  private Long customerBankAccountId;
  private String agency;
  private String accountNumber;
  private BigDecimal balance;
}
