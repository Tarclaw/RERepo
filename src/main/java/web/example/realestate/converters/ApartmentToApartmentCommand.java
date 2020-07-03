package web.example.realestate.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Apartment;

@Component
public class ApartmentToApartmentCommand implements Converter<Apartment, FacilityCommand> {

    private final AddressToAddressCommand toAddressCommand;

    public ApartmentToApartmentCommand(AddressToAddressCommand toAddressCommand) {
        this.toAddressCommand = toAddressCommand;
    }

    @Override
    public FacilityCommand convert(Apartment apartment) {
        if (apartment == null) {
            return null;
        }

        final FacilityCommand command = new FacilityCommand();
        command.setId(apartment.getId());
        command.setClientId(apartment.getClient().getId());
        command.setNumberOfRooms(apartment.getNumberOfRooms());
        command.setTotalArea(apartment.getTotalArea());
        command.setFloor(apartment.getFloor());
        command.setApartmentNumber(apartment.getApartmentNumber());
        command.setDescription(apartment.getDescription());
        command.setPublishedDateTime(apartment.getPublishedDateTime());
        command.setClosedDateTime(apartment.getClosedDateTime());
        command.setMonthRent(apartment.getMonthRent());
        command.setPrice(apartment.getPrice());
        command.setStatus(apartment.getStatus());
        command.setImage(apartment.getImage());
        command.setAddress(toAddressCommand.convert(apartment.getAddress()));
        command.setItApartment(true);

        return command;
    }
}
