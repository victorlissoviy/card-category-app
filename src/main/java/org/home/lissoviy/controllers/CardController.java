package org.home.lissoviy.controllers;

import lombok.RequiredArgsConstructor;
import org.home.lissoviy.dto.CardDTO;
import org.home.lissoviy.services.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/card/api")
@RestController
@RequiredArgsConstructor
public class CardController {

  private final CardService cardService;

  @GetMapping("/all")
  public ResponseEntity<Iterable<CardDTO>> getAllCards() {
    Iterable<CardDTO> list = cardService.getAll();
    return ResponseEntity.ok(list);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CardDTO> getCardById(@PathVariable Long id) {
    CardDTO cardDTO = cardService.get(id).orElseThrow();
    return ResponseEntity.ok(cardDTO);
  }


}
