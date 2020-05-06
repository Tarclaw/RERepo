package web.example.realestate.converters;

import org.junit.jupiter.api.Test;
import web.example.realestate.commands.RealEstateAgentCommand;
import web.example.realestate.domain.people.RealEstateAgent;

import java.math.BigInteger;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class RealEstateAgentCommandToRealEstateAgentTest {

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

    private RealEstateAgentCommandToRealEstateAgent toAgent = new RealEstateAgentCommandToRealEstateAgent();

    @Test
    void testNullValue() {
        assertNull(toAgent.convert(null));
    }

    @Test
    void testEmptyValue() {
        assertNotNull(toAgent.convert(new RealEstateAgentCommand()));
    }

    @Test
    void convert() {
        //given
        RealEstateAgentCommand command = new RealEstateAgentCommand();
        command.setId(ID);
        command.setFirstName(FIRST_NAME);
        command.setLastName(LAST_NAME);
        command.setLogin(LOGIN);
        command.setPassword(PASSWORD);
        command.setEmail(EMAIL);
        command.setSkype(SKYPE);
        command.setMobileNumber(MOBILE_NUMBER);
        command.setSalary(SALARY);
        command.setHiredDate(HIRED_DATE);
        command.setQuitDate(QUIT_DATE);

        //when
        final RealEstateAgent agent = toAgent.convert(command);

        //then
        assertEquals(ID, agent.getId());
        assertEquals(FIRST_NAME, agent.getFirstName());
        assertEquals(LAST_NAME, agent.getLastName());
        assertEquals(LOGIN, agent.getLogin());
        assertEquals(PASSWORD, agent.getPassword());
        assertEquals(EMAIL, agent.getContact().getEmail());
        assertEquals(SKYPE, agent.getContact().getSkype());
        assertEquals(MOBILE_NUMBER, agent.getContact().getMobileNumber());
        assertEquals(SALARY, agent.getSalary());
        assertEquals(HIRED_DATE, agent.getHiredDate());
        assertEquals(QUIT_DATE, agent.getQuitDate());
    }
}