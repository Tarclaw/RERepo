package web.example.realestate.services.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.converters.ApartmentCommandToApartment;
import web.example.realestate.converters.ApartmentToApartmentCommand;
import web.example.realestate.domain.building.Apartment;
import web.example.realestate.domain.people.Client;
import web.example.realestate.exceptions.ImageCorruptedException;
import web.example.realestate.exceptions.NotFoundException;
import web.example.realestate.repositories.ApartmentRepository;
import web.example.realestate.repositories.ClientRepository;
import web.example.realestate.services.ApartmentService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    private final ApartmentRepository apartmentRepository;
    private final ClientRepository clientRepository;
    private final ApartmentToApartmentCommand toApartmentCommand;
    private final ApartmentCommandToApartment toApartment;

    private static final Logger LOGGER = LoggerFactory.getLogger(ApartmentServiceImpl.class);

    public ApartmentServiceImpl(ApartmentRepository apartmentRepository,
                                ClientRepository clientRepository,
                                ApartmentToApartmentCommand toApartmentCommand,
                                ApartmentCommandToApartment toApartment) {
        this.apartmentRepository = apartmentRepository;
        this.clientRepository = clientRepository;
        this.toApartmentCommand = toApartmentCommand;
        this.toApartment = toApartment;
        LOGGER.info("New instance of ApartmentServiceImpl created");
    }

    @Override
    public Apartment getById(final Long id) {
        LOGGER.trace("Enter and execute 'ApartmentServiceImpl.getById(final Long id)' method");
        return apartmentRepository.findApartmentsByIdWithClients(id)
                .orElseThrow(
                        () -> {
                            LOGGER.warn("We don't have apartment with id= " + id);
                            return new NotFoundException("Please chose different Apartment");
                        }
                );
    }

    @Override
    public Set<Apartment> getApartments() {
        LOGGER.trace("Enter in 'ApartmentServiceImpl.getApartments()' method");

        Set<Apartment> apartments = new HashSet<>();
        apartmentRepository.findAll().iterator().forEachRemaining(apartments :: add);
        LOGGER.debug("Find all Apartments from DB and add them to HashSet");

        LOGGER.trace("'ApartmentServiceImpl.getApartments()' executed successfully.");
        return apartments;
    }

    @Override
    @Transactional
    public FacilityCommand findCommandById(final Long id) {
        LOGGER.trace("Enter and execute 'ApartmentServiceImpl.findCommandById(final Long id)' method");
        return toApartmentCommand.convert(getById(id));
    }

    @Override
    @Transactional
    public FacilityCommand saveDetached(final FacilityCommand command) {
        LOGGER.trace("Enter in 'ApartmentServiceImpl.saveDetached(final FacilityCommand command)' method");

        Client client = clientRepository.findById(command.getClientId()).orElseThrow(() -> {
            LOGGER.warn("We don't have Client with id= " + command.getClientId());
            return new NotFoundException("We don't have this Client. Please choose another one.");
        });

        Apartment detachedApartment = toApartment.convert(command);
        detachedApartment.setClient(client);
        Apartment savedApartment = apartmentRepository.save(detachedApartment);

        LOGGER.debug("We save Apartment with id= " + savedApartment.getId());
        LOGGER.trace("'ApartmentServiceImpl.saveDetached(final FacilityCommand command)' executed successfully.");
        return toApartmentCommand.convert(savedApartment);
    }

    @Override
    @Transactional
    public FacilityCommand saveAttached(final FacilityCommand command) {
        LOGGER.trace("Enter in 'ApartmentServiceImpl.saveAttached(final FacilityCommand command)' method");

        Client client = clientRepository.findById(command.getClientId()).orElseThrow(() -> {
            LOGGER.warn("We don't have Client with id= " + command.getClientId());
            return new NotFoundException("We don't have this Client. Please choose another one.");
        });

        Apartment attachedApartment = getById(command.getId());
        Apartment updatedApartment = toApartment.convertWhenAttached(attachedApartment, command);
        updatedApartment.setClient(client);

        LOGGER.debug("We update Apartment with id= " + updatedApartment.getId());
        LOGGER.trace("'ApartmentServiceImpl.saveAttached(final FacilityCommand command)' executed successfully.");
        return toApartmentCommand.convert(updatedApartment);
    }

    @Override
    public void deleteById(final Long id) {
        LOGGER.trace("Enter and execute 'ApartmentServiceImpl.deleteById(final Long id)' method");

        apartmentRepository.deleteById(id);
        LOGGER.debug("Delete Apartment with id= " + id);

        LOGGER.trace("'ApartmentServiceImpl.deleteById(final Long id)' executed successfully.");
    }

    @Override
    @Transactional
    public void saveImage(Long id, MultipartFile multipartFile) {
        LOGGER.trace("Enter in 'ApartmentServiceImpl.saveImage(Long id, MultipartFile multipartFile)' method");

        try {
            Apartment apartment = apartmentRepository.findById(id).orElseThrow(() -> {
                LOGGER.warn("We don't have Apartment with id= " + id);
                return new NotFoundException("We don't have this Apartment. Please choose another one.");
            });
            apartment.setImage(multipartFile.getBytes());
            apartmentRepository.save(apartment);
            LOGGER.debug("We set Image and save Apartment with id= " + id);
        } catch (IOException e) {
            LOGGER.error("Attention can't execute multipartFile.getBytes() method", e);
            throw new ImageCorruptedException(e.getMessage());
        }

        LOGGER.trace("'ApartmentServiceImpl.saveImage(Long id, MultipartFile multipartFile)' executed successfully.");
    }
}
