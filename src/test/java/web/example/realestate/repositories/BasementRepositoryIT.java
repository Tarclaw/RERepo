package web.example.realestate.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import web.example.realestate.domain.building.Basement;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BasementRepositoryIT {

    @Autowired
    private BasementRepository repository;

    @Test
    public void findBasementsByItCommercial() {
        Basement basement = repository.findBasementsByItCommercial(true).orElse(new Basement());
        assertEquals(true, basement.isItCommercial());
    }
}