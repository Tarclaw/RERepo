package web.example.realestate.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.ClientCommand;
import web.example.realestate.domain.building.Facility;
import web.example.realestate.domain.people.Client;
import web.example.realestate.domain.people.Contact;
import web.example.realestate.domain.people.RealEstateAgent;

import java.util.HashSet;
import java.util.Set;

@Component
public class ClientCommandToClient implements Converter<ClientCommand, Client> {

    private final FacilityCommandToFacility toFacility;
    private final RealEstateAgentCommandToRealEstateAgent toAgent;

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientCommandToClient.class);

    public ClientCommandToClient(FacilityCommandToFacility toFacility,
                                 RealEstateAgentCommandToRealEstateAgent toAgent) {
        this.toFacility = toFacility;
        this.toAgent = toAgent;
        LOGGER.info("New instance of ClientCommandToClient created.");
    }

    @Override
    public Client convert(ClientCommand command) {
        LOGGER.trace("Enter in 'ClientCommandToClient.convert' method");

        if (command == null) {
            LOGGER.debug("ClientCommand is null");
            return null;
        }

        final Client client = new Client();
        client.setId(command.getId());
        client.setFirstName(command.getFirstName());
        client.setLastName(command.getLastName());
        client.setLogin(command.getLogin());
        client.setPassword(command.getPassword());
        client.setContact(new Contact(command.getEmail(),
                                      command.getSkype(),
                                      command.getMobileNumber()));
        client.setCustomerRequirements(command.getCustomerRequirements());

        if (command.getFacilityCommands() != null && command.getFacilityCommands().size() > 0) {
            Set<Facility> source = new HashSet<>();
            command.getFacilityCommands().forEach(facilityCommand -> source.add(toFacility.convert(facilityCommand)));
            client.setFacilities(source);
            LOGGER.debug("Set Client Facilities");
        }

        if (command.getRealEstateAgentCommands() != null && command.getRealEstateAgentCommands().size() > 0) {
            Set<RealEstateAgent> source = new HashSet<>();
            command.getRealEstateAgentCommands().forEach(agentCommand -> source.add(toAgent.convert(agentCommand)));
            client.setRealEstateAgents(source);
            LOGGER.debug("Set Client Agents");
        }

        LOGGER.trace("'ClientCommandToClient.convert' executed successfully.");
        return client;
    }
}
