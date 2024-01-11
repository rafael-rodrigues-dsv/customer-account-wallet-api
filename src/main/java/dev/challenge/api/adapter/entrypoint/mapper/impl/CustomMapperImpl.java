package dev.challenge.api.adapter.entrypoint.mapper.impl;

import dev.challenge.api.adapter.entrypoint.mapper.CustomMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomMapperImpl implements CustomMapper {

  private final ModelMapper mapper;

  public CustomMapperImpl() {
    this.mapper = new ModelMapper();
    this.mapper.getConfiguration().setAmbiguityIgnored(true);
  }

  @Override
  public <D> D map(Object source, Class<D> destinationType) {
    return this.mapper.map(source, destinationType);
  }
}
