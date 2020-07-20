package web.example.realestate.services.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.converters.BasementCommandToBasement;
import web.example.realestate.converters.BasementToBasementCommand;
import web.example.realestate.domain.building.Basement;
import web.example.realestate.domain.people.Client;
import web.example.realestate.exceptions.ImageCorruptedException;
import web.example.realestate.exceptions.NotFoundException;
import web.example.realestate.repositories.BasementRepository;
import web.example.realestate.repositories.ClientRepository;
import web.example.realestate.services.BasementService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class BasementServiceImpl implements BasementService {

    private final BasementRepository basementRepository;
    private final ClientRepository clientRepository;
    private final BasementCommandToBasement toBasement;
    private final BasementToBasementCommand toBasementCommand;

    private static final Logger LOGGER = LoggerFactory.getLogger(BasementServiceImpl.class);

    public BasementServiceImpl(BasementRepository basementRepository, ClientRepository clientRepository,
                               BasementCommandToBasement toBasement, BasementToBasementCommand toBasementCommand) {
        this.basementRepository = basementRepository;
        this.clientRepository = clientRepository;
        this.toBasement = toBasement;
        this.toBasementCommand = toBasementCommand;
        LOGGER.info("New instance of BasementServiceImpl created");
    }

    @Override
    public Basement getById(final Long id) {
        LOGGER.trace("Enter and execute 'BasementServiceImpl.getById(final Long id)' method");
        return basementRepository.findBasementByIdWithClients(id)
                .orElseThrow(
                        () -> {
                            LOGGER.warn("We don't have basement with id= " + id);
                            return new NotFoundException("Please chose different Basement.");
                        }
                );
    }

    @Override
    public Set<Basement> getBasements() {
        LOGGER.trace("Enter in 'BasementServiceImpl.getBasements()' method");

        Set<Basement> basements = new HashSet<>();
        basementRepository.findAll().iterator().forEachRemaining(basements :: add);
        LOGGER.debug("Find all Basements from DB and add them to HashSet");

        LOGGER.trace("'BasementServiceImpl.getBasements()' executed successfully.");
        return basements;
    }

    @Override
    @Transactional
    public FacilityCommand findCommandById(final Long id) {
        LOGGER.trace("Enter and execute 'BasementServiceImpl.findCommandById(final Long id)' method");
        return toBasementCommand.convert(getById(id));
    }

    @Override
    @Transactional
    public FacilityCommand saveDetached(final FacilityCommand command) {
        LOGGER.trace("Enter in 'BasementServiceImpl.saveDetached(final FacilityCommand command)' method");

        Client client = clientRepository.findById(command.getClientId()).orElseThrow(() -> {
            LOGGER.warn("We don't have Client with id= " + command.getClientId());
            return new NotFoundException("We don't have this Client. Please choose another one.");
        });

        Basement detachedBasement = toBasement.convert(command);
        detachedBasement.setClient(client);
        Basement savedBasement = basementRepository.save(detachedBasement);

        LOGGER.debug("We save Basement with id= " + savedBasement.getId());
        LOGGER.trace("'BasementServiceImpl.saveDetached(final FacilityCommand command)' executed successfully.");
        return toBasementCommand.convert(savedBasement);
    }

    @Override
    @Transactional
    public FacilityCommand saveAttached(final FacilityCommand command) {
        LOGGER.trace("Enter in 'BasementServiceImpl.saveAttached(final FacilityCommand command)' method");

        Client client = clientRepository.findById(command.getClientId()).orElseThrow(() -> {
            LOGGER.warn("We don't have Client with id= " + command.getClientId());
            return new NotFoundException("We don't have this Client. Please choose another one.");
        });

        Basement attachedBasement = getById(command.getId());
        Basement updatedBasement = toBasement.convertWhenAttached(attachedBasement, command);
        updatedBasement.setClient(client);

        LOGGER.debug("We update Basement with id= " + updatedBasement.getId());
        LOGGER.trace("'BasementServiceImpl.saveAttached(final FacilityCommand command)' executed successfully.");
        return toBasementCommand.convert(updatedBasement);
    }

    @Override
    public void deleteById(final Long id) {
        LOGGER.trace("Enter and execute 'BasementServiceImpl.deleteById(final Long id)' method");

        basementRepository.deleteById(id);
        LOGGER.debug("Delete Basement with id= " + id);

        LOGGER.trace("'BasementServiceImpl.deleteById(final Long id)' executed successfully.");
    }

    @Override
    @Transactional
    public void saveImage(Long id, MultipartFile file) {
        LOGGER.trace("Enter in 'BasementServiceImpl.saveImage(Long id, MultipartFile file)' method");

        try {
            Basement basement = basementRepository.findById(id).orElseThrow(() -> {
                LOGGER.warn("We don't have Basement with id= " + id);
                return new NotFoundException("We don't have this Basement. Please choose another one.");
            });
            basement.setImage(file.getBytes());
            basementRepository.save(basement);
            LOGGER.debug("We set Image and save Basement with id= " + id);
        } catch (IOException e) {
            LOGGER.error("Attention can't execute multipartFile.getBytes() method", e);
            throw new ImageCorruptedException(e.getMessage());
        }

        LOGGER.trace("'BasementServiceImpl.saveImage(Long id, MultipartFile file)' executed successfully.");
    }
}
