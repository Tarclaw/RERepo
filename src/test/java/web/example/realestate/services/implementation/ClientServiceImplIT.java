package web.example.realestate.services.implementation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import web.example.realestate.commands.AddressCommand;
import web.example.realestate.commands.ClientCommand;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.converters.ClientToClientCommand;
import web.example.realestate.domain.enums.Status;
import web.example.realestate.domain.people.Client;
import web.example.realestate.repositories.ClientRepository;
import web.example.realestate.services.ClientService;

import java.math.BigInteger;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientServiceImplIT {

    private static final String OLD_SURNAME = "Johnson";
    private static final String NEW_SURNAME = "Clark";
    private static final Integer POSTCODE = 24399;
    private static final Integer FACILITY_NUMBER = 55;
    private static final String CITY = "City";
    private static final String DISTRICT = "District";
    private static final String STREET = "Street";
    private static final Integer APARTMENT_NUMBER = 86;
    private static final Integer FLOOR = 9;
    private static final Integer NUMBER_OF_ROOMS = 3;
    private static final Integer TOTAL_AREA = 70;
    private static final String DESCRIPTION = "desc";
    private static final BigInteger MONTH_RENT = BigInteger.valueOf(7000L);
    private static final BigInteger PRICE = BigInteger.valueOf(70000L);
    private static final Long AGENT_ID = 1L;
    private static final String FIRST_NAME = "First";
    private static final String LAST_NAME = "Last";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";
    private static final String SKYPE = "skype";
    private static final String MOBILE_NUMBER = "123456789";
    private static final String CUSTOMER_REQUIREMENTS = "requirements";


    @Autowired
    private ClientService service;

    @Autowired
    private ClientRepository repository;

    @Autowired
    private ClientToClientCommand toClientCommand;

    @Test
    @Transactional
    public void saveAttached() {
        //given
        Client client = repository.findClientsByLastName(OLD_SURNAME).orElseThrow(() -> new RuntimeException());
        client.setLastName(NEW_SURNAME);

        //when
        ClientCommand command = service.saveAttached(toClientCommand.convert(client));

        //then
        assertNotNull(command);
        assertNotEquals(OLD_SURNAME, command.getLastName());
        assertEquals(NEW_SURNAME, command.getLastName());
        assertEquals(client.getId(), command.getId());
    }

    @Test
    @Transactional
    public void saveDetached() {
        // given
        AddressCommand address = new AddressCommand();
        address.setPostcode(POSTCODE);
        address.setFacilityNumber(FACILITY_NUMBER);
        address.setCity(CITY);
        address.setDistrict(DISTRICT);
        address.setStreet(STREET);

        FacilityCommand facilityCommand = new FacilityCommand();
        facilityCommand.setItApartment(true);
        facilityCommand.setApartmentNumber(APARTMENT_NUMBER);
        facilityCommand.setFloor(FLOOR);
        facilityCommand.setNumberOfRooms(NUMBER_OF_ROOMS);
        facilityCommand.setTotalArea(TOTAL_AREA);
        facilityCommand.setDescription(DESCRIPTION);
        facilityCommand.setMonthRent(MONTH_RENT);
        facilityCommand.setPrice(PRICE);
        facilityCommand.setStatus(Status.FOR_RENT);
        facilityCommand.setAddress(address);

        ClientCommand clientCommand = new ClientCommand();
        clientCommand.setAgentId(AGENT_ID);
        clientCommand.setFirstName(FIRST_NAME);
        clientCommand.setLastName(LAST_NAME);
        clientCommand.setLogin(LOGIN);
        clientCommand.setPassword(PASSWORD);
        clientCommand.setEmail(EMAIL);
        clientCommand.setSkype(SKYPE);
        clientCommand.setMobileNumber(MOBILE_NUMBER);
        clientCommand.setCustomerRequirements(CUSTOMER_REQUIREMENTS);

        //when
        ClientCommand savedClient = service.saveDetached(clientCommand, facilityCommand);
        Set<FacilityCommand> facilityCommands = savedClient.getFacilityCommands();

        //then
        assertNotNull(savedClient);
        assertNotNull(savedClient.getId());
        facilityCommands.forEach(command -> assertNotNull(command.getId()));
    }


}
