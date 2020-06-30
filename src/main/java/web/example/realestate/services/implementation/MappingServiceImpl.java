package web.example.realestate.services.implementation;

import org.springframework.stereotype.Service;
import web.example.realestate.domain.building.Facility;
import web.example.realestate.repositories.*;
import web.example.realestate.services.MappingService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MappingServiceImpl implements MappingService {

    private static final String APARTMENT = "/apartment";
    private static final String BASEMENT = "/basement";
    private static final String GARAGE = "/garage";
    private static final String HOUSE = "/house";
    private static final String STORAGE = "/storage";

    private final ApartmentRepository apartmentRepository;
    private final BasementRepository basementRepository;
    private final GarageRepository garageRepository;
    private final HouseRepository houseRepository;
    private final StorageRepository storageRepository;

    public MappingServiceImpl(ApartmentRepository apartmentRepository, BasementRepository basementRepository,
                              GarageRepository garageRepository, HouseRepository houseRepository,
                              StorageRepository storageRepository) {
        this.apartmentRepository = apartmentRepository;
        this.basementRepository = basementRepository;
        this.garageRepository = garageRepository;
        this.houseRepository = houseRepository;
        this.storageRepository = storageRepository;
    }

    @Override
    public Map<Long, String> buildMapping(final List<Facility> facilities) {
        Map<Long, String> mappings = new HashMap<>();

        for (Facility facility : facilities) {
            Long id = facility.getId();
            if (apartmentRepository.existsById(id)) {
                mappings.put(id, APARTMENT);
            }
            if (basementRepository.existsById(id)) {
                mappings.put(id, BASEMENT);
            }
            if (garageRepository.existsById(id)) {
                mappings.put(id, GARAGE);
            }
            if (houseRepository.existsById(id)) {
                mappings.put(id, HOUSE);
            }
            if (storageRepository.existsById(id)) {
                mappings.put(id, STORAGE);
            }
        }

        return mappings;
    }

}
