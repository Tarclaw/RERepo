package web.example.realestate.services.implementation;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import web.example.realestate.commands.AddressCommand;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.converters.BasementToBasementCommand;
import web.example.realestate.domain.building.Basement;
import web.example.realestate.domain.enums.Status;
import web.example.realestate.repositories.BasementRepository;
import web.example.realestate.services.BasementService;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BasementServiceImplIT {

    private static boolean OLD_VALUE = true;
    private static boolean NEW_VALUE = false;

    private static final Integer POSTCODE = 24399;
    private static final Integer FACILITY_NUMBER = 55;
    private static final String CITY = "City";
    private static final String DISTRICT = "District";
    private static final String STREET = "Street";
    private static final Integer NUMBER_OF_ROOMS = 3;
    private static final Integer TOTAL_AREA = 70;
    private static final String DESCRIPTION = "desc";
    private static final BigInteger MONTH_RENT = BigInteger.valueOf(7000L);
    private static final BigInteger PRICE = BigInteger.valueOf(70000L);
    private static final Long CLIENT_ID = 3L;

    @Autowired
    private BasementService service;

    @Autowired
    private BasementRepository repository;

    @Autowired
    private BasementToBasementCommand toBasementCommand;

    @Test
    @Transactional
    public void saveDetached() {
        //given
        AddressCommand address = new AddressCommand();
        address.setPostcode(POSTCODE);
        address.setFacilityNumber(FACILITY_NUMBER);
        address.setCity(CITY);
        address.setDistrict(DISTRICT);
        address.setStreet(STREET);

        FacilityCommand facilityCommand = new FacilityCommand();
        facilityCommand.setClientId(CLIENT_ID);
        facilityCommand.setItBasement(true);
        facilityCommand.setItCommercial(true);
        facilityCommand.setNumberOfRooms(NUMBER_OF_ROOMS);
        facilityCommand.setTotalArea(TOTAL_AREA);
        facilityCommand.setDescription(DESCRIPTION);
        facilityCommand.setMonthRent(MONTH_RENT);
        facilityCommand.setPrice(PRICE);
        facilityCommand.setStatus(Status.FOR_RENT);
        facilityCommand.setAddress(address);

        //when
        FacilityCommand savedCommand = service.saveDetached(facilityCommand);

        //then
        Assertions.assertNotNull(savedCommand);
        Assertions.assertNotNull(savedCommand.getId());
        Assertions.assertNotNull(savedCommand.getAddress().getId());
    }

    @Test
    @Transactional
    public void saveAttached() {
        //given
        Basement basement = repository.findBasementsByItCommercial(OLD_VALUE).orElseThrow(() -> new RuntimeException());
        basement.setItCommercial(NEW_VALUE);

        //when
        FacilityCommand savedCommand = service.saveAttached(toBasementCommand.convert(basement));

        //then
        assertNotNull(savedCommand);
        assertNotEquals(OLD_VALUE, savedCommand.isItCommercial());
        assertEquals(NEW_VALUE, savedCommand.isItCommercial());
        assertEquals(basement.getId(), savedCommand.getId());
    }

}
