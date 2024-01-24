package dev.challenge.api.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.challenge.api.domain.enumeration.CustomerAccountStatusEnum;
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

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CustomerAccount")
public class CustomerAccountModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JsonBackReference
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customerId", referencedColumnName = "id")
  private CustomerModel customer;

  @Column(nullable = false, length = 10)
  private String agency;

  @Column(nullable = false, length = 15, unique = true)
  private String accountNumber;

  @Column(nullable = false)
  private BigDecimal balance = BigDecimal.ZERO;

  @Column(nullable = false)
  private Boolean isDefault = Boolean.TRUE;

  @Enumerated(EnumType.ORDINAL)
  @Column(nullable = false, length = 15)
  private CustomerAccountStatusEnum accountStatus = CustomerAccountStatusEnum.ACTIVE;
}
