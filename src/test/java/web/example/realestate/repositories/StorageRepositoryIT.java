package web.example.realestate.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import web.example.realestate.domain.building.Storage;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class StorageRepositoryIT {

    @Autowired
    private StorageRepository repository;

    @Test
    public void findStoragesByCommercialCapacityGreaterThan() {
        Storage storage = repository.findStoragesByCommercialCapacityGreaterThan(500).orElse(new Storage());
        int commercialCapacity = storage.getCommercialCapacity();
        assertEquals(600, commercialCapacity);
    }
}