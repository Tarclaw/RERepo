package web.example.realestate.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.AddressCommand;
import web.example.realestate.domain.building.Address;

@Component
public class AddressCommandToAddress implements Converter<AddressCommand, Address> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressCommandToAddress.class);

    @Override
    public Address convert(final AddressCommand source) {
        LOGGER.trace("Enter in 'AddressCommandToAddress.convert' method");

        if (source == null) {
            LOGGER.debug("AddressCommand is null");
            return null;
        }

        final Address address = new Address();
        address.setId(source.getId());
        address.setPostcode(source.getPostcode());
        address.setFacilityNumber(source.getFacilityNumber());
        address.setCity(source.getCity());
        address.setDistrict(source.getDistrict());
        address.setStreet(source.getStreet());

        LOGGER.trace("'AddressCommandToAddress.convert' executed successfully.");
        return address;
    }
}
