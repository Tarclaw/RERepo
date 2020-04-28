package web.example.realestate.services;

import web.example.realestate.domain.building.House;

import java.util.Set;

public interface HouseService {
    House getById(Long id);
    Set<House> getHouses();
}
