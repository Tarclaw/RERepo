package web.example.realestate.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.FacilityObjectCommand;
import web.example.realestate.domain.building.Facility;
import web.example.realestate.domain.building.FacilityObject;

import java.util.HashSet;
import java.util.Set;

@Component
public class FacilityObjectCommandToFacilityObject implements Converter<FacilityObjectCommand, FacilityObject> {

    private final RealEstateAgentCommandToRealEstateAgent toRealEstateAgent;
    private final FacilityCommandToFacility toFacility;

    public FacilityObjectCommandToFacilityObject(RealEstateAgentCommandToRealEstateAgent toRealEstateAgent,
                                                 FacilityCommandToFacility toFacility) {
        this.toRealEstateAgent = toRealEstateAgent;
        this.toFacility = toFacility;
    }

    @Override
    public FacilityObject convert(FacilityObjectCommand command) {
        if (command == null) {
            return null;
        }

        final FacilityObject facilityObject = new FacilityObject();
        facilityObject.setId(command.getId());
        facilityObject.setMonthRent(command.getMonthRent());
        facilityObject.setPrice(command.getPrice());
        facilityObject.setCommissionAmount(command.getCommissionAmount());
        facilityObject.setStatus(command.getStatus());
        facilityObject.setAgent(toRealEstateAgent.convert(command.getRealEstateAgentCommand()));

        if (command.getFacilityCommands() != null && command.getFacilityCommands().size() > 0) {
            Set<Facility> facilitySet = new HashSet<>();
            command.getFacilityCommands().forEach(facilityCommand -> facilitySet.add((toFacility.convert(facilityCommand))));
            facilityObject.setFacilities(facilitySet);
        }

        return facilityObject;
    }
}
