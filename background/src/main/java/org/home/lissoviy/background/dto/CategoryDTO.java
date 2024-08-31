package org.home.lissoviy.background.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

/**
 * DTO Category.
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
  /**
   * Identify Category DTO.
   */
  private Long id;

  /**
   * Name Category DTO.
   */
  private String name;

  /**
   * Set cards DTO.
   */
  private Set<CardDTO> cards;
}
