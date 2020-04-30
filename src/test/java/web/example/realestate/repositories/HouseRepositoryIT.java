package web.example.realestate.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import web.example.realestate.domain.building.House;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class HouseRepositoryIT {

    @Autowired
    private HouseRepository repository;

    @Test
    public void findHousesByHasGarden() {
        House house = repository.findHousesByHasGarden(true).orElse(new House());
        assertEquals(true, house.isHasGarden());
    }
}