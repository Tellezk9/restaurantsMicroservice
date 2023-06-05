package com.pragma.powerup.usermicroservice.adapters.driving.http.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IDishHandler;
import com.pragma.powerup.usermicroservice.configuration.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class DishRestControllerTest {

    @Mock
    private IDishHandler dishHandler;
    @InjectMocks
    private DishRestController dishRestController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(dishRestController).build();
    }

    @Test
    void saveDish() throws Exception {
        DishRequestDto dishRequestDto = new DishRequestDto("testName", 5000, "descriptionTest", "http://urlTest.com/test", 1, 1);
        doNothing().when(dishHandler).saveDish(Mockito.any(DishRequestDto.class));

        mockMvc.perform(post("/dish")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dishRequestDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(Constants.DISH_CREATED_MESSAGE));
    }

    @Test
    void updateDish() throws Exception {
        doNothing().when(dishHandler).updateDish(1, "test", 5000);

        mockMvc.perform(put("/dish/{id}",1)
                .param("description","test")
                .param("price","5000"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.message").value(Constants.DISH_UPDATED_MESSAGE));

    }
    @Test
    void changeDishState() throws Exception {
        doNothing().when(dishHandler).changeDishState(1, true);

        mockMvc.perform(put("/dish/changeStateDish")
                        .param("idDish","1")
                        .param("state","true"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.message").value(Constants.DISH_UPDATED_MESSAGE));

    }
}