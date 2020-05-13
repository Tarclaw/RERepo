package web.example.realestate.services.implementation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import web.example.realestate.commands.StorageCommand;
import web.example.realestate.converters.StorageToStorageCommand;
import web.example.realestate.domain.building.Storage;
import web.example.realestate.repositories.StorageRepository;
import web.example.realestate.services.StorageService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StorageServiceImplIT {

    private static final Integer OLD_COMMERCIAL_CAPACITY = 130;
    private static final Integer NEW_COMMERCIAL_CAPACITY = 230;

    @Autowired
    private StorageService service;

    @Autowired
    private StorageRepository repository;

    @Autowired
    private StorageToStorageCommand toStorageCommand;

    @Test
    @Transactional
    public void saveStorageCommand() {
        //given
        Storage storage = repository
                .findStoragesByCommercialCapacityGreaterThan(OLD_COMMERCIAL_CAPACITY)
                .orElseThrow(() -> new RuntimeException());
        storage.setCommercialCapacity(NEW_COMMERCIAL_CAPACITY);

        //when
        StorageCommand command = service.saveStorageCommand(toStorageCommand.convert(storage));

        //then
        assertNotNull(command);
        assertNotEquals(OLD_COMMERCIAL_CAPACITY, command.getCommercialCapacity());
        assertEquals(NEW_COMMERCIAL_CAPACITY, command.getCommercialCapacity());
        assertEquals(storage.getId(), command.getId());
    }



}
