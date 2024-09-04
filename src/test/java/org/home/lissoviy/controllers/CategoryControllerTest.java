package org.home.lissoviy.controllers;

import org.home.lissoviy.dto.CategoryDTO;
import org.home.lissoviy.services.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CategoryController.class)
class CategoryControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CategoryService categoryService;

  @Test
  @WithMockUser(username = "user")
  void getAllCategories_ShouldReturnOkAndListOfCategories() throws Exception {
    CategoryDTO categoryDTO1 = CategoryDTO.builder()
        .id(10L)
        .name("Test Category 1")
        .build();

    CategoryDTO categoryDTO2 = CategoryDTO.builder()
        .id(12L)
        .name("Test Category 2")
        .build();

    when(categoryService.getAll()).thenReturn(List.of(categoryDTO1, categoryDTO2));

    mockMvc.perform(get("/category/api/all"))
        .andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  void getAllCategories_ShouldReturnUnauthorized() throws Exception {
    mockMvc.perform(get("/category/api/all"))
        .andExpect(status().isUnauthorized())
        .andDo(print());
  }

  @Test
  @WithMockUser(username = "user")
  void getCategoryById_ShouldReturnCategory_WhenCategoryExists() throws Exception {
    CategoryDTO categoryDTO = CategoryDTO.builder()
        .id(12L)
        .name("Test Category")
        .build();

    when(categoryService.get(categoryDTO.getId())).thenReturn(Optional.of(categoryDTO));

    mockMvc.perform(get("/category/api/12"))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Category"));
  }

  @Test
  @WithMockUser(username = "user")
  void createCategory_ShouldReturnCreatedCategory() throws Exception {
    CategoryDTO categoryDTO = CategoryDTO.builder()
        .id(15L)
        .name("New Category")
        .build();

    String newCategoryJson = """
                {
                    "name": "New Category"
                }
                """;

    when(categoryService.save(any())).thenReturn(categoryDTO);

    mockMvc.perform(post("/category/api/create")
            .with(SecurityMockMvcRequestPostProcessors.csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(newCategoryJson))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("New Category"))
        .andDo(print());
  }

  @Test
  @WithMockUser(username = "user")
  void updateCategory_ShouldReturnUpdatedCategory() throws Exception {
    CategoryDTO categoryDTO = CategoryDTO.builder()
        .id(10L)
        .name("Updated Category")
        .build();

    String updateCategoryJson = """
                {
                    "id": 10,
                    "name": "Updated Category"
                }
                """;

    when(categoryService.update(any(CategoryDTO.class))).thenReturn(categoryDTO);

    mockMvc.perform(post("/category/api/update")
            .with(SecurityMockMvcRequestPostProcessors.csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(updateCategoryJson))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Category"))
        .andDo(print());
  }

  @Test
  @WithMockUser(username = "user")
  void deleteCategory_ShouldReturnOkStatus() throws Exception {
    mockMvc.perform(get("/category/api/delete/1"))
        .andExpect(status().isOk())
        .andDo(print());
  }
}