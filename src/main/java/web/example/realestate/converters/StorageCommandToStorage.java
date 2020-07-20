package web.example.realestate.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Address;
import web.example.realestate.domain.building.Storage;

@Component
public class StorageCommandToStorage implements Converter<FacilityCommand, Storage> {

    private final AddressCommandToAddress toAddress;

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageCommandToStorage.class);

    public StorageCommandToStorage(AddressCommandToAddress toAddress) {
        this.toAddress = toAddress;
        LOGGER.info("New instance of StorageCommandToStorage created.");
    }

    @Override
    public Storage convert(final FacilityCommand command) {
        LOGGER.trace("Enter in 'StorageCommandToStorage.convert' method");

        if (command == null) {
            LOGGER.debug("FacilityCommand is null");
            return null;
        }

        final Address address = toAddress.convert(command.getAddress());
        final Storage storage = new Storage();
        storage.setId(command.getId());
        storage.setNumberOfRooms(command.getNumberOfRooms());
        storage.setTotalArea(command.getTotalArea());
        storage.setDescription(command.getDescription());
        storage.setCommercialCapacity(command.getCommercialCapacity());
        storage.setHasCargoEquipment(command.isHasCargoEquipment());
        storage.setPublishedDateTime(command.getPublishedDateTime());
        storage.setClosedDateTime(command.getClosedDateTime());
        storage.setStatus(command.getStatus());
        storage.setMonthRent(command.getMonthRent());
        storage.setPrice(command.getPrice());
        address.setFacility(storage);
        storage.setAddress(address);

        LOGGER.trace("'StorageCommandToStorage.convert' executed successfully.");
        return storage;
    }

    public Storage convertWhenAttached(Storage storage, FacilityCommand command) {
        LOGGER.trace("Enter in 'StorageCommandToStorage.convertWhenAttached' method");

        storage.setNumberOfRooms(command.getNumberOfRooms());
        storage.setTotalArea(command.getTotalArea());
        storage.setDescription(command.getDescription());
        storage.setCommercialCapacity(command.getCommercialCapacity());
        storage.setHasCargoEquipment(command.isHasCargoEquipment());
        storage.setPublishedDateTime(command.getPublishedDateTime());
        storage.setClosedDateTime(command.getClosedDateTime());
        storage.setStatus(command.getStatus());
        storage.setMonthRent(command.getMonthRent());
        storage.setPrice(command.getPrice());

        storage.getAddress().setPostcode(command.getAddress().getPostcode());
        storage.getAddress().setFacilityNumber(command.getAddress().getFacilityNumber());
        storage.getAddress().setCity(command.getAddress().getCity());
        storage.getAddress().setDistrict(command.getAddress().getDistrict());
        storage.getAddress().setStreet(command.getAddress().getStreet());

        LOGGER.trace("'StorageCommandToStorage.convertWhenAttached' executed successfully.");
        return storage;
    }
}
