package web.example.realestate.services.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.converters.GarageCommandToGarage;
import web.example.realestate.converters.GarageToGarageCommand;
import web.example.realestate.domain.building.Garage;
import web.example.realestate.domain.people.Client;
import web.example.realestate.exceptions.ImageCorruptedException;
import web.example.realestate.exceptions.NotFoundException;
import web.example.realestate.repositories.ClientRepository;
import web.example.realestate.repositories.GarageRepository;
import web.example.realestate.services.GarageService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class GarageServiceImpl implements GarageService {

    private final GarageRepository garageRepository;
    private final ClientRepository clientRepository;
    private final GarageCommandToGarage toGarage;
    private final GarageToGarageCommand toGarageCommand;

    private static final Logger LOGGER = LoggerFactory.getLogger(GarageServiceImpl.class);

    public GarageServiceImpl(GarageRepository garageRepository, ClientRepository clientRepository,
                             GarageCommandToGarage toGarage, GarageToGarageCommand toGarageCommand) {
        this.garageRepository = garageRepository;
        this.clientRepository = clientRepository;
        this.toGarage = toGarage;
        this.toGarageCommand = toGarageCommand;
        LOGGER.info("New instance of GarageServiceImpl created");
    }

    @Override
    public Garage getById(final Long id) {
        LOGGER.trace("Enter and execute 'GarageServiceImpl.getById(final Long id)' method");
        return garageRepository.findGaragesByIdWithClients(id)
                .orElseThrow(
                        () -> {
                            LOGGER.warn("We don't have garage with id= " + id);
                            return new NotFoundException("Please chose different Garage.");
                        }
                );
    }

    @Override
    public Set<Garage> getGarages() {
        LOGGER.trace("Enter in 'GarageServiceImpl.getGarages()' method");

        Set<Garage> garages = new HashSet<>();
        garageRepository.findAll().iterator().forEachRemaining(garages :: add);
        LOGGER.debug("Find all Garages from DB and add them to HashSet");

        LOGGER.trace("'GarageServiceImpl.getGarages()' executed successfully.");
        return garages;
    }

    @Override
    @Transactional
    public FacilityCommand findCommandById(final Long id) {
        LOGGER.trace("Enter and execute 'GarageServiceImpl.findCommandById(final Long id)' method");
        return toGarageCommand.convert(getById(id));
    }

    @Override
    @Transactional
    public FacilityCommand saveDetached(final FacilityCommand command) {
        LOGGER.trace("Enter in 'GarageServiceImpl.saveDetached(final FacilityCommand command)' method");

        Client client = clientRepository.findById(command.getClientId()).orElseThrow(() -> {
            LOGGER.warn("We don't have Client with id= " + command.getClientId());
            return new NotFoundException("We don't have this Client. Please choose another one.");
        });

        Garage detachedGarage = toGarage.convert(command);
        detachedGarage.setClient(client);
        Garage savedGarage = garageRepository.save(detachedGarage);

        LOGGER.debug("We save Garage with id= " + savedGarage.getId());
        LOGGER.trace("'GarageServiceImpl.saveDetached(final FacilityCommand command)' executed successfully.");
        return toGarageCommand.convert(savedGarage);
    }

    @Override
    @Transactional
    public FacilityCommand saveAttached(final FacilityCommand command) {
        LOGGER.trace("Enter in 'GarageServiceImpl.saveAttached(final FacilityCommand command)' method");

        Client client = clientRepository.findById(command.getClientId()).orElseThrow(() -> {
            LOGGER.warn("We don't have Client with id= " + command.getClientId());
            return new NotFoundException("We don't have this Client. Please choose another one.");
        });

        Garage attachedGarage = getById(command.getId());
        Garage updatedGarage = toGarage.convertWhenAttached(attachedGarage, command);
        updatedGarage.setClient(client);

        LOGGER.debug("We save Garage with id= " + updatedGarage.getId());
        LOGGER.trace("'GarageServiceImpl.saveAttached(final FacilityCommand command)' executed successfully.");
        return toGarageCommand.convert(updatedGarage);
    }

    @Override
    public void deleteById(final Long id) {
        LOGGER.trace("Enter and execute 'GarageServiceImpl.deleteById(final Long id)' method");

        garageRepository.deleteById(id);
        LOGGER.debug("Delete Garage with id= " + id);

        LOGGER.trace("'GarageServiceImpl.deleteById(final Long id)' executed successfully.");
    }

    @Override
    @Transactional
    public void saveImage(Long id, MultipartFile multipartFile) {
        LOGGER.trace("Enter in 'GarageServiceImpl.saveImage(Long id, MultipartFile file)' method");

        try {
            Garage garage = garageRepository.findById(id).orElseThrow(() -> {
                LOGGER.warn("We don't have Garage with id= " + id);
                return new NotFoundException("We don't have this Garage. Please choose another one.");
            });
            garage.setImage(multipartFile.getBytes());
            garageRepository.save(garage);
            LOGGER.debug("We set Image and save Garage with id= " + id);
        } catch (IOException e) {
            LOGGER.error("Attention can't execute multipartFile.getBytes() method", e);
            throw new ImageCorruptedException(e.getMessage());
        }

        LOGGER.trace("'GarageServiceImpl.saveImage(Long id, MultipartFile file)' executed successfully.");
    }
}
