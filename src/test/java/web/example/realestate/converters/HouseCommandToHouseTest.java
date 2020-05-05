package web.example.realestate.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import web.example.realestate.commands.AddressCommand;
import web.example.realestate.commands.HouseCommand;
import web.example.realestate.domain.building.House;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HouseCommandToHouseTest {

    private static final Long ID = 1L;
    private static final Integer NUMBER_OF_ROOMS = 5;
    private static final Integer TOTAL_AREA = 150;
    private static final String DESCRIPTION = "some desc";
    private static final Integer NUMBER_OF_STOREYS = 3;
    private static final LocalDateTime PUBLISHED_DATE_TIME = LocalDateTime.now();
    private static final LocalDateTime CLOSED_DATE_TIME = LocalDateTime.now();
    private static final Long ADDRESS_ID = 1l;
    private static final boolean HAS_BACKYARD = true;
    private static final boolean HAS_GARDEN = true;

    private HouseCommandToHouse toHouse;

    @BeforeEach
    void setUp() {
        toHouse = new HouseCommandToHouse(new AddressCommandToAddress());
    }

    @Test
    void testNullValue() {
        assertNull(toHouse.convert(null));
    }

    @Test
    void testEmptyValue() {
        assertNotNull(toHouse.convert(new HouseCommand()));
    }

    @Test
    void convert() {
        //given
        AddressCommand addressCommand = new AddressCommand();
        addressCommand.setId(ADDRESS_ID);

        HouseCommand command = new HouseCommand();
        command.setId(ID);
        command.setNumberOfRooms(NUMBER_OF_ROOMS);
        command.setTotalArea(TOTAL_AREA);
        command.setDescription(DESCRIPTION);
        command.setNumberOfStoreys(NUMBER_OF_STOREYS);
        command.setPublishedDateTime(PUBLISHED_DATE_TIME);
        command.setClosedDateTime(CLOSED_DATE_TIME);
        command.setHasBackyard(HAS_BACKYARD);
        command.setHasGarden(HAS_GARDEN);
        command.setAddressCommand(addressCommand);

        //when
        House garage = toHouse.convert(command);

        //then
        assertEquals(ID, garage.getId());
        assertEquals(NUMBER_OF_ROOMS, garage.getNumberOfRooms());
        assertEquals(TOTAL_AREA, garage.getTotalArea());
        assertEquals(DESCRIPTION, garage.getDescription());
        assertEquals(NUMBER_OF_STOREYS, garage.getNumberOfStoreys());
        assertEquals(PUBLISHED_DATE_TIME, garage.getPublishedDateTime());
        assertEquals(CLOSED_DATE_TIME, garage.getClosedDateTime());
        assertEquals(HAS_BACKYARD, garage.isHasBackyard());
        assertEquals(HAS_GARDEN, garage.isHasGarden());
        assertEquals(ADDRESS_ID, garage.getAddress().getId());
    }
}