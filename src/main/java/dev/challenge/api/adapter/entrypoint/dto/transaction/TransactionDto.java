package dev.challenge.api.adapter.entrypoint.dto.transaction;

import dev.challenge.api.adapter.entrypoint.dto.customeraccount.CustomerAccountDto;
import dev.challenge.api.domain.enumeration.TransactionReasonEnum;
import dev.challenge.api.domain.enumeration.TransactionTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TransactionDto {
  private Long id;
  private CustomerAccountDto account;
  private BigDecimal amount;
  private LocalDateTime transactionDate;
  private TransactionTypeEnum transactionType;
  private TransactionReasonEnum transactionReason;
}
