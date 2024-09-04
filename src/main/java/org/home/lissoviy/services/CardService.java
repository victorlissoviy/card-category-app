package org.home.lissoviy.services;

import org.home.lissoviy.dto.CardDTO;

import java.util.Optional;

/**
 * Interface for work witch CardDTO.
 */
public interface CardService {

  /**
   * Get all cards from database.
   * 
   * @return all cards.
   */
  Iterable<CardDTO> getAll();

  /**
   * Get card from database by id.
   *
   * @param id id card.
   * @return founded card.
   */
  Optional<CardDTO> get(Long id);

  /**
   * Save card, if id saved card exist in database there will be replaced.
   *
   * @param cardDTO card for save.
   * @return saved card.
   */
  CardDTO save(CardDTO cardDTO);

  /**
   * Update exist card, if id card not exist, nothing not happen.
   *
   * @param cardDTO card for update.
   * @return updated card.
   */
  CardDTO update(CardDTO cardDTO);

  /**
   * Delete exist card, if id card not exist, nothing not happen.
   *
   * @param id id card for delete.
   */
  void delete(Long id);
}
