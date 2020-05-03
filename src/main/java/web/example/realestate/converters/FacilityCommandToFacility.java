package web.example.realestate.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Facility;

@Component
public class FacilityCommandToFacility implements Converter<FacilityCommand, Facility> {

    private final AddressCommandToAddress toAddress;

    public FacilityCommandToFacility(AddressCommandToAddress toAddress) {
        this.toAddress = toAddress;
    }

    @Override
    public Facility convert(FacilityCommand facilityCommand) {
        if (facilityCommand == null) {
            return null;
        }

        final Facility facility = new Facility();
        facility.setId(facilityCommand.getId());
        facility.setNumberOfRooms(facilityCommand.getNumberOfRooms());
        facility.setTotalArea(facilityCommand.getTotalArea());
        facility.setDescription(facilityCommand.getDescription());
        facility.setPublishedDateTime(facilityCommand.getPublishedDateTime());
        facility.setClosedDateTime(facilityCommand.getClosedDateTime());
        facility.setAddress(toAddress.convert(facilityCommand.getAddressCommand()));

        return facility;
    }
}
