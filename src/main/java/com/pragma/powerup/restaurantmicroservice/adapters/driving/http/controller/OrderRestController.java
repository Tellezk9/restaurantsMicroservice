package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.controller;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.*;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers.IOrderHandler;
import com.pragma.powerup.restaurantmicroservice.configuration.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@SecurityRequirement(name = "jwt")
public class OrderRestController {
    private final IOrderHandler orderHandler;

    @Operation(summary = "Add a new order",
            responses = {
                    @ApiResponse(responseCode = "201", description = "order created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "order exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "403", description = "Role not allowed for order creation",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PostMapping()
    public ResponseEntity<Map<String, String>> saveOrder(@Valid @RequestBody OrderRequestDto orderRequestDto) {
        orderHandler.saveOrder(orderRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.ORDER_CREATED_MESSAGE));
    }

    @Operation(summary = "get orders by status",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All orders by status returned",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = OrderResponseDto.class)))),
                    @ApiResponse(responseCode = "404", description = "No data found",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @GetMapping("/getOrders")
    public ResponseEntity<List<OrderResponseDto>> getOrders(@RequestParam Long status, Long idRestaurant, @Parameter(description = "Number of the page to list restaurants") @RequestParam Integer page) {
        return ResponseEntity.ok(orderHandler.getOrders(status, idRestaurant, page));
    }

    @Operation(summary = "get order dishes",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All order dishes by idOrder returned",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = OrderDishResponseDto.class)))),
                    @ApiResponse(responseCode = "404", description = "No data found",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @GetMapping("/getOrderDish/{idOrder}")
    public ResponseEntity<List<OrderDishResponseDto>> getOrderDishes(@PathVariable Long idOrder) {
        return ResponseEntity.ok(orderHandler.getOrderDish(idOrder));
    }

    @Operation(summary = "Assign a order",
            responses = {
                    @ApiResponse(responseCode = "202", description = "Assigned order",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "403", description = "Role not allowed for assign order",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})

    @PutMapping("/assignOrder/{idOrder}")
    public ResponseEntity<Map<String, String>> assignOrder(@PathVariable Long idOrder) {
        orderHandler.assignOrder(idOrder);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.ORDER_ASSIGNED_MESSAGE));
    }

    @Operation(summary = "Change order status",
            responses = {
                    @ApiResponse(responseCode = "200", description = "status changed",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "403", description = "Role not allowed for change order status",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})

    @PutMapping("/changeOrderStatus")
    public ResponseEntity<Map<String, String>> changeOrderStatus(@RequestParam Long idOrder,Long status) {
        orderHandler.changeOrderStatus(idOrder,status);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.ORDER_STATUS_CHANGED_MESSAGE));
    }
    @Operation(summary = "Deliver an order",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Order delivered successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Error to make the process",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "403", description = "Role not allowed for change order status",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PutMapping("/deliverOrder")
    public ResponseEntity<Map<String, String>> deliverOrder(@RequestParam Long securityCode) {
        orderHandler.deliverOrder(securityCode);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.ORDER_DELIVERED_MESSAGE));
    }

    @Operation(summary = "Cancel an order",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Order canceled successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Error to make the process",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "403", description = "Role not allowed for change order status",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PutMapping("/cancelOrder")
    public ResponseEntity<Map<String, String>> cancelOrder(@RequestParam Long idOrder) {
        orderHandler.cancelOrder(idOrder);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.ORDER_CANCELED_MESSAGE));
    }

    @Operation(summary = "get traceability order",
            responses = {
                    @ApiResponse(responseCode = "200", description = "traceability order returned",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = OrderDocumentResponseDto.class)))),
                    @ApiResponse(responseCode = "404", description = "No data found",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @GetMapping("/getTraceabilityOrder")
    public ResponseEntity<OrderDocumentResponseDto> getTraceabilityOrder(@RequestParam Long idOrder) {
        return ResponseEntity.ok(orderHandler.getTraceabilityOrder(idOrder));
    }

    @Operation(summary = "get all traceability orders",
            responses = {
                    @ApiResponse(responseCode = "200", description = "all traceability orders returned",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = OrderDocumentResponseDto.class)))),
                    @ApiResponse(responseCode = "404", description = "No data found",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @GetMapping("/getTraceabilityOrders")
    public ResponseEntity<List<OrderDocumentResponseDto>> getTraceabilityOrders() {
        return ResponseEntity.ok(orderHandler.getTraceabilityOrders());
    }

    @Operation(summary = "Get the duration of orders",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All duration of orders returned",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = OrderDurationResponseDto.class)))),
                    @ApiResponse(responseCode = "404", description = "No data found",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @GetMapping("/getOrdersDuration")
    public ResponseEntity<List<OrderDurationResponseDto>> getOrdersDuration(@RequestParam Long idRestaurant) {
        return ResponseEntity.ok(orderHandler.getOrdersDuration(idRestaurant));
    }

    @Operation(summary = "get ranking employees by restaurant",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All restaurant dishes returned",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = RankingEmployeeResponseDto.class)))),
                    @ApiResponse(responseCode = "404", description = "No data found",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @GetMapping("/getRankingEmployees")
    public ResponseEntity<List<RankingEmployeeResponseDto>> getRankingEmployees(@RequestParam Long idRestaurant) {
        return ResponseEntity.ok(orderHandler.getRankingEmployees(idRestaurant));
    }
}
