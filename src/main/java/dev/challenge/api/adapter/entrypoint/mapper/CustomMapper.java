package dev.challenge.api.adapter.entrypoint.mapper;

// TODO: Avaliar implementação utilizando generics na interface, e utilizar um
//  builder para mapeamento manual em caso de necessidade (Ex.: lib do Jackson)
public interface CustomMapper {
  <D> D map(Object source, Class<D> destinationType);
}