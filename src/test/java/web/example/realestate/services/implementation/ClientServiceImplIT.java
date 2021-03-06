package web.example.realestate.services.implementation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import web.example.realestate.commands.BasementCommand;
import web.example.realestate.commands.ClientCommand;
import web.example.realestate.converters.BasementToBasementCommand;
import web.example.realestate.converters.ClientToClientCommand;
import web.example.realestate.converters.FacilityToFacilityCommand;
import web.example.realestate.converters.RealEstateAgentToRealEstateAgentCommand;
import web.example.realestate.domain.building.Basement;
import web.example.realestate.domain.building.Facility;
import web.example.realestate.domain.people.Client;
import web.example.realestate.domain.people.RealEstateAgent;
import web.example.realestate.repositories.BasementRepository;
import web.example.realestate.repositories.ClientRepository;
import web.example.realestate.services.BasementService;
import web.example.realestate.services.ClientService;

import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientServiceImplIT {

    private static final String OLD_SURNAME = "Johnson";
    private static final String NEW_SURNAME = "Clark";

    @Autowired
    private ClientService service;

    @Autowired
    private ClientRepository repository;

    @Autowired
    private ClientToClientCommand toClientCommand;

    @Test
    @Transactional
    public void saveClientCommand() {
        //given
        Client client = repository.findClientsByLastName(OLD_SURNAME).orElseThrow();
        client.setLastName(NEW_SURNAME);

        //when
        ClientCommand command = service.saveClientCommand(toClientCommand.convert(client));

        //then
        assertNotNull(command);
        assertNotEquals(OLD_SURNAME, command.getLastName());
        assertEquals(NEW_SURNAME, command.getLastName());
        assertEquals(client.getId(), command.getId());
    }


}
