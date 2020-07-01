package web.example.realestate.converters;

import org.junit.jupiter.api.Test;
import web.example.realestate.commands.RealEstateAgentCommand;
import web.example.realestate.domain.people.Client;
import web.example.realestate.domain.people.Contact;
import web.example.realestate.domain.people.RealEstateAgent;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class RealEstateAgentToRealEstateAgentCommandTest {

    private static final Long ID = 1L;
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Odd";
    private static final String LOGIN = "johny";
    private static final String PASSWORD = "123";
    private static final String EMAIL = "odd@gmail.com";
    private static final String SKYPE = "odd123";
    private static final String MOBILE_NUMBER = "+1-035-888-55-33";
    private static final BigInteger SALARY = BigInteger.valueOf(5000);
    private static final LocalDate HIRED_DATE = LocalDate.now();
    private static final LocalDate QUIT_DATE = null;

    private RealEstateAgentToRealEstateAgentCommand toAgentCommand = new RealEstateAgentToRealEstateAgentCommand();

    @Test
    void testNullValue() {
        assertNull(toAgentCommand.convert(null));
    }

    @Test
    void testEmptyValue() {
        assertNotNull(toAgentCommand.convert(new RealEstateAgent()));
    }

    @Test
    void convert() {
        //given
        Client client = new Client();
        client.setId(ID);
        Set<Client> clients = new HashSet<>();
        clients.add(client);

        RealEstateAgent agent = new RealEstateAgent();
        agent.setId(ID);
        agent.setFirstName(FIRST_NAME);
        agent.setLastName(LAST_NAME);
        agent.setLogin(LOGIN);
        agent.setPassword(PASSWORD);
        agent.setContact(new Contact(EMAIL, SKYPE, MOBILE_NUMBER));
        agent.setSalary(SALARY);
        agent.setHiredDate(HIRED_DATE);
        agent.setQuitDate(QUIT_DATE);
        agent.setClients(clients);

        //when
        final RealEstateAgentCommand command = toAgentCommand.convert(agent);

        //then
        assertEquals(ID, command.getId());
        assertEquals(FIRST_NAME, command.getFirstName());
        assertEquals(LAST_NAME, command.getLastName());
        assertEquals(LOGIN, command.getLogin());
        assertEquals(PASSWORD, command.getPassword());
        assertEquals(EMAIL, command.getEmail());
        assertEquals(SKYPE, command.getSkype());
        assertEquals(MOBILE_NUMBER, command.getMobileNumber());
        assertEquals(SALARY, command.getSalary());
        assertEquals(HIRED_DATE, command.getHiredDate());
        assertEquals(QUIT_DATE, command.getQuitDate());
        assertEquals(clients.size(), command.getClientIds().size());
    }
}