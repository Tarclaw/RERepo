package web.example.realestate.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.FacilityCommand;
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

        final Basement basement = new Basement();
        basement.setId(command.getId());
        basement.setNumberOfRooms(command.getNumberOfRooms());
        basement.setTotalArea(command.getTotalArea());
        basement.setDescription(command.getDescription());
        basement.setItCommercial(command.isItCommercial());
        basement.setPublishedDateTime(command.getPublishedDateTime());
        basement.setClosedDateTime(command.getClosedDateTime());
        basement.setAddress(toAddress.convert(command.getAddress()));

        return basement;
    }
}
