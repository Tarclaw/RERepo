package web.example.realestate.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.HouseCommand;
import web.example.realestate.domain.building.House;

@Component
public class HouseCommandToHouse implements Converter<HouseCommand, House> {

    private final AddressCommandToAddress toAddress;

    public HouseCommandToHouse(AddressCommandToAddress toAddress) {
        this.toAddress = toAddress;
    }

    @Override
    public House convert(HouseCommand command) {
        if (command == null) {
            return null;
        }

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
        house.setAddress(toAddress.convert(command.getAddressCommand()));

        return house;
    }
}
