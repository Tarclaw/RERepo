package web.example.realestate.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import web.example.realestate.commands.ClientCommand;
import web.example.realestate.domain.building.Facility;
import web.example.realestate.domain.people.Client;
import web.example.realestate.domain.people.Contact;
import web.example.realestate.domain.people.RealEstateAgent;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class ClientToClientCommandTest {

    private static final Long ID = 1L;
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Odd";
    private static final String LOGIN = "johny";
    private static final String PASSWORD = "123";
    private static final String EMAIL = "odd@gmail.com";
    private static final String SKYPE = "odd123";
    private static final String MOBILE_NUMBER = "+1-035-888-55-33";
    private static final boolean IS_SELLER = true;
    private static final boolean IS_BUYER = true;
    private static final boolean IS_RENTER = true;
    private static final boolean IS_LEASER = true;
    private static final Long FACILITY_ID_1 = 2L;
    private static final Long FACILITY_ID_2 = 3L;
    private static final String AGENT_LOGIN_1 = "123";
    private static final String AGENT_LOGIN_2 = "qwerty";

    private ClientToClientCommand toClientCommand;

    @BeforeEach
    void setUp() {
        toClientCommand = new ClientToClientCommand(new FacilityToFacilityCommand(new AddressToAddressCommand()),
                new RealEstateAgentToRealEstateAgentCommand());
    }

    @Test
    void testNullValue() {
        assertNull(toClientCommand.convert(null));
    }

    @Test
    void testEmptyValue() {
        assertNotNull(toClientCommand.convert(new Client()));
    }

    @Test
    void convert() {
        //given
        Client client = new Client();
        client.setId(ID);
        client.setFirstName(FIRST_NAME);
        client.setLastName(LAST_NAME);
        client.setLogin(LOGIN);
        client.setPassword(PASSWORD);
        client.setContact(new Contact(EMAIL, SKYPE, MOBILE_NUMBER));
        client.setSeller(IS_SELLER);
        client.setBuyer(IS_BUYER);
        client.setRenter(IS_RENTER);
        client.setLeaser(IS_LEASER);

        Facility facility1 = new Facility();
        facility1.setId(FACILITY_ID_1);
        Facility facility2 = new Facility();
        facility2.setId(FACILITY_ID_2);
        client.setFacilities(new HashSet<>(Arrays.asList(facility1, facility2)));

        RealEstateAgent agent1 = new RealEstateAgent();
        agent1.setLogin(AGENT_LOGIN_1);
        RealEstateAgent agent2 = new RealEstateAgent();
        agent2.setLogin(AGENT_LOGIN_2);
        client.setRealEstateAgents(new HashSet<>(Arrays.asList(agent1, agent2)));

        //when
        ClientCommand command = toClientCommand.convert(client);

        //then
        assertEquals(ID, command.getId());
        assertEquals(FIRST_NAME, command.getFirstName());
        assertEquals(LAST_NAME, command.getLastName());
        assertEquals(LOGIN, command.getLogin());
        assertEquals(PASSWORD, command.getPassword());
        assertEquals(EMAIL, command.getEmail());
        assertEquals(SKYPE, command.getSkype());
        assertEquals(MOBILE_NUMBER, command.getMobileNumber());
        assertEquals(IS_SELLER, command.isSeller());
        assertEquals(IS_BUYER, command.isBuyer());
        assertEquals(IS_RENTER, command.isRenter());
        assertEquals(IS_LEASER, command.isLeaser());
        //assertEquals(2, command.getFacilityCommands().size()); //todo mapping issue
        assertEquals(2, command.getRealEstateAgentCommands().size());
    }
}