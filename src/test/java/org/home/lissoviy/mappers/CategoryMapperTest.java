package org.home.lissoviy.mappers;

import org.home.lissoviy.dto.CategoryDTO;
import org.home.lissoviy.models.Card;
import org.home.lissoviy.models.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class CategoryMapperTest {
  private CategoryMapper categoryMapper;

  @BeforeEach
  void setUp() {
    categoryMapper = CategoryMapper.INSTANCE;
  }

  @Test
  void testNull() {
    Category    category    = categoryMapper.toModel(null);
    CategoryDTO categoryDTO = categoryMapper.toDTO(null);

    assertNull(category);
    assertNull(categoryDTO);
  }

  @Test
  void testToModelWithOutCards() {
    CategoryDTO categoryDTO = CategoryDTO.builder()
        .id(110L)
        .name("1Name")
        .build();

    Category category = categoryMapper.toModel(categoryDTO);

    assertNotNull(category);
    assertEquals(110L, category.getId());
    assertEquals("1Name", category.getName());
    assertNull(category.getCards());
  }

  @Test
  void testToDTO() {
    Category category = Category.builder()
        .id(92L)
        .name("cate")
        .build();

    CategoryDTO categoryDTO = categoryMapper.toDTO(category);

    assertNotNull(categoryDTO);
    assertEquals(92L, categoryDTO.getId());
    assertEquals("cate", categoryDTO.getName());
  }

  @Test
  void testToDTOWithCards() {

    Card card1 = Card.builder()
        .id(153L)
        .name("carded three")
        .build();

    Card card2 = Card.builder()
        .id(1632L)
        .name("cardYA four")
        .build();

    Category category = Category.builder()
        .id(192L)
        .name("cate four")
        .cards(Set.of(card1, card2))
        .build();

    CategoryDTO categoryDTO = categoryMapper.toDTO(category);

    assertNotNull(categoryDTO);
    assertEquals(192L, categoryDTO.getId());
    assertEquals("cate four", categoryDTO.getName());
  }
}