package web.example.realestate.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.AddressCommand;
import web.example.realestate.domain.building.Address;

@Component
public class AddressCommandToAddress implements Converter<AddressCommand, Address> {

    @Override
    public Address convert(AddressCommand source) {
        if (source == null) {
            return null;
        }

        final Address address = new Address();
        address.setId(source.getId());
        address.setPostcode(source.getPostcode());
        address.setFacilityNumber(source.getFacilityNumber());
        address.setCity(source.getCity());
        address.setDistrict(source.getDistrict());
        address.setStreet(source.getStreet());
        return address;
    }
}
