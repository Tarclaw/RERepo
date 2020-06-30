package web.example.realestate.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import web.example.realestate.commands.AddressCommand;
import web.example.realestate.commands.ClientCommand;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.commands.RealEstateAgentCommand;
import web.example.realestate.domain.people.Client;

import static org.junit.jupiter.api.Assertions.*;

class ClientCommandToClientTest {

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

    private ClientCommandToClient toClient;

    @BeforeEach
    void setUp() {
        AddressCommandToAddress toAddress = new AddressCommandToAddress();
        FacilityCommandToFacility toFacility = new FacilityCommandToFacility(new ApartmentCommandToApartment(toAddress),
                                                                             new BasementCommandToBasement(toAddress),
                                                                             new GarageCommandToGarage(toAddress),
                                                                             new HouseCommandToHouse(toAddress),
                                                                             new StorageCommandToStorage(toAddress));
        toClient = new ClientCommandToClient(toFacility, new RealEstateAgentCommandToRealEstateAgent());
    }

    @Test
    void testNullValue() {
        assertNull(toClient.convert(null));
    }

    @Test
    void testEmptyValue() {
        assertNotNull(toClient.convert(new ClientCommand()));
    }

    @Test
    void convert() {
        //given
        ClientCommand command = new ClientCommand();
        command.setId(ID);
        command.setFirstName(FIRST_NAME);
        command.setLastName(LAST_NAME);
        command.setLogin(LOGIN);
        command.setPassword(PASSWORD);
        command.setEmail(EMAIL);
        command.setSkype(SKYPE);
        command.setMobileNumber(MOBILE_NUMBER);
        /*command.setSeller(IS_SELLER);
        command.setBuyer(IS_BUYER);
        command.setRenter(IS_RENTER);
        command.setLeaser(IS_LEASER);*/

        FacilityCommand apartment = new FacilityCommand();
        apartment.setId(FACILITY_ID_1);
        apartment.setItApartment(true);
        apartment.setAddress(new AddressCommand());
        FacilityCommand basement = new FacilityCommand();
        basement.setId(FACILITY_ID_2);
        basement.setItBasement(true);
        basement.setAddress(new AddressCommand());
        command.getFacilityCommands().add(apartment);
        command.getFacilityCommands().add(basement);

        RealEstateAgentCommand agentCommand1 = new RealEstateAgentCommand();
        agentCommand1.setLogin(AGENT_LOGIN_1);
        RealEstateAgentCommand agentCommand2 = new RealEstateAgentCommand();
        agentCommand2.setLogin(AGENT_LOGIN_2);
        command.getRealEstateAgentCommands().add(agentCommand1);
        command.getRealEstateAgentCommands().add(agentCommand2);

        //when
        Client client = toClient.convert(command);

        //then
        assertEquals(ID, client.getId());
        assertEquals(FIRST_NAME, client.getFirstName());
        assertEquals(LAST_NAME, client.getLastName());
        assertEquals(LOGIN, client.getLogin());
        assertEquals(PASSWORD, client.getPassword());
        assertEquals(EMAIL, client.getContact().getEmail());
        assertEquals(SKYPE, client.getContact().getSkype());
        assertEquals(MOBILE_NUMBER, client.getContact().getMobileNumber());
        /*assertEquals(IS_SELLER, client.isSeller());
        assertEquals(IS_BUYER, client.isBuyer());
        assertEquals(IS_RENTER, client.isRenter());
        assertEquals(IS_LEASER, client.isLeaser());*/
        assertEquals(2, client.getFacilities().size());
        assertEquals(2, client.getRealEstateAgents().size());
    }
}