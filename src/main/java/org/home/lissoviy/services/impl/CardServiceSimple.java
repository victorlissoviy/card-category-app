package org.home.lissoviy.services.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
import org.home.lissoviy.dto.CardDTO;
import org.home.lissoviy.mappers.CardMapper;
import org.home.lissoviy.models.Card;
import org.home.lissoviy.repositories.CardRepository;
import org.home.lissoviy.services.CardService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardServiceSimple implements CardService {

  private final CardRepository cardRepository;

  @Override
  public Iterable<CardDTO> getAll() {
    List<Card> cards = IterableUtils.toList(cardRepository.findAll());
    return cards.stream()
        .map(CardMapper.INSTANCE::toDTO)
        .toList();
  }

  @Override
  public Optional<CardDTO> get(Long id) {
    return cardRepository.findById(id).map(CardMapper.INSTANCE::toDTO);
  }

  @Override
  public CardDTO save(CardDTO cardDTO) {
    Card savedCard = cardRepository.save(CardMapper.INSTANCE.toModel(cardDTO));
    return CardMapper.INSTANCE.toDTO(savedCard);
  }

  @Override
  public void update(CardDTO cardDTO) {
    if (cardRepository.existsById(cardDTO.getId())) {
      cardRepository.save(CardMapper.INSTANCE.toModel(cardDTO));
    }
  }

  @Override
  public void delete(Long id) {
    cardRepository.deleteById(id);
  }

}
