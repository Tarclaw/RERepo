package web.example.realestate.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import web.example.realestate.commands.GarageCommand;
import web.example.realestate.domain.building.Address;
import web.example.realestate.domain.building.Garage;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class GarageToGarageCommandTest {

    private static final Long ID = 1L;
    private static final Integer NUMBER_OF_ROOMS = 5;
    private static final Integer TOTAL_AREA = 150;
    private static final String DESCRIPTION = "some desc";
    private static final LocalDateTime PUBLISHED_DATE_TIME = LocalDateTime.now();
    private static final LocalDateTime CLOSED_DATE_TIME = LocalDateTime.now();
    private static final Long ADDRESS_ID = 1l;
    private static final boolean HAS_PIT = true;
    private static final boolean HAS_EQUIPMENT = true;

    private GarageToGarageCommand toGarageCommand;

    @BeforeEach
    void setUp() {
        toGarageCommand = new GarageToGarageCommand(new AddressToAddressCommand());
    }

    @Test
    void testNullValue() {
        assertNull(toGarageCommand.convert(null));
    }

    @Test
    void testEmptyValue() {
        assertNotNull(toGarageCommand.convert(new Garage()));
    }

    @Test
    void convert() {
        //given
        Address address = new Address();
        address.setId(ADDRESS_ID);

        Garage garage = new Garage();
        garage.setId(ID);
        garage.setNumberOfRooms(NUMBER_OF_ROOMS);
        garage.setTotalArea(TOTAL_AREA);
        garage.setDescription(DESCRIPTION);
        garage.setPublishedDateTime(PUBLISHED_DATE_TIME);
        garage.setClosedDateTime(CLOSED_DATE_TIME);
        garage.setHasEquipment(HAS_EQUIPMENT);
        garage.setHasPit(HAS_PIT);
        garage.setAddress(address);

        //when
        GarageCommand command = toGarageCommand.convert(garage);

        //then
        assertEquals(ID, command.getId());
        assertEquals(NUMBER_OF_ROOMS, command.getNumberOfRooms());
        assertEquals(TOTAL_AREA, command.getTotalArea());
        assertEquals(DESCRIPTION, command.getDescription());
        assertEquals(PUBLISHED_DATE_TIME, command.getPublishedDateTime());
        assertEquals(CLOSED_DATE_TIME, command.getClosedDateTime());
        assertEquals(HAS_EQUIPMENT, command.isHasEquipment());
        assertEquals(HAS_PIT, command.isHasPit());
        assertEquals(ADDRESS_ID, command.getAddressCommand().getId());
    }
}