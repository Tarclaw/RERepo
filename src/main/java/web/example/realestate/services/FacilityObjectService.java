package web.example.realestate.services;

import web.example.realestate.commands.FacilityObjectCommand;
import web.example.realestate.domain.building.FacilityObject;

import java.util.Set;

public interface FacilityObjectService {

    FacilityObject getById(Long id);

    Set<FacilityObject> getFacilityObjects();

    FacilityObjectCommand findCommandById(Long id);

    FacilityObjectCommand saveFacilityObjectCommand(FacilityObjectCommand command);

    void deleteById(Long id);

}
