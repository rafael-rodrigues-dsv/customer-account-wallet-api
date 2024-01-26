package dev.challenge.api.adapter.entrypoint.mapper;

import dev.challenge.api.adapter.entrypoint.dto.customer.CustomerDto;
import dev.challenge.api.adapter.entrypoint.mapper.impl.CustomMapperImpl;
import dev.challenge.api.domain.model.CustomerModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CustomMapperTest {

  @InjectMocks
  private CustomMapperImpl customMapper;

  @BeforeEach
  void setUp() {
    customMapper = new CustomMapperImpl();
  }

  @Test
  void testMapModelToDto() {
    CustomerModel model = CustomerModel.builder().id(1L).build();

    CustomerDto dto = customMapper.map(model, CustomerDto.class);

    assertNotNull(dto);
    assertEquals(model.getId(), dto.getId());
  }

  @Test
  void testMapDtoToModel() {
    CustomerDto dto = CustomerDto.builder().id(1L).build();

    CustomerModel model = customMapper.map(dto, CustomerModel.class);

    assertNotNull(model);
    assertEquals(dto.getId(), model.getId());
  }
}