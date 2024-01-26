package dev.challenge.api.domain.impl;

import dev.challenge.api.adapter.database.repository.CustomerAddressRepository;
import dev.challenge.api.domain.CustomerService;
import dev.challenge.api.domain.enumeration.CustomerAddressTypeEnum;
import dev.challenge.api.domain.model.CustomerAddressModel;
import dev.challenge.api.domain.model.CustomerModel;
import dev.challenge.api.exception.DomainRuleException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerAddressServiceImplTest {

  @Mock
  private CustomerAddressRepository customerAddressRepository;

  @Mock
  private CustomerService customerService;

  @InjectMocks
  private CustomerAddressServiceImpl customerAddressService;

  private CustomerAddressModel createCustomerAddress() {
    return CustomerAddressModel.builder()
        .id(1L)
        .customer(CustomerModel.builder().id(1L).build())
        .street("123 Main St")
        .city("City")
        .state("State")
        .zipCode("12345")
        .addressType(CustomerAddressTypeEnum.RESIDENTIAL)
        .isDefault(true)
        .build();
  }

  @Test
  void testAdd_Success() {
    // Arrange
    Long customerId = 1L;
    CustomerAddressModel customerAddress = createCustomerAddress();

    when(customerService.findById(customerId)).thenReturn(CustomerModel.builder().id(customerId).build());
    when(customerAddressRepository.exists(any())).thenReturn(false);
    when(customerAddressRepository.save(any())).thenReturn(customerAddress);

    // Act
    CustomerAddressModel result = customerAddressService.add(customerId, customerAddress);

    // Assert
    assertNotNull(result);
    assertEquals(customerAddress.getStreet(), result.getStreet());
    assertEquals(customerAddress.getCity(), result.getCity());
    assertEquals(customerAddress.getState(), result.getState());
    assertEquals(customerAddress.getZipCode(), result.getZipCode());
    assertEquals(customerAddress.getAddressType(), result.getAddressType());
    assertTrue(result.getIsDefault());
    verify(customerAddressRepository, times(1)).save(any());
  }

  @Test
  void testUpdate_Success() {
    // Arrange
    Long addressId = 1L;
    Long customerId = 1L;
    CustomerAddressModel customerAddress = createCustomerAddress();

    when(customerAddressRepository.findById(addressId)).thenReturn(Optional.of(customerAddress));
    when(customerAddressRepository.save(any())).thenReturn(customerAddress);

    // Act
    CustomerAddressModel result = customerAddressService.update(addressId, customerId, customerAddress);

    // Assert
    assertNotNull(result);
    assertEquals(customerAddress.getAddressType(), result.getAddressType());
    assertEquals(customerAddress.getStreet(), result.getStreet());
    assertEquals(customerAddress.getCity(), result.getCity());
    assertEquals(customerAddress.getState(), result.getState());
    assertEquals(customerAddress.getZipCode(), result.getZipCode());
    verify(customerAddressRepository, times(1)).save(any());
  }

  @Test
  void testUpdate_Failure_AddressNotFound() {
    // Arrange
    Long addressId = 1L;
    Long customerId = 1L;

    when(customerAddressRepository.findById(addressId)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(EntityNotFoundException.class, () -> customerAddressService.update(addressId, customerId, createCustomerAddress()));
    verify(customerAddressRepository, never()).save(any());
  }

  @Test
  void testUpdate_Failure_WrongCustomerId() {
    // Arrange
    Long addressId = 1L;
    Long correctCustomerId = 1L;
    Long wrongCustomerId = 99L;
    CustomerAddressModel customerAddress = createCustomerAddress();

    when(customerAddressRepository.findById(addressId)).thenReturn(Optional.of(customerAddress));

    // Act & Assert
    assertThrows(DomainRuleException.class, () -> customerAddressService.update(addressId, wrongCustomerId, createCustomerAddress()));
    verify(customerAddressRepository, never()).save(any());
  }

  @Test
  void testFindByIdAndVerifyCustomerId_Success() {
    // Arrange
    Long addressId = 1L;
    Long customerId = 1L;
    CustomerAddressModel customerAddress = createCustomerAddress();

    when(customerAddressRepository.findById(addressId)).thenReturn(Optional.of(customerAddress));

    // Act
    CustomerAddressModel result = customerAddressService.findByIdAndVerifyCustomerId(addressId, customerId);

    // Assert
    assertNotNull(result);
    assertEquals(addressId, result.getId());
    verify(customerAddressRepository, times(1)).findById(addressId);
  }

  @Test
  void testFindByIdAndVerifyCustomerId_Failure_AddressNotFound() {
    // Arrange
    Long addressId = 1L;
    Long customerId = 1L;

    when(customerAddressRepository.findById(addressId)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(EntityNotFoundException.class, () -> customerAddressService.findByIdAndVerifyCustomerId(addressId, customerId));
    verify(customerAddressRepository, times(1)).findById(addressId);
  }

  @Test
  void testFindByIdAndVerifyCustomerId_Failure_WrongCustomerId() {
    // Arrange
    Long addressId = 1L;
    Long correctCustomerId = 1L;
    Long wrongCustomerId = 99L;
    CustomerAddressModel customerAddress = createCustomerAddress();

    when(customerAddressRepository.findById(addressId)).thenReturn(Optional.of(customerAddress));

    // Act & Assert
    assertThrows(DomainRuleException.class, () -> customerAddressService.findByIdAndVerifyCustomerId(addressId, wrongCustomerId));
    verify(customerAddressRepository, times(1)).findById(addressId);
  }

  @Test
  void testFindAllByCustomerId_Success() {
    // Arrange
    Long customerId = 1L;
    CustomerAddressModel customerAddress = createCustomerAddress();
    List<CustomerAddressModel> addresses = Collections.singletonList(customerAddress);

    Example<CustomerAddressModel> example = Example.of(
        CustomerAddressModel.builder()
            .customer(CustomerModel.builder().id(customerId).build())
            .build()
    );

    when(customerAddressRepository.findAll(any(Example.class))).thenReturn(addresses);

    // Act
    List<CustomerAddressModel> result = customerAddressService.findAllByCustomerId(customerId);

    // Assert
    assertNotNull(result);
    assertEquals(addresses.size(), result.size());
  }
}
