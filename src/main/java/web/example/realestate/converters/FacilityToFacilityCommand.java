package web.example.realestate.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.*;

@Component
public class FacilityToFacilityCommand implements Converter<Facility, FacilityCommand> {

    private final AddressToAddressCommand toAddressCommand;

    public FacilityToFacilityCommand(AddressToAddressCommand toAddressCommand) {
        this.toAddressCommand = toAddressCommand;
    }

    @Override
    public FacilityCommand convert(Facility facility) {
        if (facility == null) {
            return null;
        }

        final FacilityCommand command = new FacilityCommand();
        command.setId(facility.getId());
        command.setNumberOfRooms(facility.getNumberOfRooms());
        command.setTotalArea(facility.getTotalArea());
        command.setDescription(facility.getDescription());
        command.setPublishedDateTime(facility.getPublishedDateTime());
        command.setClosedDateTime(facility.getClosedDateTime());
        command.setMonthRent(facility.getMonthRent());
        command.setPrice(facility.getPrice());
        command.setStatus(facility.getStatus());
        command.setAddress(toAddressCommand.convert(facility.getAddress()));

        if (facility instanceof Apartment) {
            Apartment apartment = (Apartment) facility;
            command.setItApartment(true);
            command.setApartmentNumber(apartment.getApartmentNumber());
            command.setFloor(apartment.getFloor());
            return command;
        }

        if (facility instanceof Basement) {
            Basement basement = (Basement) facility;
            command.setItBasement(true);
            command.setItCommercial(basement.isItCommercial());
            return command;
        }

        if (facility instanceof Garage) {
            Garage garage = (Garage) facility;
            command.setItGarage(true);
            command.setHasPit(garage.isHasPit());
            command.setHasEquipment(garage.isHasEquipment());
            return command;
        }

        if (facility instanceof House) {
            House house = (House) facility;
            command.setItHouse(true);
            command.setNumberOfStoreys(house.getNumberOfStoreys());
            command.setHasBackyard(house.isHasBackyard());
            command.setHasGarden(house.isHasGarden());
            return command;
        }

        if (facility instanceof Storage) {
            Storage storage = (Storage) facility;
            command.setItStorage(true);
            command.setCommercialCapacity(storage.getCommercialCapacity());
            command.setHasCargoEquipment(storage.isHasCargoEquipment());
        }

        return command;
    }
}
