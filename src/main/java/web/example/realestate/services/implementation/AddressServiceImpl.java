package web.example.realestate.services.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import web.example.realestate.commands.AddressCommand;
import web.example.realestate.converters.AddressCommandToAddress;
import web.example.realestate.converters.AddressToAddressCommand;
import web.example.realestate.domain.building.Address;
import web.example.realestate.exceptions.NotFoundException;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressServiceImpl.class);

    public AddressServiceImpl(AddressRepository repository,
                              AddressToAddressCommand toAddressCommand,
                              AddressCommandToAddress toAddress) {
        this.repository = repository;
        this.toAddressCommand = toAddressCommand;
        this.toAddress = toAddress;
        LOGGER.info("New instance of AddressServiceImpl created");
    }

    @Override
    public Address getById(final Long id) {
        LOGGER.trace("Enter and execute 'AddressServiceImpl.getById(final Long id)' method");
        return repository.findById(id)
                .orElseThrow(
                        () -> {
                            LOGGER.warn("We don't have address with id= " + id);
                            return new NotFoundException("Please choose different Address");
                        }
                );
    }

    @Override
    public List<Address> getAddresses() {
        LOGGER.trace("Enter in 'AddressServiceImpl.getAddresses()' method");

        List<Address> addresses = new ArrayList<>();
        repository.findAll().iterator().forEachRemaining(addresses :: add);
        LOGGER.debug("Find all Addresses from DB and add them to ArrayList");

        LOGGER.trace("'AddressServiceImpl.getAddresses()' executed successfully.");
        return addresses;
    }

    @Override
    @Transactional
    public AddressCommand findCommandById(final Long id) {
        LOGGER.trace("Enter and execute 'AddressServiceImpl.findCommandById(final Long id)' method");
        return toAddressCommand.convert(getById(id));
    }

    @Override
    @Transactional
    public AddressCommand saveAddressCommand(final AddressCommand command) {
        LOGGER.trace("Enter in 'AddressServiceImpl.saveAddressCommand(final AddressCommand command)' method");

        Address detachedAddress = toAddress.convert(command);
        Address savedAddress = repository.save(detachedAddress);

        LOGGER.debug("We saved address with id=" + savedAddress.getId());
        LOGGER.trace("'AddressServiceImpl.saveAddressCommand(final AddressCommand command)' executed successfully.");
        return toAddressCommand.convert(savedAddress);
    }

    @Override
    public void deleteById(final Long id) {
        LOGGER.trace("Enter in 'AddressServiceImpl.deleteById(final Long id)' method");

        repository.deleteById(id);
        LOGGER.debug("Delete Address with id= " + id);

        LOGGER.trace("'AddressServiceImpl.deleteById(final Long id)' executed successfully.");
    }
}
