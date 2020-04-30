package web.example.realestate.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import web.example.realestate.domain.people.RealEstateAgent;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RealEstateAgentRepositoryIT {

    @Autowired
    private RealEstateAgentRepository repository;

    @Test
    public void findRealEstateAgentsByFirstNameAndLastName() {
        RealEstateAgent agent = repository.findRealEstateAgentsByFirstNameAndLastName("Billy", "Butkiss")
                .orElse(new RealEstateAgent());
        assertEquals("Billy", agent.getFirstName());
        assertEquals("Butkiss", agent.getLastName());
    }
}