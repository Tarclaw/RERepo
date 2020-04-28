package web.example.realestate.services.implementation;

import org.springframework.stereotype.Service;
import web.example.realestate.domain.building.FacilityObject;
import web.example.realestate.repositories.FacilityObjectRepository;
import web.example.realestate.services.FacilityObjectService;

import java.util.HashSet;
import java.util.Set;

@Service
public class FacilityObjectServiceImpl implements FacilityObjectService {

    private final FacilityObjectRepository repository;

    public FacilityObjectServiceImpl(FacilityObjectRepository repository) {
        this.repository = repository;
    }

    @Override
    public FacilityObject getById(final Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("We don't have FacilityObject with id=" + id)
                );
    }

    @Override
    public Set<FacilityObject> getFacilityObjects() {
        Set<FacilityObject> facilityObjects = new HashSet<>();
        repository.findAll().iterator().forEachRemaining(facilityObjects :: add);
        return facilityObjects;
    }
}
