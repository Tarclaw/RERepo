package web.example.realestate.services;

import web.example.realestate.commands.RealEstateAgentCommand;
import web.example.realestate.domain.people.RealEstateAgent;

import java.util.Set;

public interface RealEstateAgentService {

    RealEstateAgent getById(Long id);

    Set<RealEstateAgent> getRealEstateAgents();

    RealEstateAgentCommand findCommandById(Long id);

    RealEstateAgentCommand saveRealEstateAgentCommand(RealEstateAgentCommand command);

    void deleteById(Long id);
}
