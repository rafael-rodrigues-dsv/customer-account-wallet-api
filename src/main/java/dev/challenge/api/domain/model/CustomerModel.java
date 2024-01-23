package dev.challenge.api.domain.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
  private Long id;

  @Enumerated(EnumType.ORDINAL)
  @Column(nullable = false, length = 15)
  private CustomerTypeEnum customerType;

  @Column(nullable = false, length = 20)
  private String documentNumber;

  @Column(nullable = false, length = 150)
  private String name;

  @Column(length = 35)
  private String email;

  @Column(length = 15)
  private String phoneNumber;

  @JsonManagedReference
  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<CustomerAddressModel> customerAddresses;

  @JsonManagedReference
  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<CustomerAccountModel> customerAccounts;
}
