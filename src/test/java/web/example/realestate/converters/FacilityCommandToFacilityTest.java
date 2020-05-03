package web.example.realestate.converters;

import org.junit.Before;
import org.junit.Test;
import web.example.realestate.commands.AddressCommand;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Facility;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class FacilityCommandToFacilityTest {

    private static final Long ID = 1L;
    private static final Integer NUMBER_OF_ROOMS = 5;
    private static final Integer TOTAL_AREA = 150;
    private static final String DESCRIPTION = "some desc";
    private static final LocalDateTime PUBLISHED_DATE_TIME = LocalDateTime.now();
    private static final LocalDateTime CLOSED_DATE_TIME = LocalDateTime.now();
    private static final Long ADDRESS_ID = 1l;

    private FacilityCommandToFacility toFacility;

    @Before
    public void setUp() {
        toFacility = new FacilityCommandToFacility(new AddressCommandToAddress());
    }

    @Test
    public void testNullValue() {
        assertNull(toFacility.convert(null));
    }

    @Test
    public void testEmptyValue() {
        assertNotNull(toFacility.convert(new FacilityCommand()));
    }

    @Test
    public void convert() {
        //given
        AddressCommand addressCommand = new AddressCommand();
        addressCommand.setId(ADDRESS_ID);

        FacilityCommand facilityCommand = new FacilityCommand();
        facilityCommand.setId(ID);
        facilityCommand.setNumberOfRooms(NUMBER_OF_ROOMS);
        facilityCommand.setTotalArea(TOTAL_AREA);
        facilityCommand.setDescription(DESCRIPTION);
        facilityCommand.setPublishedDateTime(PUBLISHED_DATE_TIME);
        facilityCommand.setClosedDateTime(CLOSED_DATE_TIME);
        facilityCommand.setAddressCommand(addressCommand);

        //when
        Facility facility = toFacility.convert(facilityCommand);

        //then
        assertEquals(ID, facility.getId());
        assertEquals(NUMBER_OF_ROOMS, facility.getNumberOfRooms());
        assertEquals(TOTAL_AREA, facility.getTotalArea());
        assertEquals(DESCRIPTION, facility.getDescription());
        assertEquals(PUBLISHED_DATE_TIME, facility.getPublishedDateTime());
        assertEquals(CLOSED_DATE_TIME, facility.getClosedDateTime());
        assertEquals(ADDRESS_ID, facility.getAddress().getId());
    }
}