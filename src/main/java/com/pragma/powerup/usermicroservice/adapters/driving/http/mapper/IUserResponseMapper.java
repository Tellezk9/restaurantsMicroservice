package com.pragma.powerup.usermicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.UserResponseDto;
import com.pragma.powerup.usermicroservice.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserResponseMapper {
    UserResponseDto toResponse(User user);
    @Mapping(target = "name",source = "user.name")
    @Mapping(target = "lastName",source = "user.lastName")
    @Mapping(target = "dniNumber",source = "user.dniNumber")
    @Mapping(target = "phone",source = "user.phone")
    @Mapping(target = "birthDate",source = "user.birthDate")
    @Mapping(target = "mail",source = "user.mail")
    @Mapping(target = "password",source = "user.password")
    List<UserResponseDto> toUserList(List<User> userList);
}
