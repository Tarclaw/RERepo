package web.example.realestate.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import web.example.realestate.domain.building.Facility;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FacilityRepositoryIT {

    @Autowired
    private FacilityRepository repository;

    @Test
    public void findFacilitiesByDescription() {
        Facility facility = repository.findFacilitiesByDescription("vehicles repair").orElse(new Facility());
        assertEquals("vehicles repair", facility.getDescription());
    }
}