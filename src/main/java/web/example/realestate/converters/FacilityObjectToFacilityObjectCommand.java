package web.example.realestate.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.commands.FacilityObjectCommand;
import web.example.realestate.domain.building.FacilityObject;

import java.util.HashSet;
import java.util.Set;

@Component
public class FacilityObjectToFacilityObjectCommand implements Converter<FacilityObject, FacilityObjectCommand> {

    private final RealEstateAgentToRealEstateAgentCommand toRealEstateAgentCommand;
    private final FacilityToFacilityCommand toFacilityCommand;

    public FacilityObjectToFacilityObjectCommand(RealEstateAgentToRealEstateAgentCommand toRealEstateAgentCommand,
                                                 FacilityToFacilityCommand toFacilityCommand) {
        this.toRealEstateAgentCommand = toRealEstateAgentCommand;
        this.toFacilityCommand = toFacilityCommand;
    }

    @Override
    public FacilityObjectCommand convert(FacilityObject facilityObject) {
        if (facilityObject == null) {
            return null;
        }

        final FacilityObjectCommand command = new FacilityObjectCommand();
        command.setId(facilityObject.getId());
        command.setMonthRent(facilityObject.getMonthRent());
        command.setPrice(facilityObject.getPrice());
        command.setCommissionAmount(facilityObject.getCommissionAmount());
        command.setStatus(facilityObject.getStatus());
        command.setRealEstateAgentCommand(toRealEstateAgentCommand.convert(facilityObject.getAgent()));

        /*if (facilityObject.getFacilities() != null && facilityObject.getFacilities().size() > 0) {
            Set<FacilityCommand> source = new HashSet<>();
            facilityObject.getFacilities().forEach(facility -> source.add(toFacilityCommand.convert(facility)));
            command.setFacilityCommands(source);
        }*/

        return command;
    }
}
