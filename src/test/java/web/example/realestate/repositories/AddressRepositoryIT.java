package web.example.realestate.repositories;

import org.junit.runner.RunWith;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import web.example.realestate.domain.building.Address;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
class AddressRepositoryIT {

    @Autowired
    private AddressRepository repository;

    @Test
    void findAddressesByStreet() {
        Address address = repository.findAddressesByStreet("Manhattan").orElse(new Address());
        assertEquals("Manhattan", address.getStreet());
    }
}