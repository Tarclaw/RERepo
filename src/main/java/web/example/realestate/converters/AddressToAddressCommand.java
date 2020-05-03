package web.example.realestate.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.AddressCommand;
import web.example.realestate.domain.building.Address;

@Component
public class AddressToAddressCommand implements Converter<Address, AddressCommand> {

    @Override
    public AddressCommand convert(Address source) {
        if (source == null) {
            return null;
        }

        final AddressCommand command = new AddressCommand();
        command.setId(source.getId());
        command.setPostcode(source.getPostcode());
        command.setFacilityNumber(source.getFacilityNumber());
        command.setCity(source.getCity());
        command.setDistrict(source.getDistrict());
        command.setStreet(source.getStreet());
        return command;
    }
}
