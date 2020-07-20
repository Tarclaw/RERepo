package web.example.realestate.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Address;
import web.example.realestate.domain.building.House;

@Component
public class HouseCommandToHouse implements Converter<FacilityCommand, House> {

    private final AddressCommandToAddress toAddress;

    private static final Logger LOGGER = LoggerFactory.getLogger(HouseCommandToHouse.class);

    public HouseCommandToHouse(AddressCommandToAddress toAddress) {
        this.toAddress = toAddress;
        LOGGER.info("New instance of HouseCommandToHouse created.");
    }

    @Override
    public House convert(final FacilityCommand command) {
        LOGGER.trace("Enter in 'HouseCommandToHouse.convert' method");

        if (command == null) {
            LOGGER.debug("FacilityCommand is null");
            return null;
        }

        final Address address = toAddress.convert(command.getAddress());
        final House house = new House();
        house.setId(command.getId());
        house.setNumberOfRooms(command.getNumberOfRooms());
        house.setTotalArea(command.getTotalArea());
        house.setDescription(command.getDescription());
        house.setPublishedDateTime(command.getPublishedDateTime());
        house.setClosedDateTime(command.getClosedDateTime());
        house.setNumberOfStoreys(command.getNumberOfStoreys());
        house.setHasBackyard(command.isHasBackyard());
        house.setHasGarden(command.isHasGarden());
        house.setStatus(command.getStatus());
        house.setMonthRent(command.getMonthRent());
        house.setPrice(command.getPrice());
        address.setFacility(house);
        house.setAddress(address);

        LOGGER.trace("'HouseCommandToHouse.convert' executed successfully.");
        return house;
    }

    public House convertWhenAttached(House house, FacilityCommand command) {
        LOGGER.trace("Enter in 'HouseCommandToHouse.convertWhenAttached' method");

        house.setNumberOfRooms(command.getNumberOfRooms());
        house.setTotalArea(command.getTotalArea());
        house.setDescription(command.getDescription());
        house.setPublishedDateTime(command.getPublishedDateTime());
        house.setClosedDateTime(command.getClosedDateTime());
        house.setNumberOfStoreys(command.getNumberOfStoreys());
        house.setHasBackyard(command.isHasBackyard());
        house.setHasGarden(command.isHasGarden());
        house.setStatus(command.getStatus());
        house.setMonthRent(command.getMonthRent());
        house.setPrice(command.getPrice());

        house.getAddress().setPostcode(command.getAddress().getPostcode());
        house.getAddress().setFacilityNumber(command.getAddress().getFacilityNumber());
        house.getAddress().setCity(command.getAddress().getCity());
        house.getAddress().setDistrict(command.getAddress().getDistrict());
        house.getAddress().setStreet(command.getAddress().getStreet());

        LOGGER.trace("'HouseCommandToHouse.convertWhenAttached' executed successfully.");
        return house;
    }
}
