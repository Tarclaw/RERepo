package web.example.realestate.services.implementation;

import org.springframework.stereotype.Service;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.converters.FacilityCommandToFacility;
import web.example.realestate.converters.FacilityToFacilityCommand;
import web.example.realestate.domain.building.Facility;
import web.example.realestate.repositories.FacilityRepository;
import web.example.realestate.services.FacilityService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class FacilityServiceImpl implements FacilityService {

    private final FacilityRepository repository;
    private final FacilityCommandToFacility toFacility;
    private final FacilityToFacilityCommand toFacilityCommand;

    public FacilityServiceImpl(FacilityRepository repository,
                               FacilityCommandToFacility toFacility,
                               FacilityToFacilityCommand toFacilityCommand) {
        this.repository = repository;
        this.toFacility = toFacility;
        this.toFacilityCommand = toFacilityCommand;
    }

    @Override
    public Facility getById(Long id) {
        return repository.findFacilityByIdWithClients(id)
                .orElseThrow(
                        () -> new RuntimeException("There is no entity with id:" + id)
                );
    }

    @Override
    public List<Facility> getFacilities() {
        List<Facility> facilities = new ArrayList<>();
        repository.findAll().iterator().forEachRemaining(facilities :: add);
        return facilities;
    }

    @Override
    @Transactional
    public FacilityCommand findCommandById(Long id) {
        return toFacilityCommand.convert(getById(id));
    }

    @Override
    @Transactional
    public FacilityCommand saveFacilityCommand(FacilityCommand command) {
        Facility detachedFacility = toFacility.convert(command);
        Facility savedFacility = repository.save(detachedFacility);
        System.out.println("We save facility with id=" + savedFacility.getId());
        return toFacilityCommand.convert(savedFacility);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
