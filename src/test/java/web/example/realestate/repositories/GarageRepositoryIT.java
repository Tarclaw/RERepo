package web.example.realestate.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import web.example.realestate.domain.building.Garage;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GarageRepositoryIT {

    @Autowired
    private GarageRepository repository;

    @Test
    public void findGaragesByHasEquipment() {
        Garage garage = repository.findGaragesByHasEquipment(true).orElse(new Garage());
        assertEquals(true, garage.isHasEquipment());
    }
}