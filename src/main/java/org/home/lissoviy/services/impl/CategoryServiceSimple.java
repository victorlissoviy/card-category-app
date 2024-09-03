package org.home.lissoviy.services.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
import org.home.lissoviy.dto.CategoryDTO;
import org.home.lissoviy.mappers.CategoryMapper;
import org.home.lissoviy.models.Category;
import org.home.lissoviy.repositories.CategoryRepository;
import org.home.lissoviy.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceSimple implements CategoryService {

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
  public void save(CategoryDTO categoryDTO) {
    categoryRepository.save(CategoryMapper.INSTANCE.toModel(categoryDTO));
  }

  @Override
  public void update(CategoryDTO categoryDTO) {
    if (categoryRepository.existsById(categoryDTO.getId())) {
      categoryRepository.save(CategoryMapper.INSTANCE.toModel(categoryDTO));
    }
  }

  @Override
  public void delete(Long id) {
    categoryRepository.deleteById(id);
  }
}
