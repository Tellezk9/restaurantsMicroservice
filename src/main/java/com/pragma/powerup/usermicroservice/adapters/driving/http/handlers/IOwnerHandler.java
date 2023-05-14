package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.OwnerRequestDto;

public interface IOwnerHandler {
    void saveOwner(OwnerRequestDto ownerRequestDto);
}
