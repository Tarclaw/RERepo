package web.example.realestate.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.RealEstateAgentCommand;
import web.example.realestate.domain.people.Contact;
import web.example.realestate.domain.people.RealEstateAgent;

@Component
public class RealEstateAgentToRealEstateAgentCommand implements Converter<RealEstateAgent, RealEstateAgentCommand> {

    @Override
    public RealEstateAgentCommand convert(final RealEstateAgent agent) {
        if (agent == null) {
            return null;
        }

        if (agent.getContact() == null) {
            agent.setContact(new Contact("", "", ""));
        }

        final RealEstateAgentCommand command = new RealEstateAgentCommand();
        command.setId(agent.getId());
        command.setFirstName(agent.getFirstName());
        command.setLastName(agent.getLastName());
        command.setLogin(agent.getLogin());
        command.setPassword(agent.getPassword());
        command.setEmail(agent.getContact().getEmail());
        command.setSkype(agent.getContact().getSkype());
        command.setMobileNumber(agent.getContact().getMobileNumber());
        command.setSalary(agent.getSalary());
        command.setHiredDate(agent.getHiredDate());
        command.setQuitDate(agent.getQuitDate());
        agent.getClients().forEach(client -> command.getClientIds().add(client.getId()));

        return command;
    }
}
