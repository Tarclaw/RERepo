package web.example.realestate.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Garage;

@Component
public class GarageCommandToGarage implements Converter<FacilityCommand, Garage> {

    private final AddressCommandToAddress toAddress;

    public GarageCommandToGarage(AddressCommandToAddress toAddress) {
        this.toAddress = toAddress;
    }

    @Override
    public Garage convert(final FacilityCommand command) {
        if (command == null) {
            return null;
        }

        final Garage garage = new Garage();
        garage.setId(command.getId());
        garage.setNumberOfRooms(command.getNumberOfRooms());
        garage.setTotalArea(command.getTotalArea());
        garage.setDescription(command.getDescription());
        garage.setPublishedDateTime(command.getPublishedDateTime());
        garage.setClosedDateTime(command.getClosedDateTime());
        garage.setHasEquipment(command.isHasEquipment());
        garage.setHasPit(command.isHasPit());
        garage.setAddress(toAddress.convert(command.getAddress()));

        return garage;
    }
}
