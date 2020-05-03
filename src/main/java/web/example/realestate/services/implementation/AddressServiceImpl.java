package web.example.realestate.services.implementation;

import org.springframework.stereotype.Service;
import web.example.realestate.commands.AddressCommand;
import web.example.realestate.converters.AddressCommandToAddress;
import web.example.realestate.converters.AddressToAddressCommand;
import web.example.realestate.domain.building.Address;
import web.example.realestate.repositories.AddressRepository;
import web.example.realestate.services.AddressService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository repository;
    private final AddressToAddressCommand toAddressCommand;
    private final AddressCommandToAddress toAddress;

    public AddressServiceImpl(AddressRepository repository,
                              AddressToAddressCommand toAddressCommand,
                              AddressCommandToAddress toAddress) {
        this.repository = repository;
        this.toAddressCommand = toAddressCommand;
        this.toAddress = toAddress;
    }

    @Override
    public Address getById(final Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("We don't have address with id="+id)
                );
    }

    @Override
    public List<Address> getAddresses() {
        List<Address> addresses = new ArrayList<>();
        repository.findAll().iterator().forEachRemaining(addresses :: add);
        return addresses;
    }

    @Override
    @Transactional
    public AddressCommand findCommandById(Long id) {
        return toAddressCommand.convert(getById(id));
    }

    @Override
    @Transactional
    public AddressCommand saveAddressCommand(AddressCommand command) {
        Address detachedAddress = toAddress.convert(command);
        Address savedAddress = repository.save(detachedAddress);
        System.out.println("We saved address with id=" + savedAddress.getId()); //todo logging
        return toAddressCommand.convert(savedAddress);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
