package web.example.realestate.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Garage;

@Component
public class GarageToGarageCommand implements Converter<Garage, FacilityCommand> {

    private final AddressToAddressCommand toAddressCommand;

    public GarageToGarageCommand(AddressToAddressCommand toAddressCommand) {
        this.toAddressCommand = toAddressCommand;
    }

    @Override
    public FacilityCommand convert(final Garage garage) {
        if (garage == null) {
            return null;
        }

        final FacilityCommand command = new FacilityCommand();
        command.setId(garage.getId());
        command.setNumberOfRooms(garage.getNumberOfRooms());
        command.setTotalArea(garage.getTotalArea());
        command.setDescription(garage.getDescription());
        command.setPublishedDateTime(garage.getPublishedDateTime());
        command.setClosedDateTime(garage.getClosedDateTime());
        command.setHasEquipment(garage.isHasEquipment());
        command.setHasPit(garage.isHasPit());
        command.setMonthRent(garage.getMonthRent());
        command.setPrice(garage.getPrice());
        command.setStatus(garage.getStatus());
        command.setAddress(toAddressCommand.convert(garage.getAddress()));
        command.setItGarage(true);

        return command;
    }
}
