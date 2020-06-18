package web.example.realestate.services;

import web.example.realestate.domain.building.Facility;

import java.util.List;
import java.util.Map;

public interface MappingService {

    Map<Long, String> buildMapping(List<Facility> facilities);

}
