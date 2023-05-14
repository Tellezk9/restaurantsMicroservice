package com.pragma.powerup.usermicroservice.domain.spi;

import com.pragma.powerup.usermicroservice.domain.model.Owner;

public interface IOwnerPersistencePort {
    void saveOwner(Owner owner);
}
