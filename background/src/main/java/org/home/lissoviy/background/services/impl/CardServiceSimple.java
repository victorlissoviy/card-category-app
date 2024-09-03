package org.home.lissoviy.background.services.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
import org.home.lissoviy.background.dto.CardDTO;
import org.home.lissoviy.background.mappers.CardMapper;
import org.home.lissoviy.background.models.Card;
import org.home.lissoviy.background.repositories.CardRepository;
import org.home.lissoviy.background.services.CardService;
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
  public void save(CardDTO cardDTO) {
    cardRepository.save(CardMapper.INSTANCE.toModel(cardDTO));
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
