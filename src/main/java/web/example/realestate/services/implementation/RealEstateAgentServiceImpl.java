package web.example.realestate.services.implementation;

import org.springframework.stereotype.Service;
import web.example.realestate.domain.people.RealEstateAgent;
import web.example.realestate.repositories.RealEstateAgentRepository;
import web.example.realestate.services.RealEstateAgentService;

import java.util.HashSet;
import java.util.Set;

@Service
public class RealEstateAgentServiceImpl implements RealEstateAgentService {

    private final RealEstateAgentRepository repository;

    public RealEstateAgentServiceImpl(RealEstateAgentRepository repository) {
        this.repository = repository;
    }

    @Override
    public RealEstateAgent getById(final Long id) {
        return repository.findById(id)
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
}
