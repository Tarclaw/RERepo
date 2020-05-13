package web.example.realestate.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.ApartmentCommand;
import web.example.realestate.domain.building.Address;
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

        final Address address = toAddress.convert(apartmentCommand.getAddress());
        final Apartment apartment = new Apartment();
        apartment.setNumberOfRooms(apartmentCommand.getNumberOfRooms());
        apartment.setTotalArea(apartmentCommand.getTotalArea());
        apartment.setFloor(apartmentCommand.getFloor());
        apartment.setApartmentNumber(apartmentCommand.getApartmentNumber());
        apartment.setDescription(apartmentCommand.getDescription());
        apartment.setPublishedDateTime(apartmentCommand.getPublishedDateTime());
        apartment.setClosedDateTime(apartmentCommand.getClosedDateTime());
        address.setFacility(apartment);
        apartment.setAddress(address);

        return apartment;
    }

    public Apartment convertWhenAttached(Apartment apartment, ApartmentCommand source) {
        apartment.setNumberOfRooms(source.getNumberOfRooms());
        apartment.setTotalArea(source.getTotalArea());
        apartment.setFloor(source.getFloor());
        apartment.setApartmentNumber(source.getApartmentNumber());
        apartment.setDescription(source.getDescription());
        apartment.setPublishedDateTime(source.getPublishedDateTime());
        apartment.setClosedDateTime(source.getClosedDateTime());

        apartment.getAddress().setPostcode(source.getAddress().getPostcode());
        apartment.getAddress().setFacilityNumber(source.getAddress().getFacilityNumber());
        apartment.getAddress().setCity(source.getAddress().getCity());
        apartment.getAddress().setDistrict(source.getAddress().getDistrict());
        apartment.getAddress().setStreet(source.getAddress().getStreet());

        return apartment;
    }
}
