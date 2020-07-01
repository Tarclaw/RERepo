package web.example.realestate.services.implementation;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import web.example.realestate.commands.AddressCommand;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.converters.StorageToStorageCommand;
import web.example.realestate.domain.building.Storage;
import web.example.realestate.domain.enums.Status;
import web.example.realestate.domain.people.Client;
import web.example.realestate.repositories.StorageRepository;
import web.example.realestate.services.StorageService;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StorageServiceImplIT {

    private static final Integer OLD_COMMERCIAL_CAPACITY = 130;
    private static final Integer NEW_COMMERCIAL_CAPACITY = 230;

    private static final Integer POSTCODE = 24399;
    private static final Integer FACILITY_NUMBER = 55;
    private static final String CITY = "City";
    private static final String DISTRICT = "District";
    private static final String STREET = "Street";
    private static final Integer NUMBER_OF_ROOMS = 3;
    private static final Integer TOTAL_AREA = 70;
    private static final Integer COMMERCIAL_CAPACITY = 800;
    private static final String DESCRIPTION = "desc";
    private static final BigInteger MONTH_RENT = BigInteger.valueOf(7000L);
    private static final BigInteger PRICE = BigInteger.valueOf(70000L);
    private static final Long CLIENT_ID = 3L;

    @Autowired
    private StorageService storageService;

    @Autowired
    private StorageRepository repository;

    @Autowired
    private StorageToStorageCommand toStorageCommand;

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
        facilityCommand.setItStorage(true);
        facilityCommand.setCommercialCapacity(COMMERCIAL_CAPACITY);
        facilityCommand.setHasCargoEquipment(true);
        facilityCommand.setNumberOfRooms(NUMBER_OF_ROOMS);
        facilityCommand.setTotalArea(TOTAL_AREA);
        facilityCommand.setDescription(DESCRIPTION);
        facilityCommand.setMonthRent(MONTH_RENT);
        facilityCommand.setPrice(PRICE);
        facilityCommand.setStatus(Status.FOR_RENT);
        facilityCommand.setAddress(address);

        //when
        FacilityCommand savedCommand = storageService.saveDetached(facilityCommand);

        //then
        Assertions.assertNotNull(savedCommand);
        Assertions.assertNotNull(savedCommand.getId());
        Assertions.assertNotNull(savedCommand.getAddress().getId());
    }

    @Test
    @Transactional
    public void saveAttached() {
        //given
        Client client = new Client();
        client.setId(CLIENT_ID);
        Storage storage = repository
                .findStoragesByCommercialCapacityGreaterThan(OLD_COMMERCIAL_CAPACITY)
                .orElseThrow(() -> new RuntimeException());
        storage.setCommercialCapacity(NEW_COMMERCIAL_CAPACITY);
        storage.setClient(client);

        //when
        FacilityCommand command = storageService.saveAttached(toStorageCommand.convert(storage));

        //then
        assertNotNull(command);
        assertNotEquals(OLD_COMMERCIAL_CAPACITY, command.getCommercialCapacity());
        assertEquals(NEW_COMMERCIAL_CAPACITY, command.getCommercialCapacity());
        assertEquals(storage.getId(), command.getId());
    }
}
