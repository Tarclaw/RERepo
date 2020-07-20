package web.example.realestate.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.ClientCommand;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.commands.RealEstateAgentCommand;
import web.example.realestate.domain.people.Client;
import web.example.realestate.domain.people.Contact;

import java.util.HashSet;
import java.util.Set;

@Component
public class ClientToClientCommand implements Converter<Client, ClientCommand> {

    private final FacilityToFacilityCommand toFacilityCommand;
    private final RealEstateAgentToRealEstateAgentCommand toAgentCommand;

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientToClientCommand.class);

    public ClientToClientCommand(FacilityToFacilityCommand toFacilityCommand,
                                 RealEstateAgentToRealEstateAgentCommand toAgentCommand) {
        this.toFacilityCommand = toFacilityCommand;
        this.toAgentCommand = toAgentCommand;
        LOGGER.info("New instance of ClientToClientCommand created.");
    }

    @Override
    public ClientCommand convert(Client client) {
        LOGGER.trace("Enter in 'ClientToClientCommand.convert' method");

        if (client == null) {
            LOGGER.debug("Client is null");
            return null;
        }

        if (client.getContact() == null) {
            LOGGER.debug("Set empty Contact fr Client");
            client.setContact(new Contact("", "", ""));
        }

        final ClientCommand command = new ClientCommand();
        command.setId(client.getId());
        command.setFirstName(client.getFirstName());
        command.setLastName(client.getLastName());
        command.setLogin(client.getLogin());
        command.setPassword(client.getPassword());
        command.setEmail(client.getContact().getEmail());
        command.setSkype(client.getContact().getSkype());
        command.setMobileNumber(client.getContact().getMobileNumber());
        command.setCustomerRequirements(client.getCustomerRequirements());

        if (client.getFacilities() != null && client.getFacilities().size() > 0) {
            Set<FacilityCommand> source = new HashSet<>();
            client.getFacilities().forEach(facility -> source.add(toFacilityCommand.convert(facility)));
            command.setFacilityCommands(source);
            LOGGER.debug("Set FacilityCommands for ClientCommand");
        }

        if (client.getRealEstateAgents() != null && client.getRealEstateAgents().size() > 0) {
            Set<RealEstateAgentCommand> source = new HashSet<>();
            client.getRealEstateAgents().forEach(agent -> source.add(toAgentCommand.convert(agent)));
            command.setRealEstateAgentCommands(source);
            LOGGER.debug("Set AgentCommands for ClientCommand");
        }

        LOGGER.trace("'ClientToClientCommand.convert' executed successfully.");
        return command;
    }
}
