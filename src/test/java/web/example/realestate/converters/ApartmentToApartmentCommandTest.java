package web.example.realestate.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Address;
import web.example.realestate.domain.building.Apartment;
import web.example.realestate.domain.people.Client;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ApartmentToApartmentCommandTest {

    private static final Long ID = 1L;
    private static final Long CLIENT_ID = 2L;
    private static final Integer NUMBER_OF_ROOMS = 5;
    private static final Integer TOTAL_AREA = 150;
    private static final Integer FLOOR = 5;
    private static final Integer APARTMENT_NUMBER = 72;
    private static final String DESCRIPTION = "some desc";
    private static final LocalDateTime PUBLISHED_DATE_TIME = LocalDateTime.now();
    private static final LocalDateTime CLOSED_DATE_TIME = LocalDateTime.now();
    private static final Long ADDRESS_ID = 1l;

    private ApartmentToApartmentCommand toApartmentCommand;

    @BeforeEach
    void setUp() {
        toApartmentCommand = new ApartmentToApartmentCommand(new AddressToAddressCommand());
    }

    @Test
    void testNullValue() {
        assertNull(toApartmentCommand.convert(null));
    }

    @Test
    void testEmptyValue() {
        assertNotNull(toApartmentCommand.convert(new Apartment()));
    }

    @Test
    void convert() {
        //given
        Address address = new Address();
        address.setId(ADDRESS_ID);

        Client client = new Client();
        client.setId(CLIENT_ID);

        Apartment apartment = new Apartment();
        apartment.setId(ID);
        apartment.setNumberOfRooms(NUMBER_OF_ROOMS);
        apartment.setTotalArea(TOTAL_AREA);
        apartment.setFloor(FLOOR);
        apartment.setApartmentNumber(APARTMENT_NUMBER);
        apartment.setDescription(DESCRIPTION);
        apartment.setPublishedDateTime(PUBLISHED_DATE_TIME);
        apartment.setClosedDateTime(CLOSED_DATE_TIME);
        apartment.setAddress(address);
        apartment.setClient(client);

        //when
        FacilityCommand command = toApartmentCommand.convert(apartment);

        //then
        assertEquals(ID, command.getId());
        assertEquals(CLIENT_ID, command.getClientId());
        assertEquals(NUMBER_OF_ROOMS, command.getNumberOfRooms());
        assertEquals(TOTAL_AREA, command.getTotalArea());
        assertEquals(FLOOR, command.getFloor());
        assertEquals(APARTMENT_NUMBER, command.getApartmentNumber());
        assertEquals(DESCRIPTION, command.getDescription());
        assertEquals(PUBLISHED_DATE_TIME, command.getPublishedDateTime());
        assertEquals(CLOSED_DATE_TIME, command.getClosedDateTime());
        assertEquals(ADDRESS_ID, command.getAddress().getId());
    }
}