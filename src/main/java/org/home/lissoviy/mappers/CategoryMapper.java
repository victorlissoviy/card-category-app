package org.home.lissoviy.mappers;

import org.home.lissoviy.dto.CategoryDTO;
import org.home.lissoviy.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper to convert database model Category to CategoryDTO.
 */
@Mapper
public interface CategoryMapper {
  /**
   * Instance for converting.
   */
  CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

  /**
   * Convert CategoryDTO to database model Category.
   *
   * @param categoryDTO Category DTO.
   * @return database model Category.
   */
  @Mapping(source = "id", target = "id")
  @Mapping(source = "name", target = "name")
  @Mapping(source = "cards", target = "cards")
  Category toModel(CategoryDTO categoryDTO);

  /**
   * Convert database Category to CategoryDTO.
   *
   * @param category database Category.
   * @return Category DTO.
   */
  @Mapping(source = "id", target = "id")
  @Mapping(source = "name", target = "name")
  @Mapping(source = "cards", target = "cards")
  CategoryDTO toDTO(Category category);
}
