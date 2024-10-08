package org.home.lissoviy.services;

import org.home.lissoviy.dto.CategoryDTO;

import java.util.Optional;

/**
 * Interface for work witch CategoryDTO.
 */
public interface CategoryService {

  /**
   * Get all categories from database.
   *
   * @return all categories.
   */
  Iterable<CategoryDTO> getAll();

  /**
   * Get category from database by id.
   *
   * @param id id category.
   * @return founded category.
   */
  Optional<CategoryDTO> get(Long id);

  /**
   * Save category, if id saved category exist in database there will be replaced.
   *
   * @param categoryDTO category for save.
   * @return saved category.
   */
  CategoryDTO save(CategoryDTO categoryDTO);

  /**
   * Update exist category, if id category not exist, nothing not happen.
   *
   * @param categoryDTO category for update.
   * @return updated category.
   */
  CategoryDTO update(CategoryDTO categoryDTO);

  /**
   * Delete exist category, if id category not exist, nothing not happen.
   *
   * @param id id category for delete.
   */
  void delete(Long id);
}
