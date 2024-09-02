package org.home.lissoviy.background.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

/**
 * DTO card.
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO {
  /**
   * Identify Card DTO.
   */
  private Long id;

  /**
   * Name Card DTO.
   */
  private String name;

  /**
   * List categories DTO.
   */
  private List<CategoryDTO> categories;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CardDTO cardDTO = (CardDTO) o;
    return Objects.equals(name, cardDTO.name);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(name);
  }
}
