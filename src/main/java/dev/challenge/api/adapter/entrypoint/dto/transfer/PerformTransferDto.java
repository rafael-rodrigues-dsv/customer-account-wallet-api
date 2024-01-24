package dev.challenge.api.adapter.entrypoint.dto.transfer;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

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
public class PerformTransferDto {

  @NotNull(message = "Debit Account ID is mandatory")
  private Long debitAccountId;

  @NotNull(message = "Credit Account ID is mandatory")
  private Long creditAccountId;

  @NotNull(message = "Amount is mandatory")
  @Positive(message = "Amount must be greater than zero")
  private BigDecimal amount;
}
