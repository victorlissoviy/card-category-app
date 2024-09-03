package org.home.lissoviy.background.services.impl;

import org.home.lissoviy.background.dto.CardDTO;
import org.home.lissoviy.background.mappers.CardMapper;
import org.home.lissoviy.background.models.Card;
import org.home.lissoviy.background.repositories.CardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardServiceSimpleTest {

  @Mock
  CardRepository cardRepository;

  @InjectMocks
  CardServiceSimple cardService;

  @Test
  void saveCard() {
    Card card = Card.builder()
        .build();

    CardDTO cardDTO = CardMapper.INSTANCE.toDTO(card);
    cardService.save(cardDTO);

    verify(cardRepository, times(1)).save(card);
  }

  @Test
  void getCard() {
    Card card = Card.builder()
        .name("test card")
        .build();
    Long cardId = card.getId();

    when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));

    Optional<CardDTO> actualCard = cardService.get(cardId);

    assertTrue(actualCard.isPresent());

    CardDTO cardDTO = actualCard.get();
    assertEquals("test card", cardDTO.getName());
    assertNull(cardDTO.getCategories());

    verify(cardRepository, never()).save(card);
    verify(cardRepository, times(1)).findById(cardId);
  }

  @Test
  void updateCard() {
    Card card = Card.builder()
        .name("before name")
        .build();
    Long cardId = card.getId();

    CardDTO cardDTO = CardMapper.INSTANCE.toDTO(card);
    cardService.update(cardDTO);

    verify(cardRepository, never()).save(card);
    verify(cardRepository, times(1)).existsById(cardId);

    when(cardRepository.existsById(cardId)).thenReturn(true);
    cardService.update(cardDTO);

    verify(cardRepository, times(2)).existsById(cardId);
    verify(cardRepository, times(1)).save(card);
  }

  @Test
  void deleteCard() {
    cardService.delete(10L);

    verify(cardRepository, times(1)).deleteById(10L);
  }

  @Test
  void getAllCards() {
    Card card1 = Card.builder()
        .name("Card 1")
        .build();

    Card card2 = Card.builder()
        .name("Card 2")
        .build();

    when(cardRepository.findAll()).thenReturn(List.of(card1, card2));

    Iterable<CardDTO> iterable = cardService.getAll();
    Iterator<CardDTO> iterator = iterable.iterator();

    CardDTO cardDTO1 = iterator.next();
    CardDTO cardDTO2 = iterator.next();

    assertEquals("Card 1", cardDTO1.getName());
    assertEquals(card1.getId(), cardDTO2.getId());
    assertEquals("Card 2", cardDTO2.getName());
    assertEquals(card2.getId(), cardDTO1.getId());

    verify(cardRepository, times(1)).findAll();
    verify(cardRepository, never()).findById(any());
  }
}