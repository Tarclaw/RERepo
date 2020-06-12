package web.example.realestate.services.implementation;

import org.springframework.stereotype.Service;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.converters.StorageCommandToStorage;
import web.example.realestate.converters.StorageToStorageCommand;
import web.example.realestate.domain.building.Storage;
import web.example.realestate.repositories.StorageRepository;
import web.example.realestate.services.StorageService;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class StorageServiceImpl implements StorageService {

    private final StorageRepository repository;
    private final StorageCommandToStorage toStorage;
    private final StorageToStorageCommand toStorageCommand;

    public StorageServiceImpl(StorageRepository repository,
                              StorageCommandToStorage toStorage,
                              StorageToStorageCommand toStorageCommand) {
        this.repository = repository;
        this.toStorage = toStorage;
        this.toStorageCommand = toStorageCommand;
    }

    @Override
    public Storage getById(final Long id) {
        return repository.findStoragesByIdWithClients(id)
                .orElseThrow(
                        () -> new RuntimeException("We don't have storage with id=" + id)
                );
    }

    @Override
    public Set<Storage> getStorages() {
        Set<Storage> storages = new HashSet<>();
        repository.findAll().iterator().forEachRemaining(storages :: add);
        return storages;
    }

    @Override
    @Transactional
    public FacilityCommand findCommandById(Long id) {
        return toStorageCommand.convert(getById(id));
    }

    @Override
    @Transactional
    public FacilityCommand saveStorageCommand(final FacilityCommand command) {
        return command.getId() == null ? saveDetached(command) : saveAttached(command);
    }

    private FacilityCommand saveDetached(final FacilityCommand command) {
        Storage detachedStorage = toStorage.convert(command);
        Storage savedStorage = repository.save(detachedStorage);
        System.out.println("Save Storage with id=" + savedStorage.getId());
        return toStorageCommand.convert(savedStorage);
    }

    private FacilityCommand saveAttached(final FacilityCommand command) {
        Storage attachedStorage = getById(command.getId());
        Storage updatedStorage = toStorage.convertWhenAttached(attachedStorage, command);
        System.out.println("Update Storage with id=" + updatedStorage.getId());
        return toStorageCommand.convert(updatedStorage);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
