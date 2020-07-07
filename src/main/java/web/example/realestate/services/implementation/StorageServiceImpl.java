package web.example.realestate.services.implementation;

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

    public StorageServiceImpl(StorageRepository storageRepository, ClientRepository clientRepository,
                              StorageCommandToStorage toStorage, StorageToStorageCommand toStorageCommand) {
        this.storageRepository = storageRepository;
        this.clientRepository = clientRepository;
        this.toStorage = toStorage;
        this.toStorageCommand = toStorageCommand;
    }

    @Override
    public Storage getById(final Long id) {
        return storageRepository.findStoragesByIdWithClients(id)
                .orElseThrow(
                        () -> new NotFoundException("We don't have storage with id=" + id)
                );
    }

    @Override
    public Set<Storage> getStorages() {
        Set<Storage> storages = new HashSet<>();
        storageRepository.findAll().iterator().forEachRemaining(storages :: add);
        return storages;
    }

    @Override
    @Transactional
    public FacilityCommand findCommandById(Long id) {
        return toStorageCommand.convert(getById(id));
    }

    @Override
    @Transactional
    public FacilityCommand saveDetached(final FacilityCommand command) {
        Client client = clientRepository.findById(command.getClientId()).get();
        Storage detachedStorage = toStorage.convert(command);
        detachedStorage.setClient(client);
        Storage savedStorage = storageRepository.save(detachedStorage);
        System.out.println("Save Storage with id=" + savedStorage.getId());
        return toStorageCommand.convert(savedStorage);
    }

    @Override
    @Transactional
    public FacilityCommand saveAttached(final FacilityCommand command) {
        Client client = clientRepository.findById(command.getClientId()).get();
        Storage attachedStorage = getById(command.getId());
        Storage updatedStorage = toStorage.convertWhenAttached(attachedStorage, command);
        updatedStorage.setClient(client);
        System.out.println("Update Storage with id=" + updatedStorage.getId());
        return toStorageCommand.convert(updatedStorage);
    }

    @Override
    public void deleteById(Long id) {
        storageRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void saveImage(Long id, MultipartFile multipartFile) {
        try {
            Storage storage = storageRepository.findById(id).get();
            storage.setImage(multipartFile.getBytes());
            storageRepository.save(storage);
        } catch (IOException e) {
            throw new ImageCorruptedException(e.getMessage());
        }
    }
}
