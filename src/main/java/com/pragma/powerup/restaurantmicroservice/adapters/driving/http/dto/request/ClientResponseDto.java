package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class ClientResponseDto {
    private Integer id;
    private String name;
    private String lastName;
    private Integer dniNumber;
    private String phone;
    private String birthDate;
    private String mail;
}
