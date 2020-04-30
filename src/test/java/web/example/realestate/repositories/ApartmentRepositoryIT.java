package web.example.realestate.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import web.example.realestate.domain.building.Apartment;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ApartmentRepositoryIT {

    @Autowired
    private ApartmentRepository repository;

    @Test
    public void findApartmentsByTotalArea() {
        Apartment apartment = repository.findApartmentsByTotalArea(150).orElse(new Apartment());
        int area = apartment.getTotalArea();
        assertEquals(150, area);
    }
}