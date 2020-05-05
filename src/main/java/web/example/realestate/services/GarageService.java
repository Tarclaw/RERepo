package web.example.realestate.services;

import web.example.realestate.commands.GarageCommand;
import web.example.realestate.domain.building.Garage;

import java.util.Set;

public interface GarageService {

    Garage getById(Long id);

    Set<Garage> getGarages();

    GarageCommand findCommandById(Long id);

    GarageCommand saveGarageCommand(GarageCommand command);

    void deleteById(Long id);
}
