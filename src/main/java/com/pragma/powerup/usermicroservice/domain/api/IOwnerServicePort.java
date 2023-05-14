package com.pragma.powerup.usermicroservice.domain.api;

import com.pragma.powerup.usermicroservice.domain.model.Owner;

public interface IOwnerServicePort {
    void saveOwner(Owner owner);
}
