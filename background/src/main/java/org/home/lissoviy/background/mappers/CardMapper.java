package org.home.lissoviy.background.mappers;

import org.home.lissoviy.background.dto.CardDTO;
import org.home.lissoviy.background.models.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CardMapper {
  CardMapper INSTANCE = Mappers.getMapper(CardMapper.class);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "name", target = "name")
  @Mapping(source = "categories", target = "categories")
  Card toModel(CardDTO cardDTO);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "name", target = "name")
  @Mapping(source = "categories", target = "categories")
  CardDTO toDTO(Card card);
}
