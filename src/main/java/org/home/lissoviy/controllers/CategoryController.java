package org.home.lissoviy.controllers;

import lombok.RequiredArgsConstructor;
import org.home.lissoviy.dto.CategoryDTO;
import org.home.lissoviy.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Category controller for get all categories, get one category, create, update and delete category.
 * <br>Request must start "/category/api"
 * <br>
 * <br>post:
 * <br>  create "/create"
 * <br>  update "/update"
 * <br>
 * <br>get:
 * <br>  one "/{id}"
 * <br>  all "/all"
 * <br>  delete "/delete/{id}"
 */

@RequestMapping("/category/api")
@RestController
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;

  /**
   * Get all categories from database.
   *
   * @return all cards.
   */
  @GetMapping("/all")
  public ResponseEntity<Iterable<CategoryDTO>> getAllCategories() {
    Iterable<CategoryDTO> list = categoryService.getAll();
    return ResponseEntity.ok(list);
  }

  /**
   * Get one category.
   *
   * @param id id card.
   * @return fined card.
   */
  @GetMapping("/{id}")
  public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
    CategoryDTO categoryDTO = categoryService.get(id).orElseThrow();
    return ResponseEntity.ok(categoryDTO);
  }

  /**
   * Create new category and save to database. ID category will be created automatically.
   *
   * @param category category.
   * @return saved category.
   */
  @PostMapping("/create")
  public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO category) {
    CategoryDTO savedCategoryDTO = categoryService.save(category);
    return ResponseEntity.ok(savedCategoryDTO);
  }

  /**
   * Update exist category and save to database. ID category must exist in database or else do nothing.
   *
   * @param category category for update.
   * @return updated category.
   */
  @PostMapping("/update")
  public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO category) {
    CategoryDTO updatedCategory = categoryService.update(category);
    return ResponseEntity.ok(updatedCategory);
  }

  /**
   * Delete category by id. If id category not exist in database do nothing.
   *
   * @param id id category.
   * @return standard answer (200).
   */
  @GetMapping("/delete/{id}")
  public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
    categoryService.delete(id);
    return ResponseEntity.ok().build();
  }
}
