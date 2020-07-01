package web.example.realestate.services;

import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Storage;

import java.util.Set;

public interface StorageService {

    Storage getById(Long id);

    Set<Storage> getStorages();

    FacilityCommand findCommandById(Long id);

    FacilityCommand saveDetached(FacilityCommand command);

    FacilityCommand saveAttached(FacilityCommand command);

    void deleteById(Long id);

}
