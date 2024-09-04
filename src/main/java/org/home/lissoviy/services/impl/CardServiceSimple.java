package org.home.lissoviy.services.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
import org.home.lissoviy.dto.CardDTO;
import org.home.lissoviy.dto.CategoryDTO;
import org.home.lissoviy.mappers.CardMapper;
import org.home.lissoviy.mappers.CategoryMapper;
import org.home.lissoviy.models.Card;
import org.home.lissoviy.models.Category;
import org.home.lissoviy.repositories.CardRepository;
import org.home.lissoviy.repositories.CategoryRepository;
import org.home.lissoviy.services.CardService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardServiceSimple implements CardService {

  private final CardRepository cardRepository;
  private final CategoryRepository categoryRepository;

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
    updateCategories(cardDTO);

    Card savedCard = cardRepository.save(CardMapper.INSTANCE.toModel(cardDTO));
    return CardMapper.INSTANCE.toDTO(savedCard);
  }

  /**
   * Update categories to database for save or update card.
   *
   * @param cardDTO list categories.
   */
  private void updateCategories(CardDTO cardDTO) {
    List<CategoryDTO> categories = cardDTO.getCategories();

    if (categories == null || categories.isEmpty()) {
      return;
    }

    List<CategoryDTO> newCategories = categories.stream().map(categoryDTO -> {
      Long categoryDTOId = categoryDTO.getId();
      if (categoryDTOId == null || !categoryRepository.existsById(categoryDTOId)) {
        Category saved = categoryRepository.save(CategoryMapper.INSTANCE.toModel(categoryDTO));
        return CategoryMapper.INSTANCE.toDTO(saved);
      }
      Category existCategory = categoryRepository.findById(categoryDTOId).orElseThrow();
      return CategoryMapper.INSTANCE.toDTO(existCategory);
    }).toList();
    cardDTO.setCategories(newCategories);
  }

  @Override
  public CardDTO update(CardDTO cardDTO) {
    Long cardDTOId = cardDTO.getId();
    if (cardRepository.existsById(cardDTOId)) {
      updateCategories(cardDTO);

      if (cardDTO.getCategories() == null) {
        Card findedCard = cardRepository.findById(cardDTOId).orElseThrow();
        List<Category> categories = findedCard.getCategories();
        cardDTO.setCategories(categories.stream()
            .map(CategoryMapper.INSTANCE::toDTO)
            .toList());
      }

      Card updatedCard = cardRepository.save(CardMapper.INSTANCE.toModel(cardDTO));
      return CardMapper.INSTANCE.toDTO(updatedCard);
    }
    return null;
  }

  @Override
  public void delete(Long id) {
    cardRepository.deleteById(id);
  }

}
