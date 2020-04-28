package web.example.realestate.services.implementation;

import org.springframework.stereotype.Service;
import web.example.realestate.domain.building.Garage;
import web.example.realestate.repositories.GarageRepository;
import web.example.realestate.services.GarageService;

import java.util.HashSet;
import java.util.Set;

@Service
public class GarageServiceImpl implements GarageService {

    private final GarageRepository repository;

    public GarageServiceImpl(GarageRepository repository) {
        this.repository = repository;
    }

    @Override
    public Garage getById(final Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("We don't have garage with id=" + id)
                );
    }

    @Override
    public Set<Garage> getGarages() {
        Set<Garage> garages = new HashSet<>();
        repository.findAll().iterator().forEachRemaining(garages :: add);
        return garages;
    }
}
