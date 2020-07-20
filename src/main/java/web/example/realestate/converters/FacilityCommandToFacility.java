package web.example.realestate.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Facility;

@Component
public class FacilityCommandToFacility implements Converter<FacilityCommand, Facility> {

    private final ApartmentCommandToApartment toApartment;
    private final BasementCommandToBasement toBasement;
    private final GarageCommandToGarage toGarage;
    private final HouseCommandToHouse toHouse;
    private final StorageCommandToStorage toStorage;

    private static final Logger LOGGER = LoggerFactory.getLogger(FacilityCommandToFacility.class);

    public FacilityCommandToFacility(ApartmentCommandToApartment toApartment, BasementCommandToBasement toBasement,
                                     GarageCommandToGarage toGarage, HouseCommandToHouse toHouse,
                                     StorageCommandToStorage toStorage) {
        this.toApartment = toApartment;
        this.toBasement = toBasement;
        this.toGarage = toGarage;
        this.toHouse = toHouse;
        this.toStorage = toStorage;
        LOGGER.info("New instance of FacilityCommandToFacility created.");
    }

    @Override
    public Facility convert(final FacilityCommand command) {
        LOGGER.trace("Enter in 'FacilityCommandToFacility.convert' method");

        if (command == null) {
            LOGGER.debug("FacilityCommand is null");
            return null;
        }

        if (command.isItApartment()) {
            LOGGER.trace("'FacilityCommandToFacility.convert' executed successfully.");
            return toApartment.convert(command);
        }

        if (command.isItBasement()) {
            LOGGER.trace("'FacilityCommandToFacility.convert' executed successfully.");
            return toBasement.convert(command);
        }

        if (command.isItGarage()) {
            LOGGER.trace("'FacilityCommandToFacility.convert' executed successfully.");
            return toGarage.convert(command);
        }

        if (command.isItHouse()) {
            LOGGER.trace("'FacilityCommandToFacility.convert' executed successfully.");
            return toHouse.convert(command);
        }

        if (command.isItStorage()) {
            LOGGER.trace("'FacilityCommandToFacility.convert' executed successfully.");
            return toStorage.convert(command);
        }

        LOGGER.trace("'FacilityCommandToFacility.convert' executed successfully. It's null.");
        return null;
    }
}
