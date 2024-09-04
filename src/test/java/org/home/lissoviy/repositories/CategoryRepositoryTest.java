package org.home.lissoviy.repositories;

import org.home.lissoviy.models.Card;
import org.home.lissoviy.models.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Iterator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class CategoryRepositoryTest {
  
  @Autowired
  private CategoryRepository categoryRepository;

  @Test
  void testSave() {
    Category category = Category.builder()
        .name("CaCard")
        .build();

    Category savedCard = categoryRepository.save(category);

    assertEquals(category.getId(), savedCard.getId());
    assertEquals("CaCard", savedCard.getName());
    assertNull(savedCard.getCards());
  }

  @Test
  void testUpdate() {
    Category category = Category.builder()
        .name("Fa sad")
        .build();

    Category savedCard = categoryRepository.save(category);

    assertEquals("Fa sad", savedCard.getName());

    category.setName("Totem");

    savedCard = categoryRepository.save(category);

    assertEquals("Totem", savedCard.getName());
    assertEquals(category.getId(), savedCard.getId());
    assertNull(savedCard.getCards());
  }

  @Test
  void testDelete() {
    Category category = Category.builder()
        .name("Label")
        .build();

    Category savedCard = categoryRepository.save(category);

    assertEquals("Label", savedCard.getName());

    categoryRepository.delete(savedCard);

    assertTrue(categoryRepository.findById(savedCard.getId()).isEmpty());
  }

  @Test
  void testFindAll() {
    Category card1 = Category.builder()
        .name("card11")
        .build();

    Category card2 = Category.builder()
        .name("card22")
        .build();

    categoryRepository.save(card1);
    categoryRepository.save(card2);

    Iterable<Category> cards = categoryRepository.findAll();
    Iterator<Category> cardIterator = cards.iterator();

    Category actualCard1 = cardIterator.next();
    Category actualCard2 = cardIterator.next();

    assertEquals(card1.getId(), actualCard1.getId());
    assertEquals(card1.getName(), actualCard1.getName());
    assertNull(actualCard1.getCards());

    assertEquals(card2.getId(), actualCard2.getId());
    assertEquals(card2.getName(), actualCard2.getName());
    assertNull(actualCard2.getCards());
  }

  @Test
  void testSaveWithCards() {
    Card card1 = Card.builder()
        .name("Cat name")
        .build();

    Card card2 = Card.builder()
        .name("Cat name 2")
        .build();

    Category category = Category.builder()
        .name("category single")
        .cards(Set.of(card1, card2))
        .build();

    Category savedCard = categoryRepository.save(category);

    assertEquals(category.getId(), savedCard.getId());
    assertEquals("category single", savedCard.getName());

    Set<Card> cards = savedCard.getCards();
    assertNotNull(cards);
    assertEquals(2, cards.size());

    assertTrue(cards.contains(card1));
    assertTrue(cards.contains(card2));
  }
}