package web.example.realestate.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import web.example.realestate.domain.building.FacilityObject;

import java.math.BigInteger;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FacilityObjectRepositoryIT {

    @Autowired
    private FacilityObjectRepository repository;

    @Test
    public void findFacilityObjectsByPriceIsLessThan() {
        FacilityObject facilityObject = repository.findFacilityObjectsByPriceIsLessThan(BigInteger.valueOf(800000))
                .orElse(new FacilityObject());
        assertEquals(BigInteger.valueOf(700000), facilityObject.getPrice());
    }
}