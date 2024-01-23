package dev.challenge.api.adapter.entrypoint.dto.transfer;

import dev.challenge.api.adapter.entrypoint.dto.customeraccount.CustomerAccountDto;
import dev.challenge.api.adapter.entrypoint.dto.transaction.TransactionDto;
import dev.challenge.api.domain.enumeration.TransferStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TransferDto {
  private Long id;
  private CustomerAccountDto debitAccount;
  private CustomerAccountDto creditAccount;
  private BigDecimal amount;
  private LocalDateTime transferDate;
  private TransferStatusEnum transferStatus;
  private Set<TransactionDto> transactions;
}
