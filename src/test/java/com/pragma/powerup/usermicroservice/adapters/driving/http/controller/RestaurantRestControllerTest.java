package com.pragma.powerup.usermicroservice.adapters.driving.http.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.GetRestaurantsResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IRestaurantHandler;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RestaurantRestControllerTest {

    @Mock
    private IRestaurantHandler restaurantHandler;
    @InjectMocks
    private RestaurantRestController restaurantRestController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(restaurantRestController).build();
    }

    @Test
    void saveRestaurant() throws Exception {
        RestaurantRequestDto restaurantRequestDto = new RestaurantRequestDto("testName", "string", 4, "+439094230412", "string", 123);
        doNothing().when(restaurantHandler).saveRestaurant(Mockito.any(RestaurantRequestDto.class));

        mockMvc.perform(post("/restaurant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(restaurantRequestDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(Constants.RESTAURANT_CREATED_MESSAGE));
    }

    @Test
    void getRestaurants() throws Exception {
        Integer page = 2;
        GetRestaurantsResponseDto getRestaurantsResponseDto = new GetRestaurantsResponseDto(1L, "testName", "string");
        List<GetRestaurantsResponseDto> restaurantsList = new ArrayList<>();
        restaurantsList.add(getRestaurantsResponseDto);

        when(restaurantHandler.getRestaurants(page)).thenReturn(restaurantsList);

        restaurantRestController.getRestaurants(page);
        mockMvc.perform(get("/restaurant/restaurants")
                        .param("page", String.valueOf(page)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]id").value(restaurantsList.get(0).getId()))
                .andExpect(jsonPath("$[0]name").value(restaurantsList.get(0).getName()))
                .andExpect(jsonPath("$[0]urlLogo").value(restaurantsList.get(0).getUrlLogo()));
    }
}