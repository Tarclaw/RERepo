package web.example.realestate.services;

import web.example.realestate.commands.HouseCommand;
import web.example.realestate.domain.building.House;

import java.util.Set;

public interface HouseService {

    House getById(Long id);

    Set<House> getHouses();

    HouseCommand findCommandById(Long id);

    HouseCommand saveHouseCommand(HouseCommand command);

    void deleteById(Long id);
}
