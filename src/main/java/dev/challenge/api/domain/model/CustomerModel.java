package dev.challenge.api.domain.model;

import dev.challenge.api.domain.enumeration.CustomerTypeEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Customer")
public class CustomerModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long customerId;

  @Enumerated(EnumType.ORDINAL)
  @Column(nullable = false, length = 15)
  private CustomerTypeEnum customerType;

  @Column(nullable = false, length = 20, unique = true)
  private String documentNumber;

  @Column(nullable = false, length = 50)
  private String name;

  @Column(nullable = true, length = 35)
  private String email;

  @Column(nullable = true, length = 15)
  private String phoneNumber;

  @Column(nullable = true)
  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<CustomerAddressModel> customerAddresses;

  @Column(nullable = true)
  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<CustomerAddressModel> customerBankAccount;
}
