package org.home.lissoviy.background.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * DTO card.
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO {
  private Long id;
  private String name;
  private List<CategoryDTO> categories;
}
