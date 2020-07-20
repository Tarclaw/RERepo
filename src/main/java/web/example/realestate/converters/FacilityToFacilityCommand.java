package web.example.realestate.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.*;

@Component
public class FacilityToFacilityCommand implements Converter<Facility, FacilityCommand> {

    private final AddressToAddressCommand toAddressCommand;

    private static final Logger LOGGER = LoggerFactory.getLogger(FacilityToFacilityCommand.class);

    public FacilityToFacilityCommand(AddressToAddressCommand toAddressCommand) {
        this.toAddressCommand = toAddressCommand;
        LOGGER.info("New instance of FacilityToFacilityCommand created.");
    }

    @Override
    public FacilityCommand convert(Facility facility) {
        LOGGER.trace("Enter in 'FacilityToFacilityCommand.convert' method");

        if (facility == null) {
            LOGGER.debug("Facility is null");
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
            LOGGER.trace("'FacilityCommandToFacility.convert' executed successfully. Converted to Apartment.");
            return command;
        }

        if (facility instanceof Basement) {
            Basement basement = (Basement) facility;
            command.setItBasement(true);
            command.setItCommercial(basement.isItCommercial());
            LOGGER.trace("'FacilityCommandToFacility.convert' executed successfully. Converted to Basement.");
            return command;
        }

        if (facility instanceof Garage) {
            Garage garage = (Garage) facility;
            command.setItGarage(true);
            command.setHasPit(garage.isHasPit());
            command.setHasEquipment(garage.isHasEquipment());
            LOGGER.trace("'FacilityCommandToFacility.convert' executed successfully. Converted to Garage.");
            return command;
        }

        if (facility instanceof House) {
            House house = (House) facility;
            command.setItHouse(true);
            command.setNumberOfStoreys(house.getNumberOfStoreys());
            command.setHasBackyard(house.isHasBackyard());
            command.setHasGarden(house.isHasGarden());
            LOGGER.trace("'FacilityCommandToFacility.convert' executed successfully. Converted to House.");
            return command;
        }

        if (facility instanceof Storage) {
            Storage storage = (Storage) facility;
            command.setItStorage(true);
            command.setCommercialCapacity(storage.getCommercialCapacity());
            command.setHasCargoEquipment(storage.isHasCargoEquipment());
        }

        LOGGER.trace("'FacilityCommandToFacility.convert' executed successfully. Converted to Storage.");
        return command;
    }
}
