package web.example.realestate.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.BasementCommand;
import web.example.realestate.domain.building.Basement;

@Component
public class BasementCommandToBasement implements Converter<BasementCommand, Basement> {

    private final AddressCommandToAddress toAddress;

    public BasementCommandToBasement(AddressCommandToAddress toAddress) {
        this.toAddress = toAddress;
    }

    @Override
    public Basement convert(BasementCommand basementCommand) {
        if (basementCommand == null) {
            return null;
        }

        final Basement basement = new Basement();
        basement.setId(basementCommand.getId());
        basement.setNumberOfRooms(basementCommand.getNumberOfRooms());
        basement.setTotalArea(basementCommand.getTotalArea());
        basement.setDescription(basementCommand.getDescription());
        basement.setItCommercial(basementCommand.isItCommercial());
        basement.setPublishedDateTime(basementCommand.getPublishedDateTime());
        basement.setClosedDateTime(basementCommand.getClosedDateTime());
        basement.setAddress(toAddress.convert(basementCommand.getAddressCommand()));

        return basement;
    }
}
