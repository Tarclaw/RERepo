package web.example.realestate.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.ApartmentCommand;
import web.example.realestate.domain.building.Apartment;

@Component
public class ApartmentToApartmentCommand implements Converter<Apartment, ApartmentCommand> {

    private final AddressToAddressCommand toAddressCommand;

    public ApartmentToApartmentCommand(AddressToAddressCommand toAddressCommand) {
        this.toAddressCommand = toAddressCommand;
    }

    @Override
    public ApartmentCommand convert(Apartment apartment) {
        if (apartment == null) {
            return null;
        }

        final ApartmentCommand command = new ApartmentCommand();
        command.setId(apartment.getId());
        command.setNumberOfRooms(apartment.getNumberOfRooms());
        command.setTotalArea(apartment.getTotalArea());
        command.setFloor(apartment.getFloor());
        command.setApartmentNumber(apartment.getApartmentNumber());
        command.setDescription(apartment.getDescription());
        command.setPublishedDateTime(apartment.getPublishedDateTime());
        command.setClosedDateTime(apartment.getClosedDateTime());
        command.setAddressCommand(toAddressCommand.convert(apartment.getAddress()));

        return command;
    }
}
