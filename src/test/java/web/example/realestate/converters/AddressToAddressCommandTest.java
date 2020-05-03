package web.example.realestate.converters;

import org.junit.Before;
import org.junit.Test;
import web.example.realestate.commands.AddressCommand;
import web.example.realestate.domain.building.Address;

import static org.junit.Assert.*;

public class AddressToAddressCommandTest {

    private static final Long ID = 1L;
    private static final Integer POST_CODE = 584000;
    private static final Integer FACILITY_NUMBER = 13;
    private static final String CITY = "Chicago";
    private static final String DISTRICT = "IL";
    private static final String STREET = "Some Ave";

    private AddressToAddressCommand toAddressCommand;

    @Before
    public void setUp() {
        toAddressCommand = new AddressToAddressCommand();
    }

    @Test
    public void nullValue() {
        assertNull(toAddressCommand.convert(null));
    }

    @Test
    public void emptyValue() {
        assertNotNull(toAddressCommand.convert(new Address()));
    }

    @Test
    public void convert() {
        //given
        Address address = new Address();
        address.setId(ID);
        address.setCity(CITY);
        address.setDistrict(DISTRICT);
        address.setFacilityNumber(FACILITY_NUMBER);
        address.setPostcode(POST_CODE);
        address.setStreet(STREET);

        //when
        AddressCommand command = toAddressCommand.convert(address);

        //then
        assertNotNull(address);
        assertEquals(ID, command.getId());
        assertEquals(CITY, command.getCity());
        assertEquals(DISTRICT, command.getDistrict());
        assertEquals(FACILITY_NUMBER, command.getFacilityNumber());
        assertEquals(POST_CODE, command.getPostcode());
        assertEquals(STREET, command.getStreet());
    }
}