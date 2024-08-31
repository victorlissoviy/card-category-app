package org.home.lissoviy.background.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

/**
 * Just category.
 */
@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Category {

  /**
   * Id category.
   */
  @Id
  private Long id;

  /**
   * Name category.
   */
  private String name;

  /**
   * Set cards.
   */
  @ManyToMany(mappedBy = "categories")
  private Set<Card> cards;
}
