package web.example.realestate.services.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.converters.StorageCommandToStorage;
import web.example.realestate.converters.StorageToStorageCommand;
import web.example.realestate.domain.building.Storage;
import web.example.realestate.repositories.ClientRepository;
import web.example.realestate.repositories.StorageRepository;
import web.example.realestate.services.StorageService;

import java.io.IOException;
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
    private StorageRepository storageRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private StorageCommandToStorage toStorage;

    @Mock
    private StorageToStorageCommand toStorageCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new StorageServiceImpl(storageRepository, clientRepository, toStorage, toStorageCommand);
    }

    @Test
    void getById() {
        //given
        Storage source = new Storage();
        source.setId(1L);
        when(storageRepository.findStoragesByIdWithClients(anyLong())).thenReturn(Optional.of(source));

        //when
        Storage storage = service.getById(1L);

        //then
        assertNotNull(storage);
        assertEquals(1L, storage.getId());
        verify(storageRepository, times(1)).findStoragesByIdWithClients(anyLong());
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
        verify(storageRepository, times(1)).findAll();
    }

    @Test
    void findCommandById() {
        //given
        Storage storage = new Storage();
        storage.setId(1L);
        FacilityCommand source = new FacilityCommand();
        source.setId(1L);
        when(storageRepository.findStoragesByIdWithClients(anyLong())).thenReturn(Optional.of(storage));
        when(toStorageCommand.convert(storage)).thenReturn(source);

        //when
        FacilityCommand command = service.findCommandById(1L);

        //then
        assertNotNull(command);
        assertEquals(1L, command.getId());
        verify(storageRepository, times(1)).findStoragesByIdWithClients(anyLong());
        verify(toStorageCommand, times(1)).convert(any());
    }

    @Test
    void deleteById() {
        service.deleteById(anyLong());
        verify(storageRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void saveImage() throws IOException {
        //given
        Long id = 1L;
        Storage storage = new Storage();
        storage.setId(id);

        when(storageRepository.findById(id)).thenReturn(Optional.of(storage));

        ArgumentCaptor<Storage> storageCaptor = ArgumentCaptor.forClass(Storage.class);
        MockMultipartFile multipartFile = new MockMultipartFile("imagefile", "test.txt",
                "textplain", "StorageImageStub".getBytes());

        //when
        service.saveImage(id, multipartFile);

        //then
        verify(storageRepository, times(1)).save(storageCaptor.capture());

        Storage saved = storageCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, saved.getImage().length);

    }
}