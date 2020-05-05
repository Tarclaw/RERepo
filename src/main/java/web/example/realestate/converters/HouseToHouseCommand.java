package web.example.realestate.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.HouseCommand;
import web.example.realestate.domain.building.House;

@Component
public class HouseToHouseCommand implements Converter<House, HouseCommand> {

    private AddressToAddressCommand toAddressCommand;

    public HouseToHouseCommand(AddressToAddressCommand toAddressCommand) {
        this.toAddressCommand = toAddressCommand;
    }

    @Override
    public HouseCommand convert(House house) {
        if (house == null) {
            return null;
        }

        final HouseCommand command = new HouseCommand();
        command.setId(house.getId());
        command.setNumberOfRooms(house.getNumberOfRooms());
        command.setTotalArea(house.getTotalArea());
        command.setDescription(house.getDescription());
        command.setPublishedDateTime(house.getPublishedDateTime());
        command.setClosedDateTime(house.getClosedDateTime());
        command.setNumberOfStoreys(house.getNumberOfStoreys());
        command.setHasBackyard(house.isHasBackyard());
        command.setHasGarden(house.isHasGarden());
        command.setAddressCommand(toAddressCommand.convert(house.getAddress()));

        return command;
    }
}
