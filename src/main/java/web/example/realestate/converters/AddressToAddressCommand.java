package web.example.realestate.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.AddressCommand;
import web.example.realestate.domain.building.Address;

@Component
public class AddressToAddressCommand implements Converter<Address, AddressCommand> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressToAddressCommand.class);

    @Override
    public AddressCommand convert(final Address source) {
        LOGGER.trace("Enter in 'AddressToAddressCommand.convert' method");

        if (source == null) {
            LOGGER.debug("Address is null");
            return null;
        }

        final AddressCommand command = new AddressCommand();
        command.setId(source.getId());
        command.setPostcode(source.getPostcode());
        command.setFacilityNumber(source.getFacilityNumber());
        command.setCity(source.getCity());
        command.setDistrict(source.getDistrict());
        command.setStreet(source.getStreet());

        LOGGER.trace("'AddressToAddressCommand.convert' executed successfully.");
        return command;
    }
}
