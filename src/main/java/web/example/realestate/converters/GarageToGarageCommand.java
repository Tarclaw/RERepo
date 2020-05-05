package web.example.realestate.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.GarageCommand;
import web.example.realestate.domain.building.Garage;

@Component
public class GarageToGarageCommand implements Converter<Garage, GarageCommand> {

    private final AddressToAddressCommand toAddressCommand;

    public GarageToGarageCommand(AddressToAddressCommand toAddressCommand) {
        this.toAddressCommand = toAddressCommand;
    }

    @Override
    public GarageCommand convert(Garage garage) {
        if (garage == null) {
            return null;
        }

        final GarageCommand command = new GarageCommand();
        command.setId(garage.getId());
        command.setNumberOfRooms(garage.getNumberOfRooms());
        command.setTotalArea(garage.getTotalArea());
        command.setDescription(garage.getDescription());
        command.setPublishedDateTime(garage.getPublishedDateTime());
        command.setClosedDateTime(garage.getClosedDateTime());
        command.setHasEquipment(garage.isHasEquipment());
        command.setHasPit(garage.isHasPit());
        command.setAddressCommand(toAddressCommand.convert(garage.getAddress()));

        return command;
    }
}
