package web.example.realestate.services.implementation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import web.example.realestate.commands.AddressCommand;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Apartment;
import web.example.realestate.domain.enums.Status;
import web.example.realestate.services.FacilityService;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FacilityServiceImplIT {

    private static final Integer POSTCODE = 24399;
    private static final Integer FACILITY_NUMBER = 55;
    private static final String CITY = "City";
    private static final String DISTRICT = "District";
    private static final String STREET = "Street";
    private static final Integer APARTMENT_NUMBER = 86;
    private static final Integer FLOOR = 9;
    private static final Integer NUMBER_OF_ROOMS = 3;
    private static final Integer TOTAL_AREA = 70;
    private static final String DESCRIPTION = "desc";
    private static final BigInteger MONTH_RENT = BigInteger.valueOf(7000L);
    private static final BigInteger PRICE = BigInteger.valueOf(70000L);

    @Autowired
    private FacilityService facilityService;

    @Test
    @Transactional
    public void saveRawFacility() {
        //given
        AddressCommand address = new AddressCommand();
        address.setPostcode(POSTCODE);
        address.setFacilityNumber(FACILITY_NUMBER);
        address.setCity(CITY);
        address.setDistrict(DISTRICT);
        address.setStreet(STREET);

        FacilityCommand command = new FacilityCommand();
        command.setItApartment(true);
        command.setApartmentNumber(APARTMENT_NUMBER);
        command.setFloor(FLOOR);
        command.setNumberOfRooms(NUMBER_OF_ROOMS);
        command.setTotalArea(TOTAL_AREA);
        command.setDescription(DESCRIPTION);
        command.setMonthRent(MONTH_RENT);
        command.setPrice(PRICE);
        command.setStatus(Status.FOR_RENT);
        command.setAddress(address);

        //when
        Apartment apartment = (Apartment) facilityService.saveRawFacility(command);

        //then
        assertNotNull(apartment);
        assertEquals(POSTCODE, apartment.getAddress().getPostcode());
        assertEquals(FACILITY_NUMBER, apartment.getAddress().getFacilityNumber());
        assertEquals(CITY, apartment.getAddress().getCity());
        assertEquals(DISTRICT, apartment.getAddress().getDistrict());
        assertEquals(STREET, apartment.getAddress().getStreet());
        assertEquals(APARTMENT_NUMBER, apartment.getApartmentNumber());
        assertEquals(FLOOR, apartment.getFloor());
        assertEquals(NUMBER_OF_ROOMS, apartment.getNumberOfRooms());
        assertEquals(TOTAL_AREA, apartment.getTotalArea());
        assertEquals(DESCRIPTION, apartment.getDescription());
        assertEquals(MONTH_RENT, apartment.getMonthRent());
        assertEquals(PRICE, apartment.getPrice());
        assertEquals(Status.FOR_RENT, apartment.getStatus());
    }
}
