package web.example.realestate.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Garage;

@Component
public class GarageToGarageCommand implements Converter<Garage, FacilityCommand> {

    private final AddressToAddressCommand toAddressCommand;

    private static final Logger LOGGER = LoggerFactory.getLogger(GarageToGarageCommand.class);

    public GarageToGarageCommand(AddressToAddressCommand toAddressCommand) {
        this.toAddressCommand = toAddressCommand;
        LOGGER.info("New instance of GarageToGarageCommand created.");
    }

    @Override
    public FacilityCommand convert(final Garage garage) {
        LOGGER.trace("Enter in 'GarageToGarageCommand.convert' method");

        if (garage == null) {
            LOGGER.debug("Garage is null");
            return null;
        }

        final FacilityCommand command = new FacilityCommand();
        command.setId(garage.getId());
        command.setClientId(garage.getClient().getId());
        command.setNumberOfRooms(garage.getNumberOfRooms());
        command.setTotalArea(garage.getTotalArea());
        command.setDescription(garage.getDescription());
        command.setPublishedDateTime(garage.getPublishedDateTime());
        command.setClosedDateTime(garage.getClosedDateTime());
        command.setHasEquipment(garage.isHasEquipment());
        command.setHasPit(garage.isHasPit());
        command.setMonthRent(garage.getMonthRent());
        command.setPrice(garage.getPrice());
        command.setStatus(garage.getStatus());
        command.setImage(garage.getImage());
        command.setAddress(toAddressCommand.convert(garage.getAddress()));
        command.setItGarage(true);

        LOGGER.trace("'GarageToGarageCommand.convert' executed successfully.");
        return command;
    }
}
