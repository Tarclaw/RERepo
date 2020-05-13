package web.example.realestate.services.implementation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import web.example.realestate.commands.RealEstateAgentCommand;
import web.example.realestate.converters.RealEstateAgentToRealEstateAgentCommand;
import web.example.realestate.domain.people.RealEstateAgent;
import web.example.realestate.repositories.RealEstateAgentRepository;
import web.example.realestate.services.RealEstateAgentService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RealEstateAgentServiceImplIT {

    private static final String OLD_NAME = "Billy";
    private static final String OLD_SURNAME = "Butkiss";
    private static final String NEW_NAME = "Freddy";
    private static final String NEW_SURNAME = "Buypliz";


    @Autowired
    private RealEstateAgentService service;

    @Autowired
    private RealEstateAgentRepository repository;

    @Autowired
    private RealEstateAgentToRealEstateAgentCommand toAgentcommand;

    @Test
    @Transactional
    public void saveRealEstateAgentCommand() {
        //given
        RealEstateAgent agent = repository
                .findRealEstateAgentsByFirstNameAndLastName(OLD_NAME, OLD_SURNAME)
                .orElseThrow(() -> new RuntimeException());
        agent.setFirstName(NEW_NAME);
        agent.setLastName(NEW_SURNAME);

        //when
        RealEstateAgentCommand command = service.saveRealEstateAgentCommand(toAgentcommand.convert(agent));

        //then
        assertNotNull(command);
        assertNotEquals(OLD_NAME, command.getFirstName());
        assertNotEquals(OLD_SURNAME, command.getLastName());
        assertEquals(NEW_NAME, command.getFirstName());
        assertEquals(NEW_SURNAME, command.getLastName());
        assertEquals(agent.getId(), command.getId());
    }

}
