package org.home.lissoviy.background.mappers;

import org.home.lissoviy.background.dto.CategoryDTO;
import org.home.lissoviy.background.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {
  CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "name", target = "name")
  @Mapping(source = "cards", target = "cards")
  Category toModel(CategoryDTO categoryDTO);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "name", target = "name")
  @Mapping(source = "cards", target = "cards")
  CategoryDTO toDTO(Category category);
}
