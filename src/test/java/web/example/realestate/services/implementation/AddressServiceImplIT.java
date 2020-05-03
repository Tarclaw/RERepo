package web.example.realestate.services.implementation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import web.example.realestate.commands.AddressCommand;
import web.example.realestate.converters.AddressToAddressCommand;
import web.example.realestate.domain.building.Address;
import web.example.realestate.repositories.AddressRepository;
import web.example.realestate.services.AddressService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressServiceImplIT {

    private static final String OLD_STREET = "Manhattan";
    private static final String NEW_STREET = "Silicon Valley";

    @Autowired
    private AddressService service;

    @Autowired
    private AddressRepository repository;

    @Autowired
    private AddressToAddressCommand toAddressCommand;

    @Test
    @Transactional
    public void saveAddressCommand() {
        //given
        Address address = repository.findAddressesByStreet(OLD_STREET)
                .orElseThrow(() -> new RuntimeException("Wrong Street"));
        AddressCommand sourceCmd = toAddressCommand.convert(address);

        //when
        sourceCmd.setStreet(NEW_STREET);
        AddressCommand savedAddressCmd = service.saveAddressCommand(sourceCmd);

        //then
        assertNotEquals(OLD_STREET, savedAddressCmd.getStreet());
        assertEquals(NEW_STREET, savedAddressCmd.getStreet());
        assertEquals(address.getId(), savedAddressCmd.getId());
    }
}