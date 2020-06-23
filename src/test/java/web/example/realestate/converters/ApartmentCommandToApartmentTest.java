package web.example.realestate.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import web.example.realestate.commands.AddressCommand;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Apartment;
import web.example.realestate.domain.enums.Status;

import java.math.BigInteger;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ApartmentCommandToApartmentTest {

    private static final Long ID = 1L;
    private static final Integer NUMBER_OF_ROOMS = 5;
    private static final Integer TOTAL_AREA = 150;
    private static final Integer FLOOR = 5;
    private static final Integer APARTMENT_NUMBER = 72;
    private static final String DESCRIPTION = "some desc";
    private static final LocalDateTime PUBLISHED_DATE_TIME = LocalDateTime.now();
    private static final LocalDateTime CLOSED_DATE_TIME = LocalDateTime.now();
    private static final Long ADDRESS_ID = 1l;

    private ApartmentCommandToApartment toApartment;

    @BeforeEach
    void setUp() {
        toApartment = new ApartmentCommandToApartment(new AddressCommandToAddress());
    }

    @Test
    void testNullValue() {
        assertNull(toApartment.convert(null));
    }

    @Test
    void testEmptyObject() {
        AddressCommand addressCommand = new AddressCommand();
        FacilityCommand command = new FacilityCommand();
        command.setAddress(addressCommand);
        assertNotNull(toApartment.convert(command));
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
        command.setFloor(FLOOR);
        command.setApartmentNumber(APARTMENT_NUMBER);
        command.setDescription(DESCRIPTION);
        command.setPublishedDateTime(PUBLISHED_DATE_TIME);
        command.setClosedDateTime(CLOSED_DATE_TIME);
        command.setStatus(Status.FOR_RENT);
        command.setMonthRent(BigInteger.valueOf(200));
        command.setPrice(BigInteger.valueOf(200000));
        command.setAddress(addressCommand);

        //when
        Apartment apartment = toApartment.convert(command);

        //then
        assertEquals(ID, apartment.getId());
        assertEquals(NUMBER_OF_ROOMS, apartment.getNumberOfRooms());
        assertEquals(TOTAL_AREA, apartment.getTotalArea());
        assertEquals(FLOOR, apartment.getFloor());
        assertEquals(APARTMENT_NUMBER, apartment.getApartmentNumber());
        assertEquals(DESCRIPTION, apartment.getDescription());
        assertEquals(PUBLISHED_DATE_TIME, apartment.getPublishedDateTime());
        assertEquals(CLOSED_DATE_TIME, apartment.getClosedDateTime());
        assertEquals(Status.FOR_RENT, apartment.getStatus());
        assertEquals(BigInteger.valueOf(200), apartment.getMonthRent());
        assertEquals(BigInteger.valueOf(200000), apartment.getPrice());
        assertEquals(ADDRESS_ID, apartment.getAddress().getId());
    }
}