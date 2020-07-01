package web.example.realestate.services.implementation;

import org.springframework.stereotype.Service;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.converters.BasementCommandToBasement;
import web.example.realestate.converters.BasementToBasementCommand;
import web.example.realestate.domain.building.Basement;
import web.example.realestate.domain.people.Client;
import web.example.realestate.repositories.BasementRepository;
import web.example.realestate.repositories.ClientRepository;
import web.example.realestate.services.BasementService;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class BasementServiceImpl implements BasementService {

    private final BasementRepository basementRepository;
    private final ClientRepository clientRepository;
    private final BasementCommandToBasement toBasement;
    private final BasementToBasementCommand toBasementCommand;

    public BasementServiceImpl(BasementRepository basementRepository, ClientRepository clientRepository,
                               BasementCommandToBasement toBasement, BasementToBasementCommand toBasementCommand) {
        this.basementRepository = basementRepository;
        this.clientRepository = clientRepository;
        this.toBasement = toBasement;
        this.toBasementCommand = toBasementCommand;
    }

    @Override
    public Basement getById(final Long id) {
        return basementRepository.findBasementByIdWithClients(id)
                .orElseThrow(
                        () -> new RuntimeException("We don't have basement with id=" + id)
                );
    }

    @Override
    public Set<Basement> getBasements() {
        Set<Basement> basements = new HashSet<>();
        basementRepository.findAll().iterator().forEachRemaining(basements :: add);
        return basements;
    }

    @Override
    @Transactional
    public FacilityCommand findCommandById(Long id) {
        return toBasementCommand.convert(getById(id));
    }

    @Override
    @Transactional
    public FacilityCommand saveDetached(final FacilityCommand command) {
        Client client = clientRepository.findById(command.getClientId()).get();
        Basement detachedBasement = toBasement.convert(command);
        detachedBasement.setClient(client);
        Basement savedBasement = basementRepository.save(detachedBasement);
        System.out.println("Saved Basement with id=" + savedBasement.getId());
        return toBasementCommand.convert(savedBasement);
    }

    @Override
    @Transactional
    public FacilityCommand saveAttached(final FacilityCommand command) {
        Client client = clientRepository.findById(command.getClientId()).get();
        Basement attachedBasement = getById(command.getId());
        Basement updatedBasement = toBasement.convertWhenAttached(attachedBasement, command);
        updatedBasement.setClient(client);
        System.out.println("Update Basement with id=" + updatedBasement.getId());
        return toBasementCommand.convert(updatedBasement);
    }

    @Override
    public void deleteById(Long id) {
        basementRepository.deleteById(id);
    }
}
