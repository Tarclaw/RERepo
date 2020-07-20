package web.example.realestate.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.RealEstateAgentCommand;
import web.example.realestate.domain.people.Contact;
import web.example.realestate.domain.people.RealEstateAgent;

@Component
public class RealEstateAgentCommandToRealEstateAgent implements Converter<RealEstateAgentCommand, RealEstateAgent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RealEstateAgentCommandToRealEstateAgent.class);

    @Override
    public RealEstateAgent convert(RealEstateAgentCommand command) {
        LOGGER.trace("Enter in 'RealEstateAgentCommandToRealEstateAgent.convert' method");

        if (command == null) {
            LOGGER.debug("RealEstateAgentCommand is null");
            return null;
        }

        final RealEstateAgent agent = new RealEstateAgent();
        agent.setId(command.getId());
        agent.setFirstName(command.getFirstName());
        agent.setLastName(command.getLastName());
        agent.setLogin(command.getLogin());
        agent.setPassword(command.getPassword());
        agent.setContact(new Contact(command.getEmail(),
                                     command.getSkype(),
                                     command.getMobileNumber()));
        agent.setSalary(command.getSalary());
        agent.setHiredDate(command.getHiredDate());
        agent.setQuitDate(command.getQuitDate());

        LOGGER.trace("'RealEstateAgentCommandToRealEstateAgent.convert' executed successfully.");
        return agent;
    }
}
