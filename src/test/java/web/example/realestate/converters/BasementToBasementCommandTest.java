package web.example.realestate.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Address;
import web.example.realestate.domain.building.Basement;
import web.example.realestate.domain.people.Client;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class BasementToBasementCommandTest {

    //todo move to file
    private static final Long ID = 1L;
    private static final Integer NUMBER_OF_ROOMS = 5;
    private static final Integer TOTAL_AREA = 150;
    private static final String DESCRIPTION = "some desc";
    private static final boolean IT_COMMERCIAL = true;
    private static final LocalDateTime PUBLISHED_DATE_TIME = LocalDateTime.now();
    private static final LocalDateTime CLOSED_DATE_TIME = LocalDateTime.now();
    private static final Long ADDRESS_ID = 2l;
    private static final Long CLIENT_ID = 3l;

    private BasementToBasementCommand toBasementCommand;

    @BeforeEach
    void setUp() {
        toBasementCommand = new BasementToBasementCommand(new AddressToAddressCommand());
    }

    @Test
    void testNullValue() {
        assertNull(toBasementCommand.convert(null));
    }

    @Test
    void testEmptyValue() {
        Client client = new Client();
        Basement basement = new Basement();
        basement.setClient(client);
        assertNotNull(toBasementCommand.convert(basement));
    }

    @Test
    void convert() {
        //given
        Address address = new Address();
        address.setId(ADDRESS_ID);

        Client client = new Client();
        client.setId(CLIENT_ID);

        Basement basement = new Basement();
        basement.setId(ID);
        basement.setNumberOfRooms(NUMBER_OF_ROOMS);
        basement.setTotalArea(TOTAL_AREA);
        basement.setDescription(DESCRIPTION);
        basement.setItCommercial(IT_COMMERCIAL);
        basement.setPublishedDateTime(PUBLISHED_DATE_TIME);
        basement.setClosedDateTime(CLOSED_DATE_TIME);
        basement.setAddress(address);
        basement.setClient(client);

        //when
        FacilityCommand basementCommand = toBasementCommand.convert(basement);

        //then
        assertEquals(ID, basementCommand.getId());
        assertEquals(NUMBER_OF_ROOMS, basementCommand.getNumberOfRooms());
        assertEquals(TOTAL_AREA, basementCommand.getTotalArea());
        assertEquals(DESCRIPTION, basementCommand.getDescription());
        assertEquals(IT_COMMERCIAL, basementCommand.isItCommercial());
        assertEquals(PUBLISHED_DATE_TIME, basementCommand.getPublishedDateTime());
        assertEquals(CLOSED_DATE_TIME, basementCommand.getClosedDateTime());
        assertEquals(ADDRESS_ID, basementCommand.getAddress().getId());
    }
}