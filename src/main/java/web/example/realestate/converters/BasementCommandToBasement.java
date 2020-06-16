package web.example.realestate.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Address;
import web.example.realestate.domain.building.Basement;

@Component
public class BasementCommandToBasement implements Converter<FacilityCommand, Basement> {

    private final AddressCommandToAddress toAddress;

    public BasementCommandToBasement(AddressCommandToAddress toAddress) {
        this.toAddress = toAddress;
    }

    @Override
    public Basement convert(final FacilityCommand command) {
        if (command == null) {
            return null;
        }

        final Address address = toAddress.convert(command.getAddress());
        final Basement basement = new Basement();
        basement.setId(command.getId());
        basement.setNumberOfRooms(command.getNumberOfRooms());
        basement.setTotalArea(command.getTotalArea());
        basement.setDescription(command.getDescription());
        basement.setItCommercial(command.isItCommercial());
        basement.setPublishedDateTime(command.getPublishedDateTime());
        basement.setClosedDateTime(command.getClosedDateTime());
        address.setFacility(basement);
        basement.setAddress(address);

        return basement;
    }

    public Basement convertWhenAttached(Basement basement, FacilityCommand command) {
        basement.setNumberOfRooms(command.getNumberOfRooms());
        basement.setTotalArea(command.getTotalArea());
        basement.setDescription(command.getDescription());
        basement.setItCommercial(command.isItCommercial());
        basement.setPublishedDateTime(command.getPublishedDateTime());
        basement.setClosedDateTime(command.getClosedDateTime());
        basement.setStatus(command.getStatus());
        basement.setMonthRent(command.getMonthRent());
        basement.setPrice(command.getPrice());

        basement.getAddress().setPostcode(command.getAddress().getPostcode());
        basement.getAddress().setFacilityNumber(command.getAddress().getFacilityNumber());
        basement.getAddress().setCity(command.getAddress().getCity());
        basement.getAddress().setDistrict(command.getAddress().getDistrict());
        basement.getAddress().setStreet(command.getAddress().getStreet());

        return basement;
    }
}
