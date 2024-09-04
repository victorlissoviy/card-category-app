package org.home.lissoviy.controllers;

import lombok.RequiredArgsConstructor;
import org.home.lissoviy.dto.CardDTO;
import org.home.lissoviy.services.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Card controller for get all cards, get one card, create, update and delete card.
 * <br>Request must start "/card/api"
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
@RequestMapping("/card/api")
@RestController
@RequiredArgsConstructor
public class CardController {

  private final CardService cardService;

  /**
   * Get all cards from database.
   *
   * @return all cards.
   */
  @GetMapping("/all")
  public ResponseEntity<Iterable<CardDTO>> getAllCards() {
    Iterable<CardDTO> list = cardService.getAll();
    return ResponseEntity.ok(list);
  }

  /**
   * Get one card.
   *
   * @param id id card.
   * @return fined card.
   */
  @GetMapping("/{id}")
  public ResponseEntity<CardDTO> getCardById(@PathVariable Long id) {
    CardDTO cardDTO = cardService.get(id).orElseThrow();
    return ResponseEntity.ok(cardDTO);
  }

  /**
   * Create new card and save to database. ID card will be created automatically.
   *
   * @param card card.
   * @return saved card.
   */
  @PostMapping("/create")
  public ResponseEntity<CardDTO> createCard(@RequestBody CardDTO card) {
    CardDTO savedCardDTO = cardService.save(card);
    return ResponseEntity.ok(savedCardDTO);
  }

  /**
   * Update exist card and save to database. ID card must exist in database or else do nothing.
   *
   * @param card card for update.
   * @return updated card.
   */
  @PostMapping("/update")
  public ResponseEntity<CardDTO> updateCard(@RequestBody CardDTO card) {
    CardDTO updatedCard = cardService.update(card);
    return ResponseEntity.ok(updatedCard);
  }

  /**
   * Delete card by id. If id card not exist in database do nothing.
   *
   * @param id id card.
   * @return standard answer (200).
   */
  @GetMapping("/delete/{id}")
  public ResponseEntity<Void> deleteCard(@PathVariable Long id) {
    cardService.delete(id);
    return ResponseEntity.ok().build();
  }
}
