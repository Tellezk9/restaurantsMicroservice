package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class DishRequestDto {
    @NotBlank
    private String name;
    @Min(1)
    private Integer price;
    @NotBlank
    private String description;
    @NotBlank
    private String urlImage;
    @Min(1)
    private Integer idCategory;
    @Min(1)
    private Integer idRestaurant;
}
