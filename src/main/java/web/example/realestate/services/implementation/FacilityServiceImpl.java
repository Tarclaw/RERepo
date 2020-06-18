package web.example.realestate.services.implementation;

import org.springframework.stereotype.Service;
import web.example.realestate.domain.building.Facility;
import web.example.realestate.repositories.FacilityRepository;
import web.example.realestate.services.FacilityService;

import java.util.ArrayList;
import java.util.List;

@Service
public class FacilityServiceImpl implements FacilityService {

    private final FacilityRepository repository;

    public FacilityServiceImpl(FacilityRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Facility> getFacilities() {
        List<Facility> facilities = new ArrayList<>();
        repository.findAll().iterator().forEachRemaining(facilities :: add);
        return facilities;
    }
}
