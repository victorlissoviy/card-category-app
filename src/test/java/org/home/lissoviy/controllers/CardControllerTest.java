package org.home.lissoviy.controllers;

import org.home.lissoviy.dto.CardDTO;
import org.home.lissoviy.services.CardService;
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

@WebMvcTest(controllers = CardController.class)
class CardControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CardService cardService;

  @Test
  @WithMockUser(username = "user")
  void getAllCards_ShouldReturnOkAndListOfCards() throws Exception {
    CardDTO cardDTO1 = CardDTO.builder()
        .id(10L)
        .name("Test Card 1")
        .build();

    CardDTO cardDTO2 = CardDTO.builder()
        .id(12L)
        .name("Test Card 2")
        .build();

    when(cardService.getAll()).thenReturn(List.of(cardDTO1, cardDTO2));

    mockMvc.perform(get("/card/api/all"))
        .andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  void getAllCards_ShouldReturnUnauthorized() throws Exception {
    mockMvc.perform(get("/card/api/all"))
        .andExpect(status().isUnauthorized())
        .andDo(print());
  }

  @Test
  @WithMockUser(username = "user")
  void getCardById_ShouldReturnCard_WhenCardExists() throws Exception {
    CardDTO cardDTO = CardDTO.builder()
        .id(12L)
        .name("Test Card")
        .build();

    when(cardService.get(cardDTO.getId())).thenReturn(Optional.of(cardDTO));

    mockMvc.perform(get("/card/api/12"))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Card"));
  }

  @Test
  @WithMockUser(username = "user")
  void createCard_ShouldReturnCreatedCard() throws Exception {
    CardDTO cardDTO = CardDTO.builder()
        .id(15L)
        .name("New Card")
        .build();

    String newCardJson = """
                {
                    "name": "New Card"
                }
                """;

    when(cardService.save(any())).thenReturn(cardDTO);

    mockMvc.perform(post("/card/api/create")
            .with(SecurityMockMvcRequestPostProcessors.csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(newCardJson))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("New Card"))
        .andDo(print());
  }

  @Test
  @WithMockUser(username = "user")
  void updateCard_ShouldReturnUpdatedCard() throws Exception {
    CardDTO cardDTO = CardDTO.builder()
        .id(10L)
        .name("Updated Card")
        .build();

    String updateCardJson = """
                {
                    "id": 10,
                    "name": "Updated Card"
                }
                """;

    when(cardService.update(any(CardDTO.class))).thenReturn(cardDTO);

    mockMvc.perform(post("/card/api/update")
            .with(SecurityMockMvcRequestPostProcessors.csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(updateCardJson))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Card"))
        .andDo(print());
  }

  @Test
  @WithMockUser(username = "user")
  void deleteCard_ShouldReturnOkStatus() throws Exception {
    mockMvc.perform(get("/card/api/delete/1"))
        .andExpect(status().isOk())
        .andDo(print());
  }
}