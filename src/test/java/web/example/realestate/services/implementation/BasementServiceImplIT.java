package web.example.realestate.services.implementation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import web.example.realestate.commands.BasementCommand;
import web.example.realestate.converters.BasementToBasementCommand;
import web.example.realestate.domain.building.Basement;
import web.example.realestate.repositories.BasementRepository;
import web.example.realestate.services.BasementService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BasementServiceImplIT {

    private static boolean OLD_VALUE = true;
    private static boolean NEW_VALUE = false;

    @Autowired
    private BasementService service;

    @Autowired
    private BasementRepository repository;

    @Autowired
    private BasementToBasementCommand toBasementCommand;

    @Test
    public void saveBasementCommand() {
        //given
        Basement basement = repository.findBasementsByItCommercial(OLD_VALUE).orElseThrow();
        basement.setItCommercial(NEW_VALUE);

        //when
        BasementCommand savedCommand = service.saveBasementCommand(toBasementCommand.convert(basement));

        //then
        assertNotNull(savedCommand);
        assertNotEquals(OLD_VALUE, savedCommand.isItCommercial());
        assertEquals(NEW_VALUE, savedCommand.isItCommercial());
        assertEquals(basement.getId(), savedCommand.getId());
    }

}
