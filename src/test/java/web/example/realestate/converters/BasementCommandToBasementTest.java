package web.example.realestate.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import web.example.realestate.commands.AddressCommand;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Basement;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class BasementCommandToBasementTest {

    private static final Long ID = 1L;
    private static final Integer NUMBER_OF_ROOMS = 5;
    private static final Integer TOTAL_AREA = 150;
    private static final String DESCRIPTION = "some desc";
    private static final boolean IT_COMMERCIAL = true;
    private static final LocalDateTime PUBLISHED_DATE_TIME = LocalDateTime.now();
    private static final LocalDateTime CLOSED_DATE_TIME = LocalDateTime.now();
    private static final Long ADDRESS_ID = 1l;

    private BasementCommandToBasement toBasement;

    @BeforeEach
    void setUp() {
        toBasement = new BasementCommandToBasement(new AddressCommandToAddress());
    }

    @Test
    void testNullValue() {
        assertNull(toBasement.convert(null));
    }

    @Test
    void testEmptyValue() {
        assertNotNull(toBasement.convert(new FacilityCommand()));
    }

    @Test
    void convert() {
        //given
        AddressCommand addressCommand = new AddressCommand();
        addressCommand.setId(ADDRESS_ID);

        FacilityCommand command = new FacilityCommand();
        command.setId(ID);
        command.setNumberOfRooms(NUMBER_OF_ROOMS);
        command.setTotalArea(TOTAL_AREA);
        command.setDescription(DESCRIPTION);
        command.setItCommercial(IT_COMMERCIAL);
        command.setPublishedDateTime(PUBLISHED_DATE_TIME);
        command.setClosedDateTime(CLOSED_DATE_TIME);
        command.setAddress(addressCommand);

        //when
        Basement basement = toBasement.convert(command);

        //then
        assertEquals(ID, basement.getId());
        assertEquals(NUMBER_OF_ROOMS, basement.getNumberOfRooms());
        assertEquals(TOTAL_AREA, basement.getTotalArea());
        assertEquals(DESCRIPTION, basement.getDescription());
        assertEquals(IT_COMMERCIAL, basement.isItCommercial());
        assertEquals(PUBLISHED_DATE_TIME, basement.getPublishedDateTime());
        assertEquals(CLOSED_DATE_TIME, basement.getClosedDateTime());
        assertEquals(ADDRESS_ID, basement.getAddress().getId());
    }
}