package web.example.realestate.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.StorageCommand;
import web.example.realestate.domain.building.Storage;

@Component
public class StorageToStorageCommand implements Converter<Storage, StorageCommand> {

    private final AddressToAddressCommand toAddressCommand;

    public StorageToStorageCommand(AddressToAddressCommand toAddressCommand) {
        this.toAddressCommand = toAddressCommand;
    }

    @Override
    public StorageCommand convert(Storage storage) {
        if (storage == null) {
            return null;
        }

        final StorageCommand command = new StorageCommand();
        command.setId(storage.getId());
        command.setNumberOfRooms(storage.getNumberOfRooms());
        command.setTotalArea(storage.getTotalArea());
        command.setDescription(storage.getDescription());
        command.setCommercialCapacity(storage.getCommercialCapacity());
        command.setHasCargoEquipment(storage.isHasCargoEquipment());
        command.setPublishedDateTime(storage.getPublishedDateTime());
        command.setClosedDateTime(storage.getClosedDateTime());
        command.setAddressCommand(toAddressCommand.convert(storage.getAddress()));

        return command;
    }
}
