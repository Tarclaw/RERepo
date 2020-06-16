package web.example.realestate.services.implementation;

import org.springframework.stereotype.Service;
import web.example.realestate.commands.RealEstateAgentCommand;
import web.example.realestate.converters.RealEstateAgentCommandToRealEstateAgent;
import web.example.realestate.converters.RealEstateAgentToRealEstateAgentCommand;
import web.example.realestate.domain.people.RealEstateAgent;
import web.example.realestate.repositories.RealEstateAgentRepository;
import web.example.realestate.services.RealEstateAgentService;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class RealEstateAgentServiceImpl implements RealEstateAgentService {

    private final RealEstateAgentRepository repository;
    private final RealEstateAgentCommandToRealEstateAgent toAgent;
    private final RealEstateAgentToRealEstateAgentCommand toAgentCommand;

    public RealEstateAgentServiceImpl(RealEstateAgentRepository repository,
                                      RealEstateAgentCommandToRealEstateAgent toAgent,
                                      RealEstateAgentToRealEstateAgentCommand toAgentCommand) {
        this.repository = repository;
        this.toAgent = toAgent;
        this.toAgentCommand = toAgentCommand;
    }

    @Override
    public RealEstateAgent getById(final Long id) {
        return repository.findRealEstateAgentsByIdWithEntities(id)
                         .orElseThrow(
                                      () -> new RuntimeException("We don't have agent with id=" + id)
                         );
    }

    @Override
    public Set<RealEstateAgent> getRealEstateAgents() {
        Set<RealEstateAgent> agents = new HashSet<>();
        repository.findAll().iterator().forEachRemaining(agents :: add);
        return agents;
    }

    @Override
    @Transactional
    public RealEstateAgentCommand findCommandById(Long id) {
        return toAgentCommand.convert(getById(id));
    }

    @Override
    @Transactional
    public RealEstateAgentCommand saveRealEstateAgentCommand(RealEstateAgentCommand command) {
        RealEstateAgent detachedAgent = toAgent.convert(command);
        RealEstateAgent savedAgent = repository.save(detachedAgent);
        System.out.println("Save agent with id=" + savedAgent.getId());
        return toAgentCommand.convert(savedAgent);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
