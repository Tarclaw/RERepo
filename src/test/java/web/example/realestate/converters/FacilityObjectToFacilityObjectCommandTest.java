package web.example.realestate.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import web.example.realestate.commands.FacilityObjectCommand;
import web.example.realestate.domain.building.Facility;
import web.example.realestate.domain.building.FacilityObject;
import web.example.realestate.domain.enums.Status;
import web.example.realestate.domain.people.RealEstateAgent;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class FacilityObjectToFacilityObjectCommandTest {

    private static final Long ID = 1L;
    private static final BigInteger MONTH_RENT = BigInteger.valueOf(500);
    private static final BigInteger PRICE = BigInteger.valueOf(50000);
    private static final BigInteger COMMISSION_AMOUNT = BigInteger.valueOf(50);
    private static final Status STATUS = Status.FOR_RENT;
    private static final Long FACILITY_ID_1 = 2L;
    private static final Long FACILITY_ID_2 = 3L;
    private static final Long AGENT_ID = 4L;

    private FacilityObjectToFacilityObjectCommand toFacilityObjectCommand;

    @BeforeEach
    void setUp() {
        toFacilityObjectCommand = new FacilityObjectToFacilityObjectCommand(new RealEstateAgentToRealEstateAgentCommand(),
                new FacilityToFacilityCommand(new AddressToAddressCommand()));
    }

    @Test
    void testNullValue() {
        assertNull(toFacilityObjectCommand.convert(null));
    }

    @Test
    void testEmptyValue() {
        assertNotNull(toFacilityObjectCommand.convert(new FacilityObject()));
    }

    @Test
    void convert() {
        //given
        RealEstateAgent agent = new RealEstateAgent();
        agent.setId(AGENT_ID);
        Facility facility1 = new Facility();
        facility1.setId(FACILITY_ID_1);
        Facility facility2 = new Facility();
        facility2.setId(FACILITY_ID_2);

        FacilityObject facilityObject = new FacilityObject();
        facilityObject.setId(ID);
        facilityObject.setMonthRent(MONTH_RENT);
        facilityObject.setPrice(PRICE);
        facilityObject.setCommissionAmount(COMMISSION_AMOUNT);
        facilityObject.setStatus(STATUS);
        facilityObject.setAgent(agent);
        facilityObject.setFacilities(new HashSet<>(Arrays.asList(facility1, facility2)));

        //when
        FacilityObjectCommand command = toFacilityObjectCommand.convert(facilityObject);

        //then
        assertEquals(ID, command.getId());
        assertEquals(MONTH_RENT, command.getMonthRent());
        assertEquals(PRICE, command.getPrice());
        assertEquals(PRICE, command.getPrice());
        assertEquals(COMMISSION_AMOUNT, command.getCommissionAmount());
        assertEquals(STATUS, command.getStatus());
        assertEquals(AGENT_ID, command.getRealEstateAgentCommand().getId());
        //assertEquals(2, command.getFacilityCommands().size());
    }
}