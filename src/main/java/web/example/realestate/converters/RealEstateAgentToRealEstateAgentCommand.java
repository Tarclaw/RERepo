package web.example.realestate.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.RealEstateAgentCommand;
import web.example.realestate.domain.people.Contact;
import web.example.realestate.domain.people.RealEstateAgent;

@Component
public class RealEstateAgentToRealEstateAgentCommand implements Converter<RealEstateAgent, RealEstateAgentCommand> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RealEstateAgentToRealEstateAgentCommand.class);

    @Override
    public RealEstateAgentCommand convert(final RealEstateAgent agent) {
        LOGGER.trace("Enter in 'RealEstateAgentToRealEstateAgentCommand.convert' method");

        if (agent == null) {
            LOGGER.debug("RealEstateAgent is null");
            return null;
        }

        if (agent.getContact() == null) {
            LOGGER.debug("Set empty contact to RealEstateAgent");
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

        LOGGER.trace("'RealEstateAgentToRealEstateAgentCommand.convert' executed successfully.");
        return command;
    }
}
