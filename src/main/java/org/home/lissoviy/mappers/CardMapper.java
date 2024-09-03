package org.home.lissoviy.mappers;

import org.home.lissoviy.dto.CardDTO;
import org.home.lissoviy.models.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for convert Card to CardDTO and back.
 */
@Mapper
public interface CardMapper {
  /**
   * Mapper instance
   */
  CardMapper INSTANCE = Mappers.getMapper(CardMapper.class);

  /**
   * Convert CardDTO to Card model.
   *
   * @param cardDTO source
   * @return database model
   */
  @Mapping(source = "id", target = "id")
  @Mapping(source = "name", target = "name")
  @Mapping(source = "categories", target = "categories")
  Card toModel(CardDTO cardDTO);

  /**
   * Convert database Card to CardDTO.
   *
   * @param card database model.
   * @return CardDTO.
   */
  @Mapping(source = "id", target = "id")
  @Mapping(source = "name", target = "name")
  @Mapping(source = "categories", target = "categories")
  CardDTO toDTO(Card card);
}
