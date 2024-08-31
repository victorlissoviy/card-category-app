package org.home.lissoviy.background.repositories;

import org.home.lissoviy.background.models.Card;
import org.home.lissoviy.background.models.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class CardRepositoryTest {

  @Autowired
  private CardRepository cardRepository;

  @Test
  void testSave() {
    Card card = Card.builder()
        .name("CaCard")
        .build();

    Card savedCard = cardRepository.save(card);

    assertEquals(card.getId(), savedCard.getId());
    assertEquals("CaCard", savedCard.getName());
    assertNull(savedCard.getCategories());
  }

  @Test
  void testUpdate() {
    Card card = Card.builder()
        .name("Fa sad")
        .build();

    Card savedCard = cardRepository.save(card);

    assertEquals("Fa sad", savedCard.getName());

    card.setName("Totem");

    savedCard = cardRepository.save(card);

    assertEquals("Totem", savedCard.getName());
    assertEquals(card.getId(), savedCard.getId());
    assertNull(savedCard.getCategories());
  }

  @Test
  void testDelete() {
    Card card = Card.builder()
        .name("Label")
        .build();

    Card savedCard = cardRepository.save(card);

    assertEquals("Label", savedCard.getName());

    cardRepository.delete(savedCard);

    assertTrue(cardRepository.findById(savedCard.getId()).isEmpty());
  }

  @Test
  void testFindAll() {
    Card card1 = Card.builder()
        .name("card11")
        .build();

    Card card2 = Card.builder()
        .name("card22")
        .build();

    cardRepository.save(card1);
    cardRepository.save(card2);

    Iterable<Card> cards = cardRepository.findAll();
    Iterator<Card> cardIterator = cards.iterator();

    Card actualCard1 = cardIterator.next();
    Card actualCard2 = cardIterator.next();

    assertEquals(card1.getId(), actualCard1.getId());
    assertEquals(card1.getName(), actualCard1.getName());
    assertNull(actualCard1.getCategories());

    assertEquals(card2.getId(), actualCard2.getId());
    assertEquals(card2.getName(), actualCard2.getName());
    assertNull(actualCard2.getCategories());
  }

  @Test
  void testSaveWithCategories() {
    Category category1 = Category.builder()
        .name("Cat name")
        .build();

    Category category2 = Category.builder()
        .name("Cat name 2")
        .build();

    Card card = Card.builder()
        .name("card single")
        .categories(List.of(category1, category2))
        .build();

    Card savedCard = cardRepository.save(card);

    assertEquals(card.getId(), savedCard.getId());
    assertEquals("card single", savedCard.getName());

    List<Category> categories = savedCard.getCategories();
    assertNotNull(categories);
    assertEquals(2, categories.size());

    Category actualCategory1 = categories.get(0);
    Category actualCategory2 = categories.get(1);

    assertEquals(category1.getId(), actualCategory1.getId());
    assertEquals("Cat name", actualCategory1.getName());
    assertNull(actualCategory1.getCards());

    assertEquals(category2.getId(), actualCategory2.getId());
    assertEquals("Cat name 2", actualCategory2.getName());
    assertNull(actualCategory2.getCards());
  }
}