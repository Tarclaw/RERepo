package web.example.realestate.services;

import web.example.realestate.domain.building.FacilityObject;

import java.util.Set;

public interface FacilityObjectService {
    FacilityObject getById(Long id);
    Set<FacilityObject> getFacilityObjects();
}
