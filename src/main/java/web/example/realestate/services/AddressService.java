package web.example.realestate.services;

import web.example.realestate.commands.AddressCommand;
import web.example.realestate.domain.building.Address;
import java.util.List;

public interface AddressService {

    Address getById(Long id);

    List<Address> getAddresses();

    AddressCommand findCommandById(Long id);

    AddressCommand saveAddressCommand(AddressCommand command);

    void deleteById(Long id);

}
