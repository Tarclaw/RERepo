package web.example.realestate.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Facility;

@Component
public class FacilityToFacilityCommand implements Converter<Facility, FacilityCommand> {

    private final AddressToAddressCommand toAddressCommand;

    public FacilityToFacilityCommand(AddressToAddressCommand toAddressCommand) {
        this.toAddressCommand = toAddressCommand;
    }

    @Override
    public FacilityCommand convert(Facility facility) {
        if (facility == null) {
            return null;
        }

        final FacilityCommand command = new FacilityCommand();
        command.setId(facility.getId());
        command.setNumberOfRooms(facility.getNumberOfRooms());
        command.setTotalArea(facility.getTotalArea());
        command.setDescription(facility.getDescription());
        command.setPublishedDateTime(facility.getPublishedDateTime());
        command.setClosedDateTime(facility.getClosedDateTime());
        command.setAddressCommand(toAddressCommand.convert(facility.getAddress()));

        return command;
    }
}
