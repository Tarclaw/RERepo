package web.example.realestate.services.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.converters.StorageCommandToStorage;
import web.example.realestate.converters.StorageToStorageCommand;
import web.example.realestate.domain.building.Storage;
import web.example.realestate.repositories.StorageRepository;
import web.example.realestate.services.StorageService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StorageServiceImplTest {

    private StorageService service;

    @Mock
    private StorageRepository repository;

    @Mock
    private StorageCommandToStorage toStorage;

    @Mock
    private StorageToStorageCommand toStorageCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new StorageServiceImpl(repository, toStorage, toStorageCommand);
    }

    @Test
    void getById() {
        //given
        Storage source = new Storage();
        source.setId(1L);
        when(repository.findById(anyLong())).thenReturn(Optional.of(source));

        //when
        Storage storage = service.getById(1L);

        //then
        assertNotNull(storage);
        assertEquals(1L, storage.getId());
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void getByIdExceptionHandling() {
        assertThrows(RuntimeException.class, () -> service.getById(anyLong()));
    }

    @Test
    void getStorages() {
        when(service.getStorages()).
                thenReturn(new HashSet<>(Collections.singletonList(new Storage())));

        Set<Storage> storages = service.getStorages();

        assertEquals(1, storages.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void findCommandById() {
        //given
        Storage storage = new Storage();
        storage.setId(1L);
        FacilityCommand source = new FacilityCommand();
        source.setId(1L);
        when(repository.findById(anyLong())).thenReturn(Optional.of(storage));
        when(toStorageCommand.convert(storage)).thenReturn(source);

        //when
        FacilityCommand command = service.findCommandById(1L);

        //then
        assertNotNull(command);
        assertEquals(1L, command.getId());
        verify(repository, times(1)).findById(anyLong());
        verify(toStorageCommand, times(1)).convert(any());
    }

    @Test
    void deleteById() {
        service.deleteById(anyLong());
        verify(repository, times(1)).deleteById(anyLong());
    }
}