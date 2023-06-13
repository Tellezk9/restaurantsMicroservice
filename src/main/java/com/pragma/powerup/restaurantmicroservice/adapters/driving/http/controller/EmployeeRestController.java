package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.controller;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.EmployeeRequestDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers.IEmployeeHandler;
import com.pragma.powerup.restaurantmicroservice.configuration.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
@SecurityRequirement(name = "jwt")
public class EmployeeRestController {
    private final IEmployeeHandler employeeHandler;

    @Operation(summary = "Add a new restaurant employee",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Restaurant employee created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Restaurant employee exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "403", description = "Role not allowed for restaurant employee creation",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "400", description = "No user found with the role",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @PostMapping("/saveRestaurantEmployee")
    public ResponseEntity<Map<String, String>> saveRestaurantEmployee(@Valid @RequestBody EmployeeRequestDto employeeRequestDto) {
        employeeHandler.saveEmployee(employeeRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.RESTAURANT_EMPLOYEE_CREATED_MESSAGE));
    }
}
