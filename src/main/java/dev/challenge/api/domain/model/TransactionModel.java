package dev.challenge.api.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.challenge.api.domain.enumeration.TransactionReasonEnum;
import dev.challenge.api.domain.enumeration.TransactionTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Transaction")
public class TransactionModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JsonBackReference
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "transferId", referencedColumnName = "id")
  private TransferModel transfer;

  @JsonBackReference
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "accountId", referencedColumnName = "id")
  private CustomerAccountModel account;

  @Column(nullable = false)
  private BigDecimal amount;

  @Column(nullable = false)
  private LocalDateTime transactionDate;

  @Enumerated(EnumType.ORDINAL)
  @Column(nullable = false, length = 15)
  private TransactionTypeEnum transactionType;

  @Enumerated(EnumType.ORDINAL)
  @Column(nullable = false, length = 15)
  private TransactionReasonEnum transactionReason;
}
