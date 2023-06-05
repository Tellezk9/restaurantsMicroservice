package com.pragma.powerup.usermicroservice.adapters.driving.http.controller;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IDishHandler;
import com.pragma.powerup.usermicroservice.configuration.Constants;
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
@RequestMapping("/dish")
@RequiredArgsConstructor
@SecurityRequirement(name = "jwt")
public class DishRestController {
    private final IDishHandler dishHandler;

    @Operation(summary = "Add a new dish",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Restaurant created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Restaurant exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "403", description = "Role not allowed for restaurant creation",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PostMapping
    public ResponseEntity<Map<String,String>> saveDish(@Valid @RequestBody DishRequestDto dishRequestDto){
        dishHandler.saveDish(dishRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.DISH_CREATED_MESSAGE));
    }

    @Operation(summary = "Update a dish",
            responses = {
                    @ApiResponse(responseCode = "201", description = "" +
                            " updated",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Restaurant exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "403", description = "Role not allowed for restaurant creation",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PutMapping("/{id}")
    public ResponseEntity<Map<String,String>> updateDish(@PathVariable Integer id, @RequestParam String description, @RequestParam Integer price){
        dishHandler.updateDish(id, description, price);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.DISH_UPDATED_MESSAGE));
    }

    @Operation(summary = "change dish state",
            responses = {
                    @ApiResponse(responseCode = "202", description = "Dish state updated",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "412", description = "Dish not exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "409", description = "Not the owner",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "403", description = "Role not allowed for update dish state",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PutMapping("/changeStateDish")
    public ResponseEntity<Map<String,String>> changeDishState(@RequestParam Integer idDish , Boolean state){
        dishHandler.changeDishState(idDish, state);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.DISH_UPDATED_MESSAGE));
    }
}
