package web.example.realestate.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Storage;

@Component
public class StorageCommandToStorage implements Converter<FacilityCommand, Storage> {

    private final AddressCommandToAddress toAddress;

    public StorageCommandToStorage(AddressCommandToAddress toAddress) {
        this.toAddress = toAddress;
    }

    @Override
    public Storage convert(final FacilityCommand command) {
        if (command == null) {
            return null;
        }

        final Storage storage = new Storage();
        storage.setId(command.getId());
        storage.setNumberOfRooms(command.getNumberOfRooms());
        storage.setTotalArea(command.getTotalArea());
        storage.setDescription(command.getDescription());
        storage.setCommercialCapacity(command.getCommercialCapacity());
        storage.setHasCargoEquipment(command.isHasCargoEquipment());
        storage.setPublishedDateTime(command.getPublishedDateTime());
        storage.setClosedDateTime(command.getClosedDateTime());
        storage.setAddress(toAddress.convert(command.getAddress()));

        return storage;
    }
}
