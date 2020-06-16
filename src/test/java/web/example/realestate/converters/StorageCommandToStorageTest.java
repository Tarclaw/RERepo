package web.example.realestate.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import web.example.realestate.commands.AddressCommand;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Storage;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class StorageCommandToStorageTest {

    private static final Long ID = 1L;
    private static final Integer NUMBER_OF_ROOMS = 5;
    private static final Integer TOTAL_AREA = 150;
    private static final String DESCRIPTION = "some desc";
    private static final LocalDateTime PUBLISHED_DATE_TIME = LocalDateTime.now();
    private static final LocalDateTime CLOSED_DATE_TIME = LocalDateTime.now();
    private static final Long ADDRESS_ID = 2l;
    private static final Integer COMMERCIAL_CAPACITY = 150;
    private static final boolean HAS_CARGO_EQUIPMENT = true;

    private StorageCommandToStorage toStorage;

    @BeforeEach
    void setUp() {
        toStorage = new StorageCommandToStorage(new AddressCommandToAddress());
    }

    @Test
    void testNullValue() {
        assertNull(toStorage.convert(null));
    }

    @Test
    void testEmptyValue() {
        FacilityCommand command = new FacilityCommand();
        command.setAddress(new AddressCommand());
        assertNotNull(toStorage.convert(command));
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
        command.setDescription(DESCRIPTION);
        command.setCommercialCapacity(COMMERCIAL_CAPACITY);
        command.setHasCargoEquipment(HAS_CARGO_EQUIPMENT);
        command.setPublishedDateTime(PUBLISHED_DATE_TIME);
        command.setClosedDateTime(CLOSED_DATE_TIME);
        command.setAddress(addressCommand);

        //when
        Storage storage = toStorage.convert(command);

        //then
        assertEquals(ID, storage.getId());
        assertEquals(NUMBER_OF_ROOMS, storage.getNumberOfRooms());
        assertEquals(TOTAL_AREA, storage.getTotalArea());
        assertEquals(DESCRIPTION, storage.getDescription());
        assertEquals(COMMERCIAL_CAPACITY, storage.getCommercialCapacity());
        assertEquals(HAS_CARGO_EQUIPMENT, storage.isHasCargoEquipment());
        assertEquals(PUBLISHED_DATE_TIME, storage.getPublishedDateTime());
        assertEquals(CLOSED_DATE_TIME, storage.getClosedDateTime());
        assertEquals(ADDRESS_ID, storage.getAddress().getId());
    }
}