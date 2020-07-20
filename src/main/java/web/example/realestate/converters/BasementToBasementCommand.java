package web.example.realestate.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Basement;

@Component
public class BasementToBasementCommand implements Converter<Basement, FacilityCommand> {

    private final AddressToAddressCommand toAddressCommand;

    private static final Logger LOGGER = LoggerFactory.getLogger(BasementToBasementCommand.class);

    public BasementToBasementCommand(AddressToAddressCommand toAddressCommand) {
        this.toAddressCommand = toAddressCommand;
        LOGGER.info("New instance of BasementToBasementCommand created.");
    }

    @Override
    public FacilityCommand convert(Basement basement) {
        LOGGER.trace("Enter in 'BasementToBasementCommand.convert' method");

        if (basement == null) {
            LOGGER.debug("Basement is null");
            return null;
        }

        final FacilityCommand command = new FacilityCommand();
        command.setId(basement.getId());
        command.setClientId(basement.getClient().getId());
        command.setNumberOfRooms(basement.getNumberOfRooms());
        command.setTotalArea(basement.getTotalArea());
        command.setDescription(basement.getDescription());
        command.setItCommercial(basement.isItCommercial());
        command.setPublishedDateTime(basement.getPublishedDateTime());
        command.setClosedDateTime(basement.getClosedDateTime());
        command.setMonthRent(basement.getMonthRent());
        command.setPrice(basement.getPrice());
        command.setStatus(basement.getStatus());
        command.setImage(basement.getImage());
        command.setAddress(toAddressCommand.convert(basement.getAddress()));
        command.setItBasement(true);

        LOGGER.trace("'BasementToBasementCommand.convert' executed successfully.");
        return command;
    }
}
