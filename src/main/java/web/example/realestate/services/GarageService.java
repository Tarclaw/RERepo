package web.example.realestate.services;

import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Garage;

import java.util.Set;

public interface GarageService {

    Garage getById(Long id);

    Set<Garage> getGarages();

    FacilityCommand findCommandById(Long id);

    FacilityCommand saveGarageCommand(FacilityCommand command);

    void deleteById(Long id);
}
