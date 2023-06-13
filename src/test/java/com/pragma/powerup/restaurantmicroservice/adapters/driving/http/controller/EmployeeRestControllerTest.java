package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.EmployeeRequestDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers.IEmployeeHandler;
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

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class EmployeeRestControllerTest {

    @Mock
    private IEmployeeHandler employeeHandler;
    @InjectMocks
    private EmployeeRestController employeeRestController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeRestController).build();
    }

    @Test
    void saveRestaurantEmployee() throws Exception {
        EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto(1, 1);
        doNothing().when(employeeHandler).saveEmployee(Mockito.any(EmployeeRequestDto.class));

        mockMvc.perform(post("/employee/saveRestaurantEmployee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(employeeRequestDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(Constants.RESTAURANT_EMPLOYEE_CREATED_MESSAGE));
    }
}