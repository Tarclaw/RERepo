package web.example.realestate.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Address;
import web.example.realestate.domain.building.Apartment;

@Component
public class ApartmentCommandToApartment implements Converter<FacilityCommand, Apartment> {

    private final AddressCommandToAddress toAddress;

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressCommandToAddress.class);

    public ApartmentCommandToApartment(AddressCommandToAddress toAddress) {
        this.toAddress = toAddress;
        LOGGER.info("New instance of ApartmentCommandToApartment created.");
    }

    @Override
    public Apartment convert(final FacilityCommand command) {
        LOGGER.trace("Enter in 'ApartmentCommandToApartment.convert' method");

        if (command == null) {
            LOGGER.debug("FacilityCommand is null");
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
        apartment.setStatus(command.getStatus());
        apartment.setMonthRent(command.getMonthRent());
        apartment.setPrice(command.getPrice());
        address.setFacility(apartment);
        apartment.setAddress(address);

        LOGGER.trace("'ApartmentCommandToApartment.convert' executed successfully.");
        return apartment;
    }

    public Apartment convertWhenAttached(Apartment apartment, final FacilityCommand command) {
        LOGGER.trace("Enter in 'ApartmentCommandToApartment.convertWhenAttached' method");

        if (apartment == null || command == null) {
            LOGGER.debug("Apartment or FacilityCommand is null");
            return null;
        }

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

        LOGGER.trace("'ApartmentCommandToApartment.convertWhenAttached' executed successfully.");
        return apartment;
    }
}
