package web.example.realestate.services;

import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.House;

import java.util.Set;

public interface HouseService {

    House getById(Long id);

    Set<House> getHouses();

    FacilityCommand findCommandById(Long id);

    FacilityCommand saveHouseCommand(FacilityCommand command);

    void deleteById(Long id);
}
