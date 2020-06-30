package web.example.realestate.converters;

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

    public FacilityCommandToFacility(ApartmentCommandToApartment toApartment, BasementCommandToBasement toBasement,
                                     GarageCommandToGarage toGarage, HouseCommandToHouse toHouse,
                                     StorageCommandToStorage toStorage) {
        this.toApartment = toApartment;
        this.toBasement = toBasement;
        this.toGarage = toGarage;
        this.toHouse = toHouse;
        this.toStorage = toStorage;
    }

    @Override
    public Facility convert(final FacilityCommand command) {
        if (command == null) {
            return null;
        }

        if (command.isItApartment()) {
            return toApartment.convert(command);
        }

        if (command.isItBasement()) {
            return toBasement.convert(command);
        }

        if (command.isItGarage()) {
            return toGarage.convert(command);
        }

        if (command.isItHouse()) {
            return toHouse.convert(command);
        }

        if (command.isItStorage()) {
            return toStorage.convert(command);
        }

        return null;
    }
}
