package web.example.realestate.services.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.converters.HouseCommandToHouse;
import web.example.realestate.converters.HouseToHouseCommand;
import web.example.realestate.domain.building.House;
import web.example.realestate.domain.people.Client;
import web.example.realestate.exceptions.ImageCorruptedException;
import web.example.realestate.exceptions.NotFoundException;
import web.example.realestate.repositories.ClientRepository;
import web.example.realestate.repositories.HouseRepository;
import web.example.realestate.services.HouseService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;
    private final ClientRepository clientRepository;
    private final HouseCommandToHouse toHouse;
    private final HouseToHouseCommand toHouseCommand;

    private static final Logger LOGGER = LoggerFactory.getLogger(HouseServiceImpl.class);

    public HouseServiceImpl(HouseRepository houseRepository, ClientRepository clientRepository,
                            HouseCommandToHouse toHouse, HouseToHouseCommand toHouseCommand) {
        this.houseRepository = houseRepository;
        this.clientRepository = clientRepository;
        this.toHouse = toHouse;
        this.toHouseCommand = toHouseCommand;
        LOGGER.info("New instance of HouseServiceImpl created");
    }

    @Override
    public House getById(final Long id) {
        LOGGER.trace("Enter and execute 'HouseServiceImpl.getById(final Long id)' method");
        return houseRepository.findHousesByIdWithClients(id)
                .orElseThrow(
                        () -> {
                            LOGGER.warn("We don't have house with id= " + id);
                            return new NotFoundException("Please chose different House.");
                        }
                );
    }

    @Override
    public Set<House> getHouses() {
        LOGGER.trace("Enter in 'HouseServiceImpl.getHouses()' method");

        Set<House> houses = new HashSet<>();
        houseRepository.findAll().iterator().forEachRemaining(houses :: add);
        LOGGER.debug("Find all Houses from DB and add them to HashSet");

        LOGGER.trace("'HouseServiceImpl.getHouses()' executed successfully.");
        return houses;
    }

    @Override
    @Transactional
    public FacilityCommand findCommandById(final Long id) {
        LOGGER.trace("Enter and execute 'HouseServiceImpl.findCommandById(final Long id)' method");
        return toHouseCommand.convert(getById(id));
    }

    @Override
    @Transactional
    public FacilityCommand saveDetached(final FacilityCommand command) {
        LOGGER.trace("Enter in 'HouseServiceImpl.saveDetached(final FacilityCommand command)' method");

        Client client = clientRepository.findById(command.getClientId()).orElseThrow(() -> {
            LOGGER.warn("We don't have Client with id= " + command.getClientId());
            return new NotFoundException("We don't have this Client. Please choose another one.");
        });

        House detachedHouse = toHouse.convert(command);
        detachedHouse.setClient(client);
        House savedHouse = houseRepository.save(detachedHouse);

        LOGGER.debug("We save House with id= " + savedHouse.getId());
        LOGGER.trace("'HouseServiceImpl.saveDetached(final FacilityCommand command)' executed successfully.");
        return toHouseCommand.convert(savedHouse);
    }

    @Override
    @Transactional
    public FacilityCommand saveAttached(final FacilityCommand command) {
        LOGGER.trace("Enter in 'HouseServiceImpl.saveAttached(final FacilityCommand command)' method");

        Client client = clientRepository.findById(command.getClientId()).orElseThrow(() -> {
            LOGGER.warn("We don't have Client with id= " + command.getClientId());
            return new NotFoundException("We don't have this Client. Please choose another one.");
        });

        House attachedHouse = getById(command.getId());
        House updatedHouse = toHouse.convertWhenAttached(attachedHouse, command);
        updatedHouse.setClient(client);

        LOGGER.debug("We update House with id= " + updatedHouse.getId());
        LOGGER.trace("'HouseServiceImpl.saveAttached(final FacilityCommand command)' executed successfully.");
        return toHouseCommand.convert(updatedHouse);
    }

    @Override
    public void deleteById(final Long id) {
        LOGGER.trace("Enter and execute 'HouseServiceImpl.deleteById(final Long id)' method");

        houseRepository.deleteById(id);
        LOGGER.debug("Delete House with id= " + id);

        LOGGER.trace("'HouseServiceImpl.deleteById(final Long id)' executed successfully.");
    }

    @Override
    public void saveImage(Long id, MultipartFile multipartFile) {
        LOGGER.trace("Enter in 'HouseServiceImpl.saveImage(Long id, MultipartFile file)' method");

        try {
            House house = houseRepository.findById(id).orElseThrow(() -> {
                LOGGER.warn("We don't have House with id= " + id);
                return new NotFoundException("We don't have this House. Please choose another one.");
            });
            house.setImage(multipartFile.getBytes());
            houseRepository.save(house);
            LOGGER.debug("We set Image and save House with id= " + id);
        } catch (IOException e) {
            LOGGER.error("Attention can't execute multipartFile.getBytes() method", e);
            throw new ImageCorruptedException(e.getMessage());
        }

        LOGGER.trace("'HouseServiceImpl.saveImage(Long id, MultipartFile file)' executed successfully.");
    }
}
