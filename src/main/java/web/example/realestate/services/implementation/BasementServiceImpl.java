package web.example.realestate.services.implementation;

import org.springframework.stereotype.Service;
import web.example.realestate.commands.BasementCommand;
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

    @Override
    @Transactional
    public BasementCommand findCommandById(Long id) {
        return toBasementCommand.convert(getById(id));
    }

    @Override
    @Transactional
    public BasementCommand saveBasementCommand(BasementCommand command) {
        Basement detachetBasement = toBasement.convert(command);
        Basement savedBasement = repository.save(detachetBasement);
        System.out.println("Saved Basement with id=" + savedBasement.getId());
        return toBasementCommand.convert(savedBasement);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
