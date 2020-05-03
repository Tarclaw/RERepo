package web.example.realestate.converters;

import org.junit.Before;
import org.junit.Test;
import web.example.realestate.commands.AddressCommand;
import web.example.realestate.domain.building.Address;

import static org.junit.Assert.*;

public class AddressCommandToAddressTest {

    private static final Long ID = 1L;
    private static final Integer POST_CODE = 584000;
    private static final Integer FACILITY_NUMBER = 13;
    private static final String CITY = "Chicago";
    private static final String DISTRICT = "IL";
    private static final String STREET = "Some Ave";

    private AddressCommandToAddress toAddress;

    @Before
    public void setUp() {
        toAddress = new AddressCommandToAddress();
    }

    @Test
    public void nullValue() {
        assertNull(toAddress.convert(null));
    }

    @Test
    public void emptyValue() {
        assertNotNull(toAddress.convert(new AddressCommand()));
    }

    @Test
    public void convert() {
        //given
        AddressCommand command = new AddressCommand();
        command.setId(ID);
        command.setCity(CITY);
        command.setDistrict(DISTRICT);
        command.setFacilityNumber(FACILITY_NUMBER);
        command.setPostcode(POST_CODE);
        command.setStreet(STREET);

        //when
        Address address = toAddress.convert(command);

        //then
        assertNotNull(address);
        assertEquals(ID, address.getId());
        assertEquals(CITY, address.getCity());
        assertEquals(DISTRICT, address.getDistrict());
        assertEquals(FACILITY_NUMBER, address.getFacilityNumber());
        assertEquals(POST_CODE, address.getPostcode());
        assertEquals(STREET, address.getStreet());
    }
}