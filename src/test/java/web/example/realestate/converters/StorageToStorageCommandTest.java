package web.example.realestate.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import web.example.realestate.commands.StorageCommand;
import web.example.realestate.domain.building.Address;
import web.example.realestate.domain.building.Storage;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class StorageToStorageCommandTest {

    private static final Long ID = 1L;
    private static final Integer NUMBER_OF_ROOMS = 5;
    private static final Integer TOTAL_AREA = 150;
    private static final String DESCRIPTION = "some desc";
    private static final LocalDateTime PUBLISHED_DATE_TIME = LocalDateTime.now();
    private static final LocalDateTime CLOSED_DATE_TIME = LocalDateTime.now();
    private static final Long ADDRESS_ID = 2l;
    private static final Integer COMMERCIAL_CAPACITY = 150;
    private static final boolean HAS_CARGO_EQUIPMENT = true;

    private StorageToStorageCommand toStorageCommand;

    @BeforeEach
    void setUp() {
        toStorageCommand = new StorageToStorageCommand(new AddressToAddressCommand());
    }

    @Test
    void testNullValue() {
        assertNull(toStorageCommand.convert(null));
    }

    @Test
    void testEmptyValue() {
        assertNotNull(toStorageCommand.convert(new Storage()));
    }

    @Test
    void convert() {
        //given
        Address address = new Address();
        address.setId(ADDRESS_ID);

        Storage storage = new Storage();
        storage.setId(ID);
        storage.setNumberOfRooms(NUMBER_OF_ROOMS);
        storage.setTotalArea(TOTAL_AREA);
        storage.setDescription(DESCRIPTION);
        storage.setCommercialCapacity(COMMERCIAL_CAPACITY);
        storage.setHasCargoEquipment(HAS_CARGO_EQUIPMENT);
        storage.setPublishedDateTime(PUBLISHED_DATE_TIME);
        storage.setClosedDateTime(CLOSED_DATE_TIME);
        storage.setAddress(address);

        //when
        StorageCommand command = toStorageCommand.convert(storage);

        //then
        assertEquals(ID, command.getId());
        assertEquals(NUMBER_OF_ROOMS, command.getNumberOfRooms());
        assertEquals(TOTAL_AREA, command.getTotalArea());
        assertEquals(DESCRIPTION, command.getDescription());
        assertEquals(COMMERCIAL_CAPACITY, command.getCommercialCapacity());
        assertEquals(HAS_CARGO_EQUIPMENT, command.isHasCargoEquipment());
        assertEquals(PUBLISHED_DATE_TIME, command.getPublishedDateTime());
        assertEquals(CLOSED_DATE_TIME, command.getClosedDateTime());
        assertEquals(ADDRESS_ID, command.getAddressCommand().getId());
    }
}