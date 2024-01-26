package dev.challenge.api.adapter.entrypoint.dto.filter;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FindByIdAndCustomerIdFilterDtoTest {

  @Test
  void testBuilder() {
    // Arrange
    FindByIdAndCustomerIdFilterDto filterDto = FindByIdAndCustomerIdFilterDto.builder()
        .id(1L)
        .customerId(2L)
        .build();

    // Assert
    assertThat(filterDto.getId()).isEqualTo(1L);
    assertThat(filterDto.getCustomerId()).isEqualTo(2L);
  }

  @Test
  void testGettersAndSetters() {
    // Arrange
    FindByIdAndCustomerIdFilterDto filterDto = new FindByIdAndCustomerIdFilterDto();

    // Act
    filterDto.setId(1L);
    filterDto.setCustomerId(2L);

    // Assert
    assertThat(filterDto.getId()).isEqualTo(1L);
    assertThat(filterDto.getCustomerId()).isEqualTo(2L);
  }

  @Test
  void testNoArgsConstructor() {
    // Act
    FindByIdAndCustomerIdFilterDto filterDto = new FindByIdAndCustomerIdFilterDto();

    // Assert
    assertThat(filterDto).isNotNull();
  }

  @Test
  void testAllArgsConstructor() {
    // Arrange
    FindByIdAndCustomerIdFilterDto filterDto = new FindByIdAndCustomerIdFilterDto(1L, 2L);

    // Assert
    assertThat(filterDto).isNotNull();
    assertThat(filterDto.getId()).isEqualTo(1L);
    assertThat(filterDto.getCustomerId()).isEqualTo(2L);
  }
}
