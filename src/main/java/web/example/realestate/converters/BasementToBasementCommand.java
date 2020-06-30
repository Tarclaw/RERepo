package web.example.realestate.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Basement;

@Component
public class BasementToBasementCommand implements Converter<Basement, FacilityCommand> {

    private final AddressToAddressCommand toAddressCommand;

    public BasementToBasementCommand(AddressToAddressCommand toAddressCommand) {
        this.toAddressCommand = toAddressCommand;
    }

    @Override
    public FacilityCommand convert(Basement basement) {
        if (basement == null) {
            return null;
        }

        final FacilityCommand command = new FacilityCommand();
        command.setId(basement.getId());
        command.setNumberOfRooms(basement.getNumberOfRooms());
        command.setTotalArea(basement.getTotalArea());
        command.setDescription(basement.getDescription());
        command.setItCommercial(basement.isItCommercial());
        command.setPublishedDateTime(basement.getPublishedDateTime());
        command.setClosedDateTime(basement.getClosedDateTime());
        command.setMonthRent(basement.getMonthRent());
        command.setPrice(basement.getPrice());
        command.setStatus(basement.getStatus());
        command.setAddress(toAddressCommand.convert(basement.getAddress()));
        command.setItBasement(true);

        return command;
    }
}
