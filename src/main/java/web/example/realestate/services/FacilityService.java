package web.example.realestate.services;

import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Facility;

import java.util.List;

public interface FacilityService {

    Facility getById(Long id);

    List<Facility> getFacilities();

    FacilityCommand findCommandById(Long id);

    FacilityCommand saveFacilityCommand(FacilityCommand command);

    void deleteById(Long id);
}
