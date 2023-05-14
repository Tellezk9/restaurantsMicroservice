package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.model.Owner;
import com.pragma.powerup.usermicroservice.domain.model.Role;
import com.pragma.powerup.usermicroservice.domain.spi.IOwnerPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerUseCaseTest {
    @Mock
    private IOwnerPersistencePort ownerPersistencePort;
    @InjectMocks
    private OwnerUseCase ownerUseCase;

    @Test
    void saveOwner() {
        Role role = new Role(4L, null, null);
        Owner owner = new Owner("test", "testLastName", 1234, "+123456789012", "2002/05/01", "test@gmail.com", "1234", role);
        doNothing().when(ownerPersistencePort).saveOwner(owner);
        ownerUseCase.saveOwner(owner);
        verify(ownerPersistencePort, times(1)).saveOwner(owner);
    }
}