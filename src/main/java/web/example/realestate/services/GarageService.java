package web.example.realestate.services;

import web.example.realestate.domain.building.Garage;

import java.util.Set;

public interface GarageService {
    Garage getById(Long id);
    Set<Garage> getGarages();
}
