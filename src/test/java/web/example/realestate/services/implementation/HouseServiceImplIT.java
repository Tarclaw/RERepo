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
import web.example.realestate.converters.HouseToHouseCommand;
import web.example.realestate.domain.building.House;
import web.example.realestate.domain.enums.Status;
import web.example.realestate.domain.people.Client;
import web.example.realestate.repositories.HouseRepository;
import web.example.realestate.services.HouseService;

import java.math.BigInteger;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HouseServiceImplIT {

    private static final boolean OLD_HAS_GARDEN = true;
    private static final boolean NEW_HAS_GARDEN = false;

    private static final Integer POSTCODE = 24399;
    private static final Integer FACILITY_NUMBER = 55;
    private static final String CITY = "City";
    private static final String DISTRICT = "District";
    private static final String STREET = "Street";
    private static final Integer NUMBER_OF_ROOMS = 3;
    private static final Integer NUMBER_OF_Storeys = 2;
    private static final Integer TOTAL_AREA = 70;
    private static final String DESCRIPTION = "desc";
    private static final BigInteger MONTH_RENT = BigInteger.valueOf(7000L);
    private static final BigInteger PRICE = BigInteger.valueOf(70000L);
    private static final Long CLIENT_ID = 3L;

    @Autowired
    private HouseService service;

    @Autowired
    private HouseRepository repository;

    @Autowired
    private HouseToHouseCommand toHouseCommand;

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
        facilityCommand.setItHouse(true);
        facilityCommand.setNumberOfStoreys(NUMBER_OF_Storeys);
        facilityCommand.setHasBackyard(true);
        facilityCommand.setHasGarden(true);
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
        Client client = new Client();
        client.setId(CLIENT_ID);
        House house = repository.findHousesByHasGarden(OLD_HAS_GARDEN).orElseThrow(() -> new RuntimeException());
        house.setHasGarden(NEW_HAS_GARDEN);
        house.setClient(client);

        //when
        FacilityCommand command = service.saveAttached(toHouseCommand.convert(house));

        //then
        assertNotNull(command);
        assertNotEquals(OLD_HAS_GARDEN, command.isHasGarden());
        assertEquals(NEW_HAS_GARDEN, command.isHasGarden());
        assertEquals(house.getId(), command.getId());
    }

}
