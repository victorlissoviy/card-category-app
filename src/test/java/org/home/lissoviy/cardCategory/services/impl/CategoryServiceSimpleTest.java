package org.home.lissoviy.services.impl;

import org.home.lissoviy.dto.CategoryDTO;
import org.home.lissoviy.mappers.CategoryMapper;
import org.home.lissoviy.models.Category;
import org.home.lissoviy.repositories.CategoryRepository;
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
class CategoryServiceSimpleTest {

  @Mock
  CategoryRepository categoryRepository;

  @InjectMocks
  CategoryServiceSimple categoryService;

  @Test
  void saveCategory() {
    Category category = Category.builder()
        .build();

    CategoryDTO categoryDTO = CategoryMapper.INSTANCE.toDTO(category);
    categoryService.save(categoryDTO);

    verify(categoryRepository, times(1)).save(category);
  }

  @Test
  void getCategory() {
    Category category = Category.builder()
        .name("test category")
        .build();
    Long categoryId = category.getId();

    when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

    Optional<CategoryDTO> actualCategory = categoryService.get(categoryId);

    assertTrue(actualCategory.isPresent());

    CategoryDTO categoryDTO = actualCategory.get();
    assertEquals("test category", categoryDTO.getName());
    assertNull(categoryDTO.getCards());

    verify(categoryRepository, never()).save(category);
    verify(categoryRepository, times(1)).findById(categoryId);
  }

  @Test
  void updateCategory() {
    Category category = Category.builder()
        .name("before name")
        .build();
    Long categoryId = category.getId();

    CategoryDTO categoryDTO = CategoryMapper.INSTANCE.toDTO(category);
    categoryService.update(categoryDTO);

    verify(categoryRepository, never()).save(category);
    verify(categoryRepository, times(1)).existsById(categoryId);

    when(categoryRepository.existsById(categoryId)).thenReturn(true);
    categoryService.update(categoryDTO);

    verify(categoryRepository, times(2)).existsById(categoryId);
    verify(categoryRepository, times(1)).save(category);
  }

  @Test
  void deleteCategory() {
    categoryService.delete(10L);

    verify(categoryRepository, times(1)).deleteById(10L);
  }

  @Test
  void getAllCategories() {
    Category category1 = Category.builder()
        .name("Category 1")
        .build();

    Category category2 = Category.builder()
        .name("Category 2")
        .build();

    when(categoryRepository.findAll()).thenReturn(List.of(category1, category2));

    Iterable<CategoryDTO> iterable = categoryService.getAll();
    Iterator<CategoryDTO> iterator = iterable.iterator();

    CategoryDTO categoryDTO1 = iterator.next();
    CategoryDTO categoryDTO2 = iterator.next();

    assertEquals("Category 1", categoryDTO1.getName());
    assertEquals(category1.getId(), categoryDTO2.getId());
    assertEquals("Category 2", categoryDTO2.getName());
    assertEquals(category2.getId(), categoryDTO1.getId());

    verify(categoryRepository, times(1)).findAll();
    verify(categoryRepository, never()).findById(any());
  }
}