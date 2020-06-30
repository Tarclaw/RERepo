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
        AddressCommandToAddress toAddress = new AddressCommandToAddress();
        toFacility = new FacilityCommandToFacility(new ApartmentCommandToApartment(toAddress),
                                                   new BasementCommandToBasement(toAddress),
                                                   new GarageCommandToGarage(toAddress),
                                                   new HouseCommandToHouse(toAddress),
                                                   new StorageCommandToStorage(toAddress));
    }

    @Test
    public void testNullValue() {
        assertNull(toFacility.convert(null));
    }

    @Test
    public void testEmptyValue() {
        FacilityCommand command = new FacilityCommand();
        command.setItApartment(true);
        command.setAddress(new AddressCommand());
        assertNotNull(toFacility.convert(command));
    }

    @Test
    public void convert() {
        //given
        AddressCommand addressCommand = new AddressCommand();
        addressCommand.setId(ADDRESS_ID);

        FacilityCommand command = new FacilityCommand();
        command.setId(ID);
        command.setNumberOfRooms(NUMBER_OF_ROOMS);
        command.setTotalArea(TOTAL_AREA);
        command.setDescription(DESCRIPTION);
        command.setPublishedDateTime(PUBLISHED_DATE_TIME);
        command.setClosedDateTime(CLOSED_DATE_TIME);
        command.setAddress(addressCommand);
        command.setItApartment(true);

        //when
        Facility facility = toFacility.convert(command);

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