package web.example.realestate.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.RealEstateAgentCommand;
import web.example.realestate.domain.people.Contact;
import web.example.realestate.domain.people.RealEstateAgent;

@Component
public class RealEstateAgentCommandToRealEstateAgent implements Converter<RealEstateAgentCommand, RealEstateAgent> {

    @Override
    public RealEstateAgent convert(RealEstateAgentCommand command) {
        if (command == null) {
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

        return agent;
    }
}
