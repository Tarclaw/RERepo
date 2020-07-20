package web.example.realestate.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Address;
import web.example.realestate.domain.building.Basement;

@Component
public class BasementCommandToBasement implements Converter<FacilityCommand, Basement> {

    private final AddressCommandToAddress toAddress;

    private static final Logger LOGGER = LoggerFactory.getLogger(BasementCommandToBasement.class);

    public BasementCommandToBasement(AddressCommandToAddress toAddress) {
        this.toAddress = toAddress;
        LOGGER.info("New instance of BasementCommandToBasement created.");
    }

    @Override
    public Basement convert(final FacilityCommand command) {
        LOGGER.trace("Enter in 'BasementCommandToBasement.convert' method");

        if (command == null) {
            LOGGER.debug("FacilityCommand is null");
            return null;
        }

        final Address address = toAddress.convert(command.getAddress());
        final Basement basement = new Basement();
        basement.setId(command.getId());
        basement.setNumberOfRooms(command.getNumberOfRooms());
        basement.setTotalArea(command.getTotalArea());
        basement.setDescription(command.getDescription());
        basement.setItCommercial(command.isItCommercial());
        basement.setPublishedDateTime(command.getPublishedDateTime());
        basement.setClosedDateTime(command.getClosedDateTime());
        basement.setStatus(command.getStatus());
        basement.setMonthRent(command.getMonthRent());
        basement.setPrice(command.getPrice());
        address.setFacility(basement);
        basement.setAddress(address);

        LOGGER.trace("'BasementCommandToBasement.convert' executed successfully.");
        return basement;
    }

    public Basement convertWhenAttached(Basement basement, final FacilityCommand command) {
        LOGGER.trace("Enter in 'BasementCommandToBasement.convertWhenAttached' method");

        basement.setNumberOfRooms(command.getNumberOfRooms());
        basement.setTotalArea(command.getTotalArea());
        basement.setDescription(command.getDescription());
        basement.setItCommercial(command.isItCommercial());
        basement.setPublishedDateTime(command.getPublishedDateTime());
        basement.setClosedDateTime(command.getClosedDateTime());
        basement.setStatus(command.getStatus());
        basement.setMonthRent(command.getMonthRent());
        basement.setPrice(command.getPrice());

        basement.getAddress().setPostcode(command.getAddress().getPostcode());
        basement.getAddress().setFacilityNumber(command.getAddress().getFacilityNumber());
        basement.getAddress().setCity(command.getAddress().getCity());
        basement.getAddress().setDistrict(command.getAddress().getDistrict());
        basement.getAddress().setStreet(command.getAddress().getStreet());

        LOGGER.trace("'BasementCommandToBasement.convertWhenAttached' executed successfully.");
        return basement;
    }
}
