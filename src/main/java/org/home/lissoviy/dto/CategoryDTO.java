package org.home.lissoviy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CategoryDTO that = (CategoryDTO) o;
    return Objects.equals(getName(), that.getName())
        && Objects.equals(getCards(), that.getCards());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getCards());
  }
}
