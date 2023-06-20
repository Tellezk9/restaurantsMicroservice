package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.DishResponseDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers.IDishHandler;
import com.pragma.powerup.restaurantmicroservice.configuration.Constants;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

        mockMvc.perform(put("/dish/{id}", 1)
                        .param("description", "test")
                        .param("price", "5000"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Constants.DISH_UPDATED_MESSAGE));

    }

    @Test
    void changeDishState() throws Exception {
        doNothing().when(dishHandler).changeDishState(1, true);

        mockMvc.perform(put("/dish/changeStateDish")
                        .param("idDish", "1")
                        .param("state", "true"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Constants.DISH_UPDATED_MESSAGE));

    }


    @Test
    void getDishes() throws Exception {
        Integer page = 2;
        Integer idRestaurant = 2;
        Integer idCategory = 1;
        DishResponseDto dishResponseDto = new DishResponseDto(1, "testName", 12345, "testDescription", "url//test.com", 1);
        List<DishResponseDto> dishList = new ArrayList<>();
        dishList.add(dishResponseDto);

        when(dishHandler.getDishes(idRestaurant, idCategory, page)).thenReturn(dishList);

        mockMvc.perform(get("/dish/getDishes")
                        .param("page", String.valueOf(page))
                        .param("idRestaurant", String.valueOf(idRestaurant))
                        .param("idCategory", String.valueOf(idCategory))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]id").value(dishList.get(0).getId()))
                .andExpect(jsonPath("$[0]name").value(dishList.get(0).getName()))
                .andExpect(jsonPath("$[0]price").value(dishList.get(0).getPrice()))
                .andExpect(jsonPath("$[0]description").value(dishList.get(0).getDescription()))
                .andExpect(jsonPath("$[0]urlImage").value(dishList.get(0).getUrlImage()))
                .andExpect(jsonPath("$[0]idCategory").value(dishList.get(0).getIdCategory()));
    }
}