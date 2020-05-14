package web.example.realestate.services.implementation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.converters.GarageToGarageCommand;
import web.example.realestate.domain.building.Garage;
import web.example.realestate.repositories.GarageRepository;
import web.example.realestate.services.GarageService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GarageServiceImplIT {

    private static boolean OLD_HAS_EQUIPMENT = true;
    private static boolean NEW_HAS_EQUIPMENT = false;

    @Autowired
    private GarageService service;

    @Autowired
    private GarageRepository repository;

    @Autowired
    private GarageToGarageCommand toGarageCommand;

    @Test
    @Transactional
    public void saveGarageCommand() {
        //given
        Garage garage = repository.findGaragesByHasEquipment(OLD_HAS_EQUIPMENT).orElseThrow(() -> new RuntimeException());
        garage.setHasEquipment(NEW_HAS_EQUIPMENT);

        //when
        FacilityCommand command = service.saveGarageCommand(toGarageCommand.convert(garage));

        //then
        assertNotNull(command);
        assertNotEquals(OLD_HAS_EQUIPMENT, command.isHasEquipment());
        assertEquals(NEW_HAS_EQUIPMENT, command.isHasEquipment());
        assertEquals(garage.getId(), command.getId());
    }

}
