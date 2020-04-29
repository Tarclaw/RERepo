package web.example.realestate.services.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import web.example.realestate.domain.building.Storage;
import web.example.realestate.repositories.StorageRepository;
import web.example.realestate.services.StorageService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StorageServiceImplTest {

    private StorageService service;

    @Mock
    private StorageRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new StorageServiceImpl(repository);
    }

    @Test
    void getStorages() {
        when(service.getStorages()).
                thenReturn(new HashSet<>(Collections.singletonList(new Storage())));

        Set<Storage> storages = service.getStorages();

        assertEquals(1, storages.size());
        verify(repository, times(1)).findAll();
    }
}