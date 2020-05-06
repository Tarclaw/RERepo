package web.example.realestate.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.commands.FacilityObjectCommand;
import web.example.realestate.commands.RealEstateAgentCommand;
import web.example.realestate.domain.building.FacilityObject;
import web.example.realestate.domain.enums.Status;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class FacilityObjectCommandToFacilityObjectTest {

    private static final Long ID = 1L;
    private static final BigInteger MONTH_RENT = BigInteger.valueOf(500);
    private static final BigInteger PRICE = BigInteger.valueOf(50000);
    private static final BigInteger COMMISSION_AMOUNT = BigInteger.valueOf(50);
    private static final Status STATUS = Status.FOR_RENT;
    private static final Long FACILITY_ID_1 = 2L;
    private static final Long FACILITY_ID_2 = 3L;
    private static final Long AGENT_ID = 4L;

    private FacilityObjectCommandToFacilityObject toFacilityObject;

    @BeforeEach
    void setUp() {
        toFacilityObject = new FacilityObjectCommandToFacilityObject(new RealEstateAgentCommandToRealEstateAgent(),
                new FacilityCommandToFacility(new AddressCommandToAddress()));
    }

    @Test
    void testNullValue() {
        assertNull(toFacilityObject.convert(null));
    }

    @Test
    void testEmptyValue() {
        assertNotNull(toFacilityObject.convert(new FacilityObjectCommand()));
    }

    @Test
    void convert() {
        //given
        FacilityObjectCommand command = new FacilityObjectCommand();
        command.setId(ID);
        command.setMonthRent(MONTH_RENT);
        command.setPrice(PRICE);
        command.setCommissionAmount(COMMISSION_AMOUNT);
        command.setStatus(STATUS);

        RealEstateAgentCommand agentCommand = new RealEstateAgentCommand();
        agentCommand.setId(AGENT_ID);
        command.setRealEstateAgentCommand(agentCommand);

        FacilityCommand facilityCommand1 = new FacilityCommand();
        facilityCommand1.setId(FACILITY_ID_1);

        FacilityCommand facilityCommand2 = new FacilityCommand();
        facilityCommand2.setId(FACILITY_ID_2);

        command.getFacilityCommands().add(facilityCommand1);
        command.getFacilityCommands().add(facilityCommand2);

        //when
        FacilityObject facilityObject = toFacilityObject.convert(command);

        //then
        assertEquals(ID, facilityObject.getId());
        assertEquals(MONTH_RENT, facilityObject.getMonthRent());
        assertEquals(PRICE, facilityObject.getPrice());
        assertEquals(PRICE, facilityObject.getPrice());
        assertEquals(COMMISSION_AMOUNT, facilityObject.getCommissionAmount());
        assertEquals(STATUS, facilityObject.getStatus());
        assertEquals(AGENT_ID, facilityObject.getAgent().getId());
        assertEquals(2, facilityObject.getFacilities().size());
    }
}