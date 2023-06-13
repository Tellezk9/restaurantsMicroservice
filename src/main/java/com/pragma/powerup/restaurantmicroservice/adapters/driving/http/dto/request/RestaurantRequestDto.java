package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class RestaurantRequestDto {
    @NotBlank
    private String name;
    @NotBlank
    private String address;
    @Min(1)
    private Integer idOwner;
    @NotBlank
    @Pattern(regexp = "[+][0-9]{12}")
    private String phone;
    @NotBlank
    private String urlLogo;
    @Min(1)
    private Integer nit;
}
