package web.example.realestate.services.implementation;

import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.stereotype.Service;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.converters.GarageCommandToGarage;
import web.example.realestate.converters.GarageToGarageCommand;
import web.example.realestate.domain.building.Facility;
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
        return repository.findGaragesByIdWithClients(id)
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
    public FacilityCommand findCommandById(Long id) {
        return toGarageCommand.convert(getById(id));
    }

    @Override
    @Transactional
    public FacilityCommand saveGarageCommand(final FacilityCommand command) {
        return command.getId() == null ? saveDetached(command) : saveAttached(command);
    }

    private FacilityCommand saveDetached(final FacilityCommand command) {
        Garage detachedGarage = toGarage.convert(command);
        Garage savedGarage = repository.save(detachedGarage);
        System.out.println("Save Garage with id=" + savedGarage.getId());
        return toGarageCommand.convert(savedGarage);
    }

    private FacilityCommand saveAttached(final FacilityCommand command) {
        Garage attachedGarage = getById(command.getId());
        Garage updatedGarage = toGarage.convertWhenAttached(attachedGarage, command);
        System.out.println("Update Garage with id=" + updatedGarage.getId());
        return toGarageCommand.convert(updatedGarage);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
