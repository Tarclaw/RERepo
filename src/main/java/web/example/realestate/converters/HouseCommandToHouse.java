package web.example.realestate.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Address;
import web.example.realestate.domain.building.House;

@Component
public class HouseCommandToHouse implements Converter<FacilityCommand, House> {

    private final AddressCommandToAddress toAddress;

    public HouseCommandToHouse(AddressCommandToAddress toAddress) {
        this.toAddress = toAddress;
    }

    @Override
    public House convert(final FacilityCommand command) {
        if (command == null) {
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
        address.setFacility(house);
        house.setAddress(address);

        return house;
    }

    public House convertWhenAttached(House house, FacilityCommand command) {
        house.setNumberOfRooms(command.getNumberOfRooms());
        house.setTotalArea(command.getTotalArea());
        house.setDescription(command.getDescription());
        house.setPublishedDateTime(command.getPublishedDateTime());
        house.setClosedDateTime(command.getClosedDateTime());
        house.setNumberOfStoreys(command.getNumberOfStoreys());
        house.setHasBackyard(command.isHasBackyard());
        house.setHasGarden(command.isHasGarden());

        house.getAddress().setPostcode(command.getAddress().getPostcode());
        house.getAddress().setFacilityNumber(command.getAddress().getFacilityNumber());
        house.getAddress().setCity(command.getAddress().getCity());
        house.getAddress().setDistrict(command.getAddress().getDistrict());
        house.getAddress().setStreet(command.getAddress().getStreet());

        return house;
    }
}
