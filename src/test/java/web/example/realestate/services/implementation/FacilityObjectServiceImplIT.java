package web.example.realestate.services.implementation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import web.example.realestate.commands.ClientCommand;
import web.example.realestate.commands.FacilityObjectCommand;
import web.example.realestate.converters.ClientToClientCommand;
import web.example.realestate.converters.FacilityObjectToFacilityObjectCommand;
import web.example.realestate.domain.building.FacilityObject;
import web.example.realestate.domain.people.Client;
import web.example.realestate.repositories.ClientRepository;
import web.example.realestate.repositories.FacilityObjectRepository;
import web.example.realestate.services.ClientService;
import web.example.realestate.services.FacilityObjectService;

import java.math.BigInteger;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FacilityObjectServiceImplIT {

    private static final BigInteger OLD_PRICE = BigInteger.valueOf(800000);
    private static final BigInteger NEW_PRICE = BigInteger.valueOf(600000);

    @Autowired
    private FacilityObjectService service;

    @Autowired
    private FacilityObjectRepository repository;

    @Autowired
    private FacilityObjectToFacilityObjectCommand toFacilityObjectCommand;

    @Test
    @Transactional
    public void saveFacilityObjectCommand() {
        //given
        FacilityObject facilityObject = repository
                .findFacilityObjectsByPriceIsLessThan(OLD_PRICE)
                .orElseThrow(() -> new RuntimeException());
        facilityObject.setPrice(NEW_PRICE);

        //when
        FacilityObjectCommand command = service
                .saveFacilityObjectCommand(toFacilityObjectCommand.convert(facilityObject));

        //then
        assertNotNull(command);
        assertNotEquals(OLD_PRICE, command.getPrice());
        assertEquals(NEW_PRICE, command.getPrice());
        assertEquals(facilityObject.getId(), command.getId());
    }
}
