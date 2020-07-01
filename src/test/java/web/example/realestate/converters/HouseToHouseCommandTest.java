package web.example.realestate.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Address;
import web.example.realestate.domain.building.House;
import web.example.realestate.domain.people.Client;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HouseToHouseCommandTest {
    private static final Long ID = 1L;
    private static final Integer NUMBER_OF_ROOMS = 5;
    private static final Integer TOTAL_AREA = 150;
    private static final String DESCRIPTION = "some desc";
    private static final Integer NUMBER_OF_STOREYS = 3;
    private static final LocalDateTime PUBLISHED_DATE_TIME = LocalDateTime.now();
    private static final LocalDateTime CLOSED_DATE_TIME = LocalDateTime.now();
    private static final Long ADDRESS_ID = 2l;
    private static final boolean HAS_BACKYARD = true;
    private static final boolean HAS_GARDEN = true;

    private HouseToHouseCommand toHouseCommand;

    @BeforeEach
    void setUp() {
        toHouseCommand = new HouseToHouseCommand(new AddressToAddressCommand());
    }

    @Test
    void testNullValue() {
        assertNull(toHouseCommand.convert(null));
    }

    @Test
    void testEmptyValue() {
        House house = new House();
        house.setClient(new Client());
        assertNotNull(toHouseCommand.convert(house));
    }

    @Test
    void convert() {
        //given
        Address address = new Address();
        address.setId(ADDRESS_ID);

        Client client = new Client();
        client.setId(ID);

        House house = new House();
        house.setId(ID);
        house.setNumberOfRooms(NUMBER_OF_ROOMS);
        house.setTotalArea(TOTAL_AREA);
        house.setDescription(DESCRIPTION);
        house.setNumberOfStoreys(NUMBER_OF_STOREYS);
        house.setPublishedDateTime(PUBLISHED_DATE_TIME);
        house.setClosedDateTime(CLOSED_DATE_TIME);
        house.setHasBackyard(HAS_BACKYARD);
        house.setHasGarden(HAS_GARDEN);
        house.setAddress(address);
        house.setClient(client);

        //when
        FacilityCommand command = toHouseCommand.convert(house);

        //then
        assertEquals(ID, command.getId());
        assertEquals(NUMBER_OF_ROOMS, command.getNumberOfRooms());
        assertEquals(TOTAL_AREA, command.getTotalArea());
        assertEquals(DESCRIPTION, command.getDescription());
        assertEquals(NUMBER_OF_STOREYS, command.getNumberOfStoreys());
        assertEquals(PUBLISHED_DATE_TIME, command.getPublishedDateTime());
        assertEquals(CLOSED_DATE_TIME, command.getClosedDateTime());
        assertEquals(HAS_BACKYARD, command.isHasBackyard());
        assertEquals(HAS_GARDEN, command.isHasGarden());
        assertEquals(ADDRESS_ID, command.getAddress().getId());
    }
}