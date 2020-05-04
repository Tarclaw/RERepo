package web.example.realestate.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.ApartmentCommand;
import web.example.realestate.domain.building.Apartment;

@Component
public class ApartmentCommandToApartment implements Converter<ApartmentCommand, Apartment> {

    private final AddressCommandToAddress toAddress;

    public ApartmentCommandToApartment(AddressCommandToAddress toAddress) {
        this.toAddress = toAddress;
    }

    @Override
    public Apartment convert(ApartmentCommand apartmentCommand) {
        if (apartmentCommand == null) {
            return null;
        }

        final Apartment apartment = new Apartment();
        apartment.setId(apartmentCommand.getId());
        apartment.setNumberOfRooms(apartmentCommand.getNumberOfRooms());
        apartment.setTotalArea(apartmentCommand.getTotalArea());
        apartment.setFloor(apartmentCommand.getFloor());
        apartment.setApartmentNumber(apartmentCommand.getApartmentNumber());
        apartment.setDescription(apartmentCommand.getDescription());
        apartment.setPublishedDateTime(apartmentCommand.getPublishedDateTime());
        apartment.setClosedDateTime(apartmentCommand.getClosedDateTime());
        apartment.setAddress(toAddress.convert(apartmentCommand.getAddressCommand()));

        return apartment;
    }
}
