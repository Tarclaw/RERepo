package web.example.realestate.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Address;
import web.example.realestate.domain.building.Garage;

@Component
public class GarageCommandToGarage implements Converter<FacilityCommand, Garage> {

    private final AddressCommandToAddress toAddress;

    public GarageCommandToGarage(AddressCommandToAddress toAddress) {
        this.toAddress = toAddress;
    }

    @Override
    public Garage convert(final FacilityCommand command) {
        if (command == null) {
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
        address.setFacility(garage);
        garage.setAddress(address);

        return garage;
    }

    public Garage convertWhenAttached(Garage garage, FacilityCommand command) {
        garage.setNumberOfRooms(command.getNumberOfRooms());
        garage.setTotalArea(command.getTotalArea());
        garage.setDescription(command.getDescription());
        garage.setPublishedDateTime(command.getPublishedDateTime());
        garage.setClosedDateTime(command.getClosedDateTime());
        garage.setHasEquipment(command.isHasEquipment());
        garage.setHasPit(command.isHasPit());

        garage.getAddress().setPostcode(command.getAddress().getPostcode());
        garage.getAddress().setFacilityNumber(command.getAddress().getFacilityNumber());
        garage.getAddress().setCity(command.getAddress().getCity());
        garage.getAddress().setDistrict(command.getAddress().getDistrict());
        garage.getAddress().setStreet(command.getAddress().getStreet());

        return garage;
    }
}
