package web.example.realestate.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import web.example.realestate.commands.AddressCommand;
import web.example.realestate.commands.GarageCommand;
import web.example.realestate.domain.building.Garage;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class GarageCommandToGarageTest {

    private static final Long ID = 1L;
    private static final Integer NUMBER_OF_ROOMS = 5;
    private static final Integer TOTAL_AREA = 150;
    private static final String DESCRIPTION = "some desc";
    private static final LocalDateTime PUBLISHED_DATE_TIME = LocalDateTime.now();
    private static final LocalDateTime CLOSED_DATE_TIME = LocalDateTime.now();
    private static final Long ADDRESS_ID = 1l;
    private static final boolean HAS_PIT = true;
    private static final boolean HAS_EQUIPMENT = true;

    private GarageCommandToGarage toGarage;

    @BeforeEach
    void setUp() {
        toGarage = new GarageCommandToGarage(new AddressCommandToAddress());
    }

    @Test
    void testNullValue() {
        assertNull(toGarage.convert(null));
    }

    @Test
    void testEmptyValue() {
        assertNotNull(toGarage.convert(new GarageCommand()));
    }

    @Test
    void convert() {
        //given
        AddressCommand addressCommand = new AddressCommand();
        addressCommand.setId(ADDRESS_ID);

        GarageCommand command = new GarageCommand();
        command.setId(ID);
        command.setNumberOfRooms(NUMBER_OF_ROOMS);
        command.setTotalArea(TOTAL_AREA);
        command.setDescription(DESCRIPTION);
        command.setPublishedDateTime(PUBLISHED_DATE_TIME);
        command.setClosedDateTime(CLOSED_DATE_TIME);
        command.setHasEquipment(HAS_EQUIPMENT);
        command.setHasPit(HAS_PIT);
        command.setAddressCommand(addressCommand);

        //when
        Garage garage = toGarage.convert(command);

        //then
        assertEquals(ID, garage.getId());
        assertEquals(NUMBER_OF_ROOMS, garage.getNumberOfRooms());
        assertEquals(TOTAL_AREA, garage.getTotalArea());
        assertEquals(DESCRIPTION, garage.getDescription());
        assertEquals(PUBLISHED_DATE_TIME, garage.getPublishedDateTime());
        assertEquals(CLOSED_DATE_TIME, garage.getClosedDateTime());
        assertEquals(HAS_EQUIPMENT, garage.isHasEquipment());
        assertEquals(HAS_PIT, garage.isHasPit());
        assertEquals(ADDRESS_ID, garage.getAddress().getId());
    }
}