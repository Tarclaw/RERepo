package web.example.realestate.services.implementation;

import org.springframework.stereotype.Service;
import web.example.realestate.domain.building.Storage;
import web.example.realestate.repositories.StorageRepository;
import web.example.realestate.services.StorageService;

import java.util.HashSet;
import java.util.Set;

@Service
public class StorageServiceImpl implements StorageService {

    private final StorageRepository repository;

    public StorageServiceImpl(StorageRepository repository) {
        this.repository = repository;
    }

    @Override
    public Storage getById(final Long id) {
        return repository.findById(id)
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
}
