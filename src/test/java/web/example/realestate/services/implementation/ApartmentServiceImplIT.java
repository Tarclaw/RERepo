package web.example.realestate.services.implementation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import web.example.realestate.commands.ApartmentCommand;
import web.example.realestate.converters.ApartmentToApartmentCommand;
import web.example.realestate.domain.building.Apartment;
import web.example.realestate.repositories.ApartmentRepository;
import web.example.realestate.services.ApartmentService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApartmentServiceImplIT {

    private static final Integer OLD_TOTAL_AREA = 150;
    private static final Integer NEW_TOTAL_AREA = 165;

    @Autowired
    private ApartmentService service;

    @Autowired
    private ApartmentRepository repository;

    @Autowired
    private ApartmentToApartmentCommand toApartmentCommand;

    @Test
    @Transactional
    public void saveApartmentCommand() {
        //given
        Apartment apartment = repository.findApartmentsByTotalArea(OLD_TOTAL_AREA).orElseThrow();
        apartment.setTotalArea(NEW_TOTAL_AREA);

        //when
        ApartmentCommand command = service.saveApartmentCommand(toApartmentCommand.convert(apartment));

        //then
        assertNotNull(command);
        assertNotEquals(OLD_TOTAL_AREA, command.getTotalArea());
        assertEquals(NEW_TOTAL_AREA, command.getTotalArea());
        assertEquals(apartment.getId(), command.getId());
    }
}
