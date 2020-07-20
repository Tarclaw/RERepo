package web.example.realestate.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.House;

@Component
public class HouseToHouseCommand implements Converter<House, FacilityCommand> {

    private final AddressToAddressCommand toAddressCommand;

    private static final Logger LOGGER = LoggerFactory.getLogger(HouseToHouseCommand.class);

    public HouseToHouseCommand(AddressToAddressCommand toAddressCommand) {
        this.toAddressCommand = toAddressCommand;
        LOGGER.info("New instance of HouseToHouseCommand created.");
    }

    @Override
    public FacilityCommand convert(House house) {
        LOGGER.trace("Enter in 'HouseToHouseCommand.convert' method");

        if (house == null) {
            LOGGER.debug("House is null");
            return null;
        }

        final FacilityCommand command = new FacilityCommand();
        command.setId(house.getId());
        command.setClientId(house.getClient().getId());
        command.setNumberOfRooms(house.getNumberOfRooms());
        command.setTotalArea(house.getTotalArea());
        command.setDescription(house.getDescription());
        command.setPublishedDateTime(house.getPublishedDateTime());
        command.setClosedDateTime(house.getClosedDateTime());
        command.setNumberOfStoreys(house.getNumberOfStoreys());
        command.setHasBackyard(house.isHasBackyard());
        command.setHasGarden(house.isHasGarden());
        command.setMonthRent(house.getMonthRent());
        command.setPrice(house.getPrice());
        command.setStatus(house.getStatus());
        command.setImage(house.getImage());
        command.setAddress(toAddressCommand.convert(house.getAddress()));
        command.setItHouse(true);

        LOGGER.trace("'HouseToHouseCommand.convert' executed successfully.");
        return command;
    }
}
