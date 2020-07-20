package web.example.realestate.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Storage;

@Component
public class StorageToStorageCommand implements Converter<Storage, FacilityCommand> {

    private final AddressToAddressCommand toAddressCommand;

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageToStorageCommand.class);

    public StorageToStorageCommand(AddressToAddressCommand toAddressCommand) {
        this.toAddressCommand = toAddressCommand;
        LOGGER.info("New instance of StorageToStorageCommand created.");
    }

    @Override
    public FacilityCommand convert(final Storage storage) {
        LOGGER.trace("Enter in 'StorageToStorageCommand.convert' method");

        if (storage == null) {
            LOGGER.debug("Storage is null");
            return null;
        }

        final FacilityCommand command = new FacilityCommand();
        command.setId(storage.getId());
        command.setClientId(storage.getClient().getId());
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
        command.setImage(storage.getImage());
        command.setAddress(toAddressCommand.convert(storage.getAddress()));
        command.setItStorage(true);

        LOGGER.trace("'StorageToStorageCommand.convert' executed successfully.");
        return command;
    }
}
