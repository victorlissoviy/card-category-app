package org.home.lissoviy.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
import org.home.lissoviy.dto.CategoryDTO;
import org.home.lissoviy.mappers.CategoryMapper;
import org.home.lissoviy.models.Card;
import org.home.lissoviy.models.Category;
import org.home.lissoviy.repositories.CardRepository;
import org.home.lissoviy.repositories.CategoryRepository;
import org.home.lissoviy.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceSimple implements CategoryService {

  private final CardRepository cardRepository;
  private final CategoryRepository categoryRepository;

  @Override
  public Iterable<CategoryDTO> getAll() {
    List<Category> categories = IterableUtils.toList(categoryRepository.findAll());
    return categories.stream()
        .map(CategoryMapper.INSTANCE::toDTO)
        .toList();
  }

  @Override
  public Optional<CategoryDTO> get(Long id) {
    return categoryRepository.findById(id).map(CategoryMapper.INSTANCE::toDTO);
  }

  @Override
  public CategoryDTO save(CategoryDTO categoryDTO) {
    Category savetCategory = categoryRepository.save(CategoryMapper.INSTANCE.toModel(categoryDTO));
    return CategoryMapper.INSTANCE.toDTO(savetCategory);
  }

  @Override
  public CategoryDTO update(CategoryDTO categoryDTO) {
    if (categoryRepository.existsById(categoryDTO.getId())) {
      Category updatedCategory = categoryRepository.save(CategoryMapper.INSTANCE.toModel(categoryDTO));
      return CategoryMapper.INSTANCE.toDTO(updatedCategory);
    }
    return null;
  }

  /**
   * Delete category, if category exist in cards it will be deleted from cards.
   *
   * @param id id category for delete.
   */
  @Transactional
  @Override
  public void delete(Long id) {
    Optional<Category> categoryOptional = categoryRepository.findById(id);
    if (categoryOptional.isPresent()) {

      Category category = categoryOptional.get();
      List<Card> cards = cardRepository.findAllByCategoriesContains(category);
      for (Card card : cards) {
        card.getCategories().remove(category);
        cardRepository.save(card);
      }

      categoryRepository.deleteById(id);
    }
  }
}
