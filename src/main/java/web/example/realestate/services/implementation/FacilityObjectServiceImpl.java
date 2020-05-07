package web.example.realestate.services.implementation;

import org.springframework.stereotype.Service;
import web.example.realestate.commands.FacilityObjectCommand;
import web.example.realestate.converters.FacilityObjectCommandToFacilityObject;
import web.example.realestate.converters.FacilityObjectToFacilityObjectCommand;
import web.example.realestate.domain.building.FacilityObject;
import web.example.realestate.repositories.FacilityObjectRepository;
import web.example.realestate.services.FacilityObjectService;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class FacilityObjectServiceImpl implements FacilityObjectService {

    private final FacilityObjectRepository repository;
    private final FacilityObjectCommandToFacilityObject toFacilityObject;
    private final FacilityObjectToFacilityObjectCommand toFacilityObjectCommand;

    public FacilityObjectServiceImpl(FacilityObjectRepository repository,
                                     FacilityObjectCommandToFacilityObject toFacilityObject,
                                     FacilityObjectToFacilityObjectCommand toFacilityObjectCommand) {
        this.repository = repository;
        this.toFacilityObject = toFacilityObject;
        this.toFacilityObjectCommand = toFacilityObjectCommand;
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

    @Override
    @Transactional
    public FacilityObjectCommand findCommandById(Long id) {
        return toFacilityObjectCommand.convert(getById(id));
    }

    @Override
    @Transactional
    public FacilityObjectCommand saveFacilityObjectCommand(FacilityObjectCommand command) {
        FacilityObject detachedFO = toFacilityObject.convert(command);
        FacilityObject savedFO = repository.save(detachedFO);
        System.out.println("Save FacilityObject with id=" + savedFO.getId());
        return toFacilityObjectCommand.convert(savedFO);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
