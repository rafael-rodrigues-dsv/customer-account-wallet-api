package dev.challenge.api.adapter.entrypoint.dto.customeraccount;

import dev.challenge.api.domain.enumeration.CustomerAccountStatusEnum;
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
public class CustomerAccountDto {
  private Long id;
  private String agency;
  private String accountNumber;
  private BigDecimal balance;
  private Boolean isDefault;
  private CustomerAccountStatusEnum accountStatus;
}
