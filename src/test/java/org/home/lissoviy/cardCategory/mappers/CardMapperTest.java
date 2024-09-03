package org.home.lissoviy.mappers;

import org.home.lissoviy.dto.CardDTO;
import org.home.lissoviy.dto.CategoryDTO;
import org.home.lissoviy.models.Card;
import org.home.lissoviy.models.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class CardMapperTest {
  private CardMapper cardMapper;

  @BeforeEach
  void setUp() {
    cardMapper = CardMapper.INSTANCE;
  }

  @Test
  void testNull() {
    Card    card    = cardMapper.toModel(null);
    CardDTO cardDTO = cardMapper.toDTO(null);

    assertNull(card);
    assertNull(cardDTO);
  }

  @Test
  void testToModelWithOutCategories() {
    CardDTO cardDTO = CardDTO.builder()
        .id(10L)
        .name("Name")
        .build();

    Card card = cardMapper.toModel(cardDTO);

    assertEquals(10L, card.getId());
    assertEquals("Name", card.getName());
    assertNull(card.getCategories());
  }

  @Test
  void testToModelWithCategories() {
    CategoryDTO cat1 = CategoryDTO.builder()
        .id(20L)
        .name("Cat one")
        .build();

    CategoryDTO cat2 = CategoryDTO.builder()
        .id(202L)
        .name("Dog two")
        .build();

    CardDTO cardDTO = CardDTO.builder()
        .id(10L)
        .name("Name")
        .categories(List.of(cat1, cat2))
        .build();

    Card card = cardMapper.toModel(cardDTO);

    assertEquals(10L, card.getId());
    assertEquals("Name", card.getName());

    List<Category> categories = card.getCategories();
    assertNotNull(categories);
    assertEquals(2, categories.size());

    Category actCat1 = categories.get(0);
    Category actCat2 = categories.get(1);

    assertEquals(20L, actCat1.getId());
    assertEquals("Cat one", actCat1.getName());
    assertNull(actCat1.getCards());

    assertEquals(202L, actCat2.getId());
    assertEquals("Dog two", actCat2.getName());
    assertNull(actCat2.getCards());
  }

  @Test
  void testToDTOWithOutCategories() {
    Card card = Card.builder()
        .id(12L)
        .name("card super name")
        .build();

    CardDTO cardDTO = cardMapper.toDTO(card);

    assertEquals(12L, cardDTO.getId());
    assertEquals("card super name", cardDTO.getName());
    assertNull(cardDTO.getCategories());
  }

  @Test
  void testToDTOWithCategories() {
    Category cat1 = Category.builder()
        .id(21L)
        .name("Nyam Nyam")
        .build();

    Category cat2 = Category.builder()
        .id(215L)
        .name("Nyame Nyame")
        .build();

    Card card = Card.builder()
        .id(12L)
        .name("super card name")
        .categories(List.of(cat1, cat2))
        .build();

    CardDTO cardDTO = cardMapper.toDTO(card);

    assertEquals(12L, cardDTO.getId());
    assertEquals("super card name", cardDTO.getName());

    List<Category> categories = card.getCategories();
    assertNotNull(categories);

    Category actCat1 = categories.get(0);
    Category actCat2 = categories.get(1);

    assertEquals(21L, actCat1.getId());
    assertEquals("Nyam Nyam", actCat1.getName());
    assertNull(actCat1.getCards());

    assertEquals(215L, actCat2.getId());
    assertEquals("Nyame Nyame", actCat2.getName());
    assertNull(actCat2.getCards());
  }
}