package web.example.realestate.services;

import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Facility;

import java.util.List;

public interface FacilityService {

    List<Facility> getFacilities();

    List<Facility> getFacilitiesByIds(Long id);

    Facility saveRawFacility(FacilityCommand command);

    void deleteById(Long id);

}
