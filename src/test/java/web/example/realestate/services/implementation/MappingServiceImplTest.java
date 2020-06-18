package web.example.realestate.services.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import web.example.realestate.domain.building.*;
import web.example.realestate.repositories.*;
import web.example.realestate.services.MappingService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MappingServiceImplTest {

    private MappingService service;

    private List<Facility> facilities;

    private Map<Long, String> mappings;

    @Mock
    private ApartmentRepository apartmentRepository;
    @Mock
    private BasementRepository basementRepository;
    @Mock
    private GarageRepository garageRepository;
    @Mock
    private HouseRepository houseRepository;
    @Mock
    private StorageRepository storageRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new MappingServiceImpl(apartmentRepository, basementRepository,
                                         garageRepository, houseRepository, storageRepository);
        initList();
        initMap();
    }

    private void initList() {
        facilities = new ArrayList<>();

        Apartment apartment = new Apartment();
        apartment.setId(1L);
        facilities.add(apartment);

        Basement basement = new Basement();
        basement.setId(2L);
        facilities.add(basement);

        Garage garage = new Garage();
        garage.setId(3L);
        facilities.add(garage);

        House house = new House();
        house.setId(4L);
        facilities.add(house);

        Storage storage = new Storage();
        storage.setId(5L);
        facilities.add(storage);
    }

    private void initMap() {
        mappings = new HashMap<>();
        mappings.put(1L, "/apartment");
        mappings.put(2L, "/basement");
        mappings.put(3L, "/garage");
        mappings.put(4L, "/house");
        mappings.put(5L, "/storage");
    }

    @Test
    void buildMapping() {
        //given
        when(apartmentRepository.existsById(1L)).thenReturn(true);
        when(apartmentRepository.existsById(2L)).thenReturn(false);
        when(apartmentRepository.existsById(3L)).thenReturn(false);
        when(apartmentRepository.existsById(4L)).thenReturn(false);
        when(apartmentRepository.existsById(5L)).thenReturn(false);

        when(basementRepository.existsById(1L)).thenReturn(false);
        when(basementRepository.existsById(2L)).thenReturn(true);
        when(basementRepository.existsById(3L)).thenReturn(false);
        when(basementRepository.existsById(4L)).thenReturn(false);
        when(basementRepository.existsById(5L)).thenReturn(false);

        when(garageRepository.existsById(1L)).thenReturn(false);
        when(garageRepository.existsById(2L)).thenReturn(false);
        when(garageRepository.existsById(3L)).thenReturn(true);
        when(garageRepository.existsById(4L)).thenReturn(false);
        when(garageRepository.existsById(5L)).thenReturn(false);

        when(houseRepository.existsById(1L)).thenReturn(false);
        when(houseRepository.existsById(2L)).thenReturn(false);
        when(houseRepository.existsById(3L)).thenReturn(false);
        when(houseRepository.existsById(4L)).thenReturn(true);
        when(houseRepository.existsById(5L)).thenReturn(false);

        when(storageRepository.existsById(1L)).thenReturn(false);
        when(storageRepository.existsById(2L)).thenReturn(false);
        when(storageRepository.existsById(3L)).thenReturn(false);
        when(storageRepository.existsById(4L)).thenReturn(false);
        when(storageRepository.existsById(5L)).thenReturn(true);

        //when
        Map<Long, String> mappingsFromService = service.buildMapping(facilities);

        //then
        assertEquals(mappings, mappingsFromService);
        verify(apartmentRepository, times(5)).existsById(anyLong());
        verify(basementRepository, times(5)).existsById(anyLong());
        verify(garageRepository, times(5)).existsById(anyLong());
        verify(houseRepository, times(5)).existsById(anyLong());
        verify(storageRepository, times(5)).existsById(anyLong());
    }
}