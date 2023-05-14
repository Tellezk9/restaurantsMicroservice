package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@AllArgsConstructor
@Getter
public class UserRequestDto {
    private String name;
    private String lastName;
    private Integer dniNumber;
    @Length(max = 13, min = 10)
    private Integer phone;
    private Date birthDate;
    @Pattern(regexp="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", message="Por favor, introduzca un correo electrónico válido")
    private String mail;
    private String password;
    private String idRole;
}
