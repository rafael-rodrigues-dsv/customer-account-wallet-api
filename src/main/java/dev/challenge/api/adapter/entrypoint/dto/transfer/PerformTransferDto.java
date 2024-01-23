package dev.challenge.api.adapter.entrypoint.dto.transfer;

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
public class PerformTransferDto {
  private Long debitAccountId;
  private Long creditAccountId;
  private BigDecimal amount;
}
