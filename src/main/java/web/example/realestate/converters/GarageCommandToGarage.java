package web.example.realestate.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Address;
import web.example.realestate.domain.building.Garage;

@Component
public class GarageCommandToGarage implements Converter<FacilityCommand, Garage> {

    private final AddressCommandToAddress toAddress;

    private static final Logger LOGGER = LoggerFactory.getLogger(GarageCommandToGarage.class);

    public GarageCommandToGarage(AddressCommandToAddress toAddress) {
        this.toAddress = toAddress;
        LOGGER.info("New instance of GarageCommandToGarage created.");
    }

    @Override
    public Garage convert(final FacilityCommand command) {
        LOGGER.trace("Enter in 'GarageCommandToGarage.convert' method");
        if (command == null) {
            LOGGER.debug("FacilityCommand is null");
            return null;
        }

        final Address address = toAddress.convert(command.getAddress());
        final Garage garage = new Garage();
        garage.setId(command.getId());
        garage.setNumberOfRooms(command.getNumberOfRooms());
        garage.setTotalArea(command.getTotalArea());
        garage.setDescription(command.getDescription());
        garage.setPublishedDateTime(command.getPublishedDateTime());
        garage.setClosedDateTime(command.getClosedDateTime());
        garage.setHasEquipment(command.isHasEquipment());
        garage.setHasPit(command.isHasPit());
        garage.setStatus(command.getStatus());
        garage.setMonthRent(command.getMonthRent());
        garage.setPrice(command.getPrice());
        address.setFacility(garage);
        garage.setAddress(address);

        LOGGER.trace("'GarageCommandToGarage.convert' executed successfully.");
        return garage;
    }

    public Garage convertWhenAttached(Garage garage, FacilityCommand command) {
        LOGGER.trace("Enter in 'GarageCommandToGarage.convertWhenAttached' method");

        garage.setNumberOfRooms(command.getNumberOfRooms());
        garage.setTotalArea(command.getTotalArea());
        garage.setDescription(command.getDescription());
        garage.setPublishedDateTime(command.getPublishedDateTime());
        garage.setClosedDateTime(command.getClosedDateTime());
        garage.setHasEquipment(command.isHasEquipment());
        garage.setHasPit(command.isHasPit());
        garage.setStatus(command.getStatus());
        garage.setMonthRent(command.getMonthRent());
        garage.setPrice(command.getPrice());

        garage.getAddress().setPostcode(command.getAddress().getPostcode());
        garage.getAddress().setFacilityNumber(command.getAddress().getFacilityNumber());
        garage.getAddress().setCity(command.getAddress().getCity());
        garage.getAddress().setDistrict(command.getAddress().getDistrict());
        garage.getAddress().setStreet(command.getAddress().getStreet());

        LOGGER.trace("'GarageCommandToGarage.convertWhenAttached' executed successfully.");
        return garage;
    }
}
