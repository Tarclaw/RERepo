package web.example.realestate.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import web.example.realestate.domain.people.Client;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClientRepositoryIT {

    @Autowired
    private ClientRepository repository;

    @Test
    public void findClientsByLastName() {
        Client client = repository.findClientsByLastName("Johnson").orElse(new Client());
        assertEquals("Johnson", client.getLastName());
    }
}