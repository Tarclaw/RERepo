package web.example.realestate.services.implementation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.converters.FacilityToFacilityCommand;
import web.example.realestate.domain.building.Facility;
import web.example.realestate.repositories.FacilityRepository;
import web.example.realestate.services.FacilityService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FacilityServiceImplIT {

    private static final String OLD_DESCRIPTION = "generic description";
    private static final String NEW_DESCRIPTION = "decent apartment";

    @Autowired
    private FacilityService service;

    @Autowired
    private FacilityRepository repository;

    @Autowired
    private FacilityToFacilityCommand toFacilityCommand;

    @Test
    @Transactional
    public void saveFacilityCommand() {
        //given
        Facility facility = repository.findFacilitiesByDescription(OLD_DESCRIPTION)
                .orElseThrow(() -> new RuntimeException("Wrong description"));
        FacilityCommand sourceCommand = toFacilityCommand.convert(facility);

        //when
        sourceCommand.setDescription(NEW_DESCRIPTION);
        FacilityCommand savedCommand = service.saveFacilityCommand(sourceCommand);

        //then
        assertNotEquals(OLD_DESCRIPTION, savedCommand.getDescription());
        assertEquals(NEW_DESCRIPTION, savedCommand.getDescription());
        //assertEquals(facility.getId(), savedCommand.getId()); //todo wtf id
    }
}