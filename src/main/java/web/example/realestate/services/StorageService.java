package web.example.realestate.services;

import web.example.realestate.commands.StorageCommand;
import web.example.realestate.domain.building.Storage;

import java.util.Set;

public interface StorageService {

    Storage getById(Long id);

    Set<Storage> getStorages();

    StorageCommand findCommandById(Long id);

    StorageCommand saveStorageCommand(StorageCommand command);

    void deleteById(Long id);

}
