package web.example.realestate.services.implementation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import web.example.realestate.commands.AddressCommand;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.converters.ApartmentToApartmentCommand;
import web.example.realestate.domain.building.Apartment;
import web.example.realestate.domain.enums.Status;
import web.example.realestate.repositories.ApartmentRepository;
import web.example.realestate.services.ApartmentService;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApartmentServiceImplIT {

    private static final Integer OLD_TOTAL_AREA = 150;
    private static final Integer NEW_TOTAL_AREA = 165;

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
    private static final Long CLIENT_ID = 3L;

    @Autowired
    private ApartmentService service;

    @Autowired
    private ApartmentRepository repository;

    @Autowired
    private ApartmentToApartmentCommand toApartmentCommand;

    @Test
    @Transactional
    public void saveDetached() {
        //given
        AddressCommand address = new AddressCommand();
        address.setPostcode(POSTCODE);
        address.setFacilityNumber(FACILITY_NUMBER);
        address.setCity(CITY);
        address.setDistrict(DISTRICT);
        address.setStreet(STREET);

        FacilityCommand facilityCommand = new FacilityCommand();
        facilityCommand.setClientId(CLIENT_ID);
        facilityCommand.setItApartment(true);
        facilityCommand.setApartmentNumber(APARTMENT_NUMBER);
        facilityCommand.setFloor(FLOOR);
        facilityCommand.setNumberOfRooms(NUMBER_OF_ROOMS);
        facilityCommand.setTotalArea(TOTAL_AREA);
        facilityCommand.setDescription(DESCRIPTION);
        facilityCommand.setMonthRent(MONTH_RENT);
        facilityCommand.setPrice(PRICE);
        facilityCommand.setStatus(Status.FOR_RENT);
        facilityCommand.setAddress(address);

        //when
        FacilityCommand savedCommand = service.saveDetached(facilityCommand);

        //then
        assertNotNull(savedCommand);
        assertNotNull(savedCommand.getId());
        assertNotNull(savedCommand.getAddress().getId());
    }

    @Test
    @Transactional
    public void saveAttached() {
        //given
        Apartment apartment = repository.findApartmentsByTotalArea(OLD_TOTAL_AREA).orElseThrow(() -> new RuntimeException());
        apartment.setTotalArea(NEW_TOTAL_AREA);

        //when
        FacilityCommand command = service.saveAttached(toApartmentCommand.convert(apartment));

        //then
        assertNotNull(command);
        assertNotEquals(OLD_TOTAL_AREA, command.getTotalArea());
        assertEquals(NEW_TOTAL_AREA, command.getTotalArea());
        assertEquals(apartment.getId(), command.getId());
    }
}
