package web.example.realestate.converters;

import org.junit.Before;
import org.junit.Test;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Address;
import web.example.realestate.domain.building.Facility;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class FacilityToFacilityCommandTest {

    private static final Long ID = 1L;
    private static final Integer NUMBER_OF_ROOMS = 5;
    private static final Integer TOTAL_AREA = 150;
    private static final String DESCRIPTION = "some desc";
    private static final LocalDateTime PUBLISHED_DATE_TIME = LocalDateTime.now();
    private static final LocalDateTime CLOSED_DATE_TIME = LocalDateTime.now();
    private static final Long ADDRESS_ID = 1l;

    private FacilityToFacilityCommand toFacilityCommand;

    @Before
    public void setUp() {
        toFacilityCommand = new FacilityToFacilityCommand(new AddressToAddressCommand());
    }

    @Test
    public void testNullValue() {
        assertNull(toFacilityCommand.convert(null));
    }

    @Test
    public void testEmptyValue() {
        assertNotNull(toFacilityCommand.convert(new Facility()));
    }

    @Test
    public void convert() {
        //given
        Address address = new Address();
        address.setId(ADDRESS_ID);

        Facility facility = new Facility();
        facility.setId(ID);
        facility.setNumberOfRooms(NUMBER_OF_ROOMS);
        facility.setTotalArea(TOTAL_AREA);
        facility.setDescription(DESCRIPTION);
        facility.setPublishedDateTime(PUBLISHED_DATE_TIME);
        facility.setClosedDateTime(CLOSED_DATE_TIME);
        facility.setAddress(address);

        //when
        FacilityCommand facilityCommand = toFacilityCommand.convert(facility);

        //then
        assertEquals(ID, facilityCommand.getId());
        assertEquals(NUMBER_OF_ROOMS, facilityCommand.getNumberOfRooms());
        assertEquals(TOTAL_AREA, facilityCommand.getTotalArea());
        assertEquals(DESCRIPTION, facilityCommand.getDescription());
        assertEquals(PUBLISHED_DATE_TIME, facilityCommand.getPublishedDateTime());
        assertEquals(CLOSED_DATE_TIME, facilityCommand.getClosedDateTime());
        assertEquals(ADDRESS_ID, facilityCommand.getAddress().getId());
    }
}