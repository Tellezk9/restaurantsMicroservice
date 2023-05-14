package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.api.IOwnerServicePort;
import com.pragma.powerup.usermicroservice.domain.model.Owner;
import com.pragma.powerup.usermicroservice.domain.service.OwnerValidator;
import com.pragma.powerup.usermicroservice.domain.spi.IOwnerPersistencePort;

public class OwnerUseCase implements IOwnerServicePort {
    private final IOwnerPersistencePort ownerPersistencePort;

    public OwnerUseCase(IOwnerPersistencePort ownerPersistencePort) {
        this.ownerPersistencePort = ownerPersistencePort;
    }

    public void saveOwner(Owner owner) {
        OwnerValidator ownerValidator = new OwnerValidator();

        ownerValidator.allFieldsFilled(owner);
        ownerValidator.isValidPhone(owner.getPhone());
        ownerValidator.isOlder(owner.getBirthDate());
        ownerValidator.isValidMail(owner.getMail());

        ownerPersistencePort.saveOwner(owner);
    }
}
