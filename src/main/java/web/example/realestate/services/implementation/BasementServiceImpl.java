package web.example.realestate.services.implementation;

import org.springframework.stereotype.Service;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.converters.BasementCommandToBasement;
import web.example.realestate.converters.BasementToBasementCommand;
import web.example.realestate.domain.building.Basement;
import web.example.realestate.repositories.BasementRepository;
import web.example.realestate.services.BasementService;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class BasementServiceImpl implements BasementService {

    private final BasementRepository repository;
    private final BasementCommandToBasement toBasement;
    private final BasementToBasementCommand toBasementCommand;

    public BasementServiceImpl(BasementRepository repository,
                               BasementCommandToBasement toBasement,
                               BasementToBasementCommand toBasementCommand) {
        this.repository = repository;
        this.toBasement = toBasement;
        this.toBasementCommand = toBasementCommand;
    }

    @Override
    public Basement getById(final Long id) {
        return repository.findBasementByIdWithClients(id)
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

    @Override
    @Transactional
    public FacilityCommand findCommandById(Long id) {
        return toBasementCommand.convert(getById(id));
    }

    @Override
    @Transactional
    public FacilityCommand saveBasementCommand(final FacilityCommand command) {
        return command.getId() == null ? saveDetached(command) : saveAttached(command);
    }

    private FacilityCommand saveDetached(final FacilityCommand command) {
        Basement detachedBasement = toBasement.convert(command);
        Basement savedBasement = repository.save(detachedBasement);
        System.out.println("Saved Basement with id=" + savedBasement.getId());
        return toBasementCommand.convert(savedBasement);
    }

    private FacilityCommand saveAttached(final FacilityCommand command) {
        Basement attachedBasement = getById(command.getId());
        Basement updatedBasement = toBasement.convertWhenAttached(attachedBasement, command);
        System.out.println("Update Basement with id=" + updatedBasement.getId());
        return toBasementCommand.convert(updatedBasement);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
