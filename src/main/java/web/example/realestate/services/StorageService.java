package web.example.realestate.services;

import web.example.realestate.domain.building.Storage;

import java.util.Set;

public interface StorageService {
    Storage getById(Long id);
    Set<Storage> getStorages();
}
