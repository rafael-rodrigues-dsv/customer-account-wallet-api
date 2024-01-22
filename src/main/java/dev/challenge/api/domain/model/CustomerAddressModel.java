package dev.challenge.api.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.challenge.api.domain.enumeration.CustomerAddressTypeEnum;
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

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CustomerAddress")
public class CustomerAddressModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JsonBackReference
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customerId", referencedColumnName = "id")
  private CustomerModel customer;

  @Column(nullable = false, length = 100)
  private String street;

  @Column(nullable = false, length = 100)
  private String city;

  @Column(nullable = false, length = 30)
  private String state;

  @Column(nullable = false, length = 10)
  private String zipCode;

  @Column(nullable = false)
  private Boolean isDefault = true;

  @Enumerated(EnumType.ORDINAL)
  @Column(nullable = false, length = 15)
  private CustomerAddressTypeEnum addressType;
}
