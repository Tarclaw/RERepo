package web.example.realestate.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Address;
import web.example.realestate.domain.building.Apartment;

@Component
public class ApartmentCommandToApartment implements Converter<FacilityCommand, Apartment> {

    private final AddressCommandToAddress toAddress;

    public ApartmentCommandToApartment(AddressCommandToAddress toAddress) {
        this.toAddress = toAddress;
    }

    @Override
    public Apartment convert(final FacilityCommand command) {
        if (command == null) {
            return null;
        }

        final Address address = toAddress.convert(command.getAddress());
        final Apartment apartment = new Apartment();
        apartment.setId(command.getId());
        apartment.setNumberOfRooms(command.getNumberOfRooms());
        apartment.setTotalArea(command.getTotalArea());
        apartment.setFloor(command.getFloor());
        apartment.setApartmentNumber(command.getApartmentNumber());
        apartment.setDescription(command.getDescription());
        apartment.setPublishedDateTime(command.getPublishedDateTime());
        apartment.setClosedDateTime(command.getClosedDateTime());
        address.setFacility(apartment);
        apartment.setAddress(address);

        return apartment;
    }

    public Apartment convertWhenAttached(Apartment apartment, final FacilityCommand command) {
        apartment.setNumberOfRooms(command.getNumberOfRooms());
        apartment.setTotalArea(command.getTotalArea());
        apartment.setFloor(command.getFloor());
        apartment.setApartmentNumber(command.getApartmentNumber());
        apartment.setDescription(command.getDescription());
        apartment.setPublishedDateTime(command.getPublishedDateTime());
        apartment.setClosedDateTime(command.getClosedDateTime());
        apartment.setStatus(command.getStatus());
        apartment.setMonthRent(command.getMonthRent());
        apartment.setPrice(command.getPrice());

        apartment.getAddress().setPostcode(command.getAddress().getPostcode());
        apartment.getAddress().setFacilityNumber(command.getAddress().getFacilityNumber());
        apartment.getAddress().setCity(command.getAddress().getCity());
        apartment.getAddress().setDistrict(command.getAddress().getDistrict());
        apartment.getAddress().setStreet(command.getAddress().getStreet());

        return apartment;
    }
}
