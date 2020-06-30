package web.example.realestate.services.implementation;

import org.springframework.stereotype.Service;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.converters.FacilityCommandToFacility;
import web.example.realestate.domain.building.*;
import web.example.realestate.repositories.*;
import web.example.realestate.services.FacilityService;

import java.util.ArrayList;
import java.util.List;

@Service
public class FacilityServiceImpl implements FacilityService {

    private final FacilityRepository repository;
    private final FacilityCommandToFacility toFacility;

    private final ApartmentRepository apartmentRepository;
    private final BasementRepository basementRepository;
    private final GarageRepository garageRepository;
    private final HouseRepository houseRepository;
    private final StorageRepository storageRepository;

    public FacilityServiceImpl(FacilityRepository repository, FacilityCommandToFacility toFacility,
                               ApartmentRepository apartmentRepository, BasementRepository basementRepository,
                               GarageRepository garageRepository, HouseRepository houseRepository,
                               StorageRepository storageRepository) {
        this.repository = repository;
        this.toFacility = toFacility;
        this.apartmentRepository = apartmentRepository;
        this.basementRepository = basementRepository;
        this.garageRepository = garageRepository;
        this.houseRepository = houseRepository;
        this.storageRepository = storageRepository;
    }

    @Override
    public List<Facility> getFacilities() {
        List<Facility> facilities = new ArrayList<>();
        repository.findAll().iterator().forEachRemaining(facilities :: add);
        return facilities;
    }

    @Override
    public List<Facility> getFacilitiesByIds(Long clientId) {
        return repository.findFacilitiesByClientId(clientId);
    }

    @Override
    public Facility saveRawFacility(FacilityCommand command) {
        Facility facility = toFacility.convert(command);

        if (facility instanceof Apartment) {
            Apartment apartment = (Apartment) facility;
            return apartmentRepository.save(apartment);
        }

        if (facility instanceof Basement) {
            Basement basement = (Basement) facility;
            return basementRepository.save(basement);
        }

        if (facility instanceof Garage) {
            Garage garage = (Garage) facility;
            return garageRepository.save(garage);
        }

        if (facility instanceof House) {
            House house = (House) facility;
            return houseRepository.save(house);
        }

        if (facility instanceof Storage) {
            Storage storage = (Storage) facility;
            return storageRepository.save(storage);
        }

        return null;
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
