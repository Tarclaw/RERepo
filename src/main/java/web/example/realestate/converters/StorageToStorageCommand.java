package web.example.realestate.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Storage;

@Component
public class StorageToStorageCommand implements Converter<Storage, FacilityCommand> {

    private final AddressToAddressCommand toAddressCommand;

    public StorageToStorageCommand(AddressToAddressCommand toAddressCommand) {
        this.toAddressCommand = toAddressCommand;
    }

    @Override
    public FacilityCommand convert(final Storage storage) {
        if (storage == null) {
            return null;
        }

        final FacilityCommand command = new FacilityCommand();
        command.setId(storage.getId());
        command.setNumberOfRooms(storage.getNumberOfRooms());
        command.setTotalArea(storage.getTotalArea());
        command.setDescription(storage.getDescription());
        command.setCommercialCapacity(storage.getCommercialCapacity());
        command.setHasCargoEquipment(storage.isHasCargoEquipment());
        command.setPublishedDateTime(storage.getPublishedDateTime());
        command.setClosedDateTime(storage.getClosedDateTime());
        command.setMonthRent(storage.getMonthRent());
        command.setPrice(storage.getPrice());
        command.setStatus(storage.getStatus());
        command.setAddress(toAddressCommand.convert(storage.getAddress()));
        command.setStorage(true);

        return command;
    }
}
