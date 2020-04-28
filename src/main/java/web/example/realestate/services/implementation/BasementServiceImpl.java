package web.example.realestate.services.implementation;

import org.springframework.stereotype.Service;
import web.example.realestate.domain.building.Basement;
import web.example.realestate.repositories.BasementRepository;
import web.example.realestate.services.BasementService;

import java.util.HashSet;
import java.util.Set;

@Service
public class BasementServiceImpl implements BasementService {

    private final BasementRepository repository;

    public BasementServiceImpl(BasementRepository repository) {
        this.repository = repository;
    }

    @Override
    public Basement getById(final Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("We don't have basement with id=" + id)
                );
    }

    @Override
    public Set<Basement> getBasements() {
        Set<Basement> basements = new HashSet<>();
        repository.findAll().iterator().forEachRemaining(basements :: add);
        return basements;
    }
}
