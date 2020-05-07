package web.example.realestate.services.implementation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import web.example.realestate.commands.HouseCommand;
import web.example.realestate.converters.HouseToHouseCommand;
import web.example.realestate.domain.building.House;
import web.example.realestate.repositories.HouseRepository;
import web.example.realestate.services.HouseService;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HouseServiceImplIT {

    private static final boolean OLD_HAS_GARDEN = true;
    private static final boolean NEW_HAS_GARDEN = false;

    @Autowired
    private HouseService service;

    @Autowired
    private HouseRepository repository;

    @Autowired
    private HouseToHouseCommand toHouseCommand;

    @Test
    @Transactional
    public void saveHouseCommand() {
        //given
        House house = repository.findHousesByHasGarden(OLD_HAS_GARDEN).orElseThrow();
        house.setHasGarden(NEW_HAS_GARDEN);

        //when
        HouseCommand command = service.saveHouseCommand(toHouseCommand.convert(house));

        //then
        assertNotNull(command);
        assertNotEquals(OLD_HAS_GARDEN, command.isHasGarden());
        assertEquals(NEW_HAS_GARDEN, command.isHasGarden());
        assertEquals(house.getId(), command.getId());
    }

}
