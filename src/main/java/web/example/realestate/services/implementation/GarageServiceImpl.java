package web.example.realestate.services.implementation;

import org.springframework.stereotype.Service;
import web.example.realestate.commands.GarageCommand;
import web.example.realestate.converters.GarageCommandToGarage;
import web.example.realestate.converters.GarageToGarageCommand;
import web.example.realestate.domain.building.Garage;
import web.example.realestate.repositories.GarageRepository;
import web.example.realestate.services.GarageService;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class GarageServiceImpl implements GarageService {

    private final GarageRepository repository;
    private final GarageCommandToGarage toGarage;
    private final GarageToGarageCommand toGarageCommand;

    public GarageServiceImpl(GarageRepository repository,
                             GarageCommandToGarage toGarage,
                             GarageToGarageCommand toGarageCommand) {
        this.repository = repository;
        this.toGarage = toGarage;
        this.toGarageCommand = toGarageCommand;
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

    @Override
    @Transactional
    public GarageCommand findCommandById(Long id) {
        return toGarageCommand.convert(getById(id));
    }

    @Override
    @Transactional
    public GarageCommand saveGarageCommand(GarageCommand command) {
        Garage detachedGarage = toGarage.convert(command);
        Garage savedGarage = repository.save(detachedGarage);
        System.out.println("Save Garage with id=" + savedGarage.getId());
        return toGarageCommand.convert(savedGarage);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
