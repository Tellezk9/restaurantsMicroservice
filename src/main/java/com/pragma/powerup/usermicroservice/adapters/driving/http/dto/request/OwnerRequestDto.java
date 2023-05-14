package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class OwnerRequestDto {
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    @Min(1)
    private Integer dniNumber;
    @NotBlank
    @Pattern(regexp = "[+][0-9]{12}")
    private String phone;
    @NotBlank
    @Pattern(regexp = "^\\d{4}/(0?[1-9]|1[0-2])/(0?[1-9]|[12][0-9]|3[01])$", message = "the date must be in the format yyyy/mm/dd")
    private String birthDate;
    @NotBlank
    @Email
    private String mail;
    @NotBlank
    private String password;
}
