package web.example.realestate.services;

import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Apartment;

import java.util.Set;

public interface ApartmentService {

    Apartment getById(Long id);

    Set<Apartment> getApartments();

    FacilityCommand findCommandById(Long id);

    FacilityCommand saveApartmentCommand(FacilityCommand command);

    void deleteById(Long id);
}
