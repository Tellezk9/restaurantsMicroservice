package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.*;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Test
    void assignOrder() throws Exception {
        Long idOrder = 1L;
        doNothing().when(orderHandler).assignOrder(idOrder);

        mockMvc.perform(put("/order/assignOrder/{idOrder}",idOrder))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.message").value(Constants.ORDER_ASSIGNED_MESSAGE));

        verify(orderHandler,times(1)).assignOrder(idOrder);
    }

    @Test
    void changeOrderStatus() throws Exception {
        Long idOrder = 1L;
        Long status = 1L;
        doNothing().when(orderHandler).changeOrderStatus(idOrder,status);

        mockMvc.perform(put("/order/changeOrderStatus")
                        .param("idOrder",String.valueOf(idOrder))
                        .param("status",String.valueOf(status))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Constants.ORDER_STATUS_CHANGED_MESSAGE));

        verify(orderHandler,times(1)).changeOrderStatus(idOrder,status);
    }

    @Test
    void deliverOrder() throws Exception {
        Long securityCode = 1L;
        doNothing().when(orderHandler).deliverOrder(securityCode);

        mockMvc.perform(put("/order/deliverOrder")
                        .param("securityCode",String.valueOf(securityCode))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Constants.ORDER_DELIVERED_MESSAGE));

        verify(orderHandler,times(1)).deliverOrder(securityCode);
    }

    @Test
    void cancelOrder() throws Exception {
        Long idOrder = 1L;
        doNothing().when(orderHandler).cancelOrder(idOrder);

        mockMvc.perform(put("/order/cancelOrder")
                        .param("idOrder",String.valueOf(idOrder))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Constants.ORDER_CANCELED_MESSAGE));

        verify(orderHandler,times(1)).cancelOrder(idOrder);
    }

    @Test
    void getTraceabilityOrder() throws Exception {
        Long idOrder = 1L;
        OrderDocumentResponseDto orderDocumentResponseDto = new OrderDocumentResponseDto(1L,1L,1L,null,null,null,1L,null);
        when(orderHandler.getTraceabilityOrder(idOrder)).thenReturn(orderDocumentResponseDto);

        mockMvc.perform(get("/order/getTraceabilityOrder")
                        .param("idOrder",String.valueOf(idOrder)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idOrder").value(orderDocumentResponseDto.getIdOrder()))
                .andExpect(jsonPath("$.idClient").value(orderDocumentResponseDto.getIdClient()))
                .andExpect(jsonPath("$.idEmployee").value(orderDocumentResponseDto.getIdEmployee()))
                .andExpect(jsonPath("$.dateInit").value(orderDocumentResponseDto.getDateInit()))
                .andExpect(jsonPath("$.dateEnd").value(orderDocumentResponseDto.getDateEnd()))
                .andExpect(jsonPath("$.previousStatus").value(orderDocumentResponseDto.getPreviousStatus()))
                .andExpect(jsonPath("$.actualStatus").value(orderDocumentResponseDto.getActualStatus()))
                .andExpect(jsonPath("$.order").value(orderDocumentResponseDto.getOrder()));
    }

    @Test
    void getTraceabilityOrders() throws Exception {
        List<OrderDocumentResponseDto> orderDocumentResponseDto = List.of(new OrderDocumentResponseDto(1L,1L,1L,null,null,null,1L,null));
        when(orderHandler.getTraceabilityOrders()).thenReturn(orderDocumentResponseDto);

        mockMvc.perform(get("/order/getTraceabilityOrders"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]idOrder").value(orderDocumentResponseDto.get(0).getIdOrder()))
                .andExpect(jsonPath("$[0]idClient").value(orderDocumentResponseDto.get(0).getIdClient()))
                .andExpect(jsonPath("$[0]idEmployee").value(orderDocumentResponseDto.get(0).getIdEmployee()))
                .andExpect(jsonPath("$[0]dateInit").value(orderDocumentResponseDto.get(0).getDateInit()))
                .andExpect(jsonPath("$[0]dateEnd").value(orderDocumentResponseDto.get(0).getDateEnd()))
                .andExpect(jsonPath("$[0]previousStatus").value(orderDocumentResponseDto.get(0).getPreviousStatus()))
                .andExpect(jsonPath("$[0]actualStatus").value(orderDocumentResponseDto.get(0).getActualStatus()))
                .andExpect(jsonPath("$[0]order").value(orderDocumentResponseDto.get(0).getOrder()));
    }

    @Test
    void getOrdersDuration() throws Exception {
        Long idRestaurant = 1L;
        List<OrderDurationResponseDto> orderDurationResponseDtoList = List.of(new OrderDurationResponseDto(1L,null,null,null));
        when(orderHandler.getOrdersDuration(idRestaurant)).thenReturn(orderDurationResponseDtoList);

        mockMvc.perform(get("/order/getOrdersDuration")
                        .param("idRestaurant",String.valueOf(idRestaurant)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]idOrder").value(orderDurationResponseDtoList.get(0).getIdOrder()))
                .andExpect(jsonPath("$[0]dateInit").value(orderDurationResponseDtoList.get(0).getDateInit()))
                .andExpect(jsonPath("$[0]dateEnd").value(orderDurationResponseDtoList.get(0).getDateEnd()))
                .andExpect(jsonPath("$[0]order").value(orderDurationResponseDtoList.get(0).getOrder()));
    }

    @Test
    void getRankingEmployees() throws Exception {
        Long idRestaurant = 1L;
        List<RankingEmployeeResponseDto> rankingEmployeeResponseDtoList = List.of(new RankingEmployeeResponseDto(1L,null));
        when(orderHandler.getRankingEmployees(idRestaurant)).thenReturn(rankingEmployeeResponseDtoList);

        mockMvc.perform(get("/order/getRankingEmployees")
                        .param("idRestaurant",String.valueOf(idRestaurant)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]idEmployee").value(rankingEmployeeResponseDtoList.get(0).getIdEmployee()))
                .andExpect(jsonPath("$[0]averageMinutes").value(rankingEmployeeResponseDtoList.get(0).getAverageMinutes()));
    }
}