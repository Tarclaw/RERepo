package web.example.realestate.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.BasementCommand;
import web.example.realestate.domain.building.Basement;

@Component
public class BasementToBasementCommand implements Converter<Basement, BasementCommand> {

    private final AddressToAddressCommand toAddressCommand;

    public BasementToBasementCommand(AddressToAddressCommand toAddressCommand) {
        this.toAddressCommand = toAddressCommand;
    }

    @Override
    public BasementCommand convert(Basement basement) {
        if (basement == null) {
            return null;
        }

        final BasementCommand command = new BasementCommand();
        command.setId(basement.getId());
        command.setNumberOfRooms(basement.getNumberOfRooms());
        command.setTotalArea(basement.getTotalArea());
        command.setDescription(basement.getDescription());
        command.setItCommercial(basement.isItCommercial());
        command.setPublishedDateTime(basement.getPublishedDateTime());
        command.setClosedDateTime(basement.getClosedDateTime());
        command.setAddressCommand(toAddressCommand.convert(basement.getAddress()));

        return command;
    }
}
