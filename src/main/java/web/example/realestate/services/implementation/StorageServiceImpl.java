package web.example.realestate.services.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.converters.StorageCommandToStorage;
import web.example.realestate.converters.StorageToStorageCommand;
import web.example.realestate.domain.building.Storage;
import web.example.realestate.domain.people.Client;
import web.example.realestate.exceptions.ImageCorruptedException;
import web.example.realestate.exceptions.NotFoundException;
import web.example.realestate.repositories.ClientRepository;
import web.example.realestate.repositories.StorageRepository;
import web.example.realestate.services.StorageService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class StorageServiceImpl implements StorageService {

    private final StorageRepository storageRepository;
    private final ClientRepository clientRepository;
    private final StorageCommandToStorage toStorage;
    private final StorageToStorageCommand toStorageCommand;

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageServiceImpl.class);

    public StorageServiceImpl(StorageRepository storageRepository, ClientRepository clientRepository,
                              StorageCommandToStorage toStorage, StorageToStorageCommand toStorageCommand) {
        this.storageRepository = storageRepository;
        this.clientRepository = clientRepository;
        this.toStorage = toStorage;
        this.toStorageCommand = toStorageCommand;
        LOGGER.info("New instance of StorageServiceImpl created");
    }

    @Override
    public Storage getById(final Long id) {
        LOGGER.trace("Enter and execute 'StorageServiceImpl.getById(final Long id)' method");
        return storageRepository.findStoragesByIdWithClients(id)
                .orElseThrow(
                        () -> {
                            LOGGER.warn("We don't have storage with id= " + id);
                            return new NotFoundException("Please chose different Storage.");
                        }
                );
    }

    @Override
    public Set<Storage> getStorages() {
        LOGGER.trace("Enter in 'StorageServiceImpl.getStorages()' method");

        Set<Storage> storages = new HashSet<>();
        storageRepository.findAll().iterator().forEachRemaining(storages :: add);
        LOGGER.debug("Find all Storages from DB and add them to HashSet");

        LOGGER.trace("'StorageServiceImpl.getStorages()' executed successfully.");
        return storages;
    }

    @Override
    @Transactional
    public FacilityCommand findCommandById(final Long id) {
        LOGGER.trace("Enter and execute 'StorageServiceImpl.findCommandById(final Long id)' method");
        return toStorageCommand.convert(getById(id));
    }

    @Override
    @Transactional
    public FacilityCommand saveDetached(final FacilityCommand command) {
        LOGGER.trace("Enter in 'StorageServiceImpl.saveDetached(final FacilityCommand command)' method");

        Client client = clientRepository.findById(command.getClientId()).orElseThrow(() -> {
            LOGGER.warn("We don't have Client with id= " + command.getClientId());
            return new NotFoundException("We don't have this Client. Please choose another one.");
        });

        Storage detachedStorage = toStorage.convert(command);
        detachedStorage.setClient(client);
        Storage savedStorage = storageRepository.save(detachedStorage);

        LOGGER.debug("We save Storage with id= " + savedStorage.getId());
        LOGGER.trace("'StorageServiceImpl.saveDetached(final FacilityCommand command)' executed successfully.");
        return toStorageCommand.convert(savedStorage);
    }

    @Override
    @Transactional
    public FacilityCommand saveAttached(final FacilityCommand command) {
        LOGGER.trace("Enter in 'StorageServiceImpl.saveAttached(final FacilityCommand command)' method");

        Client client = clientRepository.findById(command.getClientId()).orElseThrow(() -> {
            LOGGER.warn("We don't have Client with id= " + command.getClientId());
            return new NotFoundException("We don't have this Client. Please choose another one.");
        });

        Storage attachedStorage = getById(command.getId());
        Storage updatedStorage = toStorage.convertWhenAttached(attachedStorage, command);
        updatedStorage.setClient(client);

        LOGGER.debug("We update Storage with id= " + updatedStorage.getId());
        LOGGER.trace("'StorageServiceImpl.saveAttached(final FacilityCommand command)' executed successfully.");
        return toStorageCommand.convert(updatedStorage);
    }

    @Override
    public void deleteById(final Long id) {
        LOGGER.trace("Enter and execute 'StorageServiceImpl.deleteById(final Long id)' method");

        storageRepository.deleteById(id);
        LOGGER.debug("Delete Storage with id= " + id);

        LOGGER.trace("'StorageServiceImpl.deleteById(final Long id)' executed successfully.");
    }

    @Override
    @Transactional
    public void saveImage(Long id, MultipartFile multipartFile) {
        LOGGER.trace("Enter in 'StorageServiceImpl.saveImage(Long id, MultipartFile file)' method");

        try {
            Storage storage = storageRepository.findById(id).orElseThrow(() -> {
                LOGGER.warn("We don't have Storage with id= " + id);
                return new NotFoundException("We don't have this Storage. Please choose another one.");
            });
            storage.setImage(multipartFile.getBytes());
            storageRepository.save(storage);
            LOGGER.debug("We set Image and save Storage with id= " + id);
        } catch (IOException e) {
            LOGGER.error("Attention can't execute multipartFile.getBytes() method", e);
            throw new ImageCorruptedException(e.getMessage());
        }

        LOGGER.trace("'StorageServiceImpl.saveImage(Long id, MultipartFile file)' executed successfully.");
    }
}
