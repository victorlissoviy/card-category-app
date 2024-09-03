package org.home.lissoviy.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

/**
 * Just card.
 */
@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Card {
  /**
   * Id card.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * Name card.
   */
  private String name;

  /**
   * List categories.
   */
  @ManyToMany
  @JoinTable(name = "card_category",
             joinColumns = @JoinColumn(name = "card_id"),
             inverseJoinColumns = @JoinColumn(name = "category_id"))
  private List<Category> categories;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Card card = (Card) o;
    return Objects.equals(getName(), card.getName())
        && Objects.equals(getCategories(), card.getCategories());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getCategories());
  }
}
