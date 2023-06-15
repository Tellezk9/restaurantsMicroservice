package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.OrderDishResponseDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers.IOrderHandler;
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OrderRestControllerTest {

    @Mock
    private IOrderHandler orderHandler;
    @InjectMocks
    private OrderRestController orderRestController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderRestController).build();
    }


    @Test
    void saveOrder() throws Exception {
        Long idRestaurant = 1L;
        List<Long> orderDishes = Arrays.asList(1L, 2L, 3L);
        List<Integer> amountDishes = Arrays.asList(2, 3, 4);

        OrderRequestDto orderRequestDto = new OrderRequestDto(idRestaurant, orderDishes, amountDishes);
        doNothing().when(orderHandler).saveOrder(Mockito.any(OrderRequestDto.class));

        mockMvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(orderRequestDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(Constants.ORDER_CREATED_MESSAGE));
    }

    @Test
    void getOrders() throws Exception {
        Long status = 1L;
        Long idRestaurant = 1L;
        Integer page = 1;
        List<OrderResponseDto> orderResponseDtoList = List.of(new OrderResponseDto(1L, 1L, null, status, null, idRestaurant));
        when(orderHandler.getOrders(status, idRestaurant, page)).thenReturn(orderResponseDtoList);

        mockMvc.perform(get("/order/getOrders")
                        .param("status", String.valueOf(status))
                        .param("idRestaurant", String.valueOf(idRestaurant))
                        .param("page", String.valueOf(page))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]id").value(orderResponseDtoList.get(0).getId()))
                .andExpect(jsonPath("$[0]idClient").value(orderResponseDtoList.get(0).getIdClient()))
                .andExpect(jsonPath("$[0]date").value(orderResponseDtoList.get(0).getDate()))
                .andExpect(jsonPath("$[0]idChef").value(orderResponseDtoList.get(0).getIdChef()))
                .andExpect(jsonPath("$[0]idRestaurant").value(orderResponseDtoList.get(0).getIdRestaurant()));
    }


    @Test
    void getOrderDishes() throws Exception {
        Long idOrder = 1L;
        String nameDish = "testDish";
        Integer amount = 1;
        List<OrderDishResponseDto> orderResponseDtoList = List.of(new OrderDishResponseDto(nameDish,amount));
        when(orderHandler.getOrderDish(idOrder)).thenReturn(orderResponseDtoList);

        mockMvc.perform(get("/order/getOrderDish/{id}",idOrder))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]nameDish").value(orderResponseDtoList.get(0).getNameDish()))
                .andExpect(jsonPath("$[0]amount").value(orderResponseDtoList.get(0).getAmount()));
    }
}