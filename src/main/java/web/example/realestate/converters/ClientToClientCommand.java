package web.example.realestate.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.ClientCommand;
import web.example.realestate.commands.RealEstateAgentCommand;
import web.example.realestate.domain.people.Client;
import web.example.realestate.domain.people.Contact;

import java.util.HashSet;
import java.util.Set;

@Component
public class ClientToClientCommand implements Converter<Client, ClientCommand> {

    private final FacilityToFacilityCommand toFacilityCommand;
    private final RealEstateAgentToRealEstateAgentCommand toAgentCommand;

    public ClientToClientCommand(FacilityToFacilityCommand toFacilityCommand,
                                 RealEstateAgentToRealEstateAgentCommand toAgentCommand) {
        this.toFacilityCommand = toFacilityCommand;
        this.toAgentCommand = toAgentCommand;
    }

    @Override
    public ClientCommand convert(Client client) {
        if (client == null) {
            return null;
        }

        if (client.getContact() == null) {
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
        command.setSeller(client.isSeller());
        command.setBuyer(client.isBuyer());
        command.setRenter(client.isRenter());
        command.setLeaser(client.isLeaser());

        /*if (client.getFacilities() != null && client.getFacilities().size() > 0) {
            Set<FacilityCommand> source = new HashSet<>();
            client.getFacilities().forEach(facility -> source.add(toFacilityCommand.convert(facility)));
            command.setFacilityCommands(source);
        }*/

        if (client.getRealEstateAgents() != null && client.getRealEstateAgents().size() > 0) {
            Set<RealEstateAgentCommand> source = new HashSet<>();
            client.getRealEstateAgents().forEach(agent -> source.add(toAgentCommand.convert(agent)));
            command.setRealEstateAgentCommands(source);
        }

        return command;
    }
}
