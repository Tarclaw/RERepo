package web.example.realestate.services.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import web.example.realestate.converters.FacilityCommandToFacility;
import web.example.realestate.domain.building.Facility;
import web.example.realestate.domain.people.Client;
import web.example.realestate.repositories.*;
import web.example.realestate.services.FacilityService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FacilityServiceImplTest {

    private static final Long CLIENT_ID = 1L;

    private FacilityService service;

    @Mock
    private FacilityRepository facilityRepository;

    @Mock
    private FacilityCommandToFacility toFacility;

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
        service = new FacilityServiceImpl(facilityRepository, toFacility, apartmentRepository,
                basementRepository, garageRepository, houseRepository, storageRepository);
    }

    @Test
    void getFacilities() {
        //given
        when(service.getFacilities()).thenReturn(Collections.singletonList(new Facility()));

        //when
        List<Facility> facilities = service.getFacilities();

        //then
        assertEquals(1, facilities.size());
        verify(facilityRepository, times(1)).findAll();
    }

    @Test
    void getFacilitiesByClientId() {
        //given
        Client client = new Client();
        client.setId(CLIENT_ID);
        Facility facility = new Facility();
        facility.setClient(client);

        when(service.getFacilitiesByIds(CLIENT_ID)).thenReturn(Collections.singletonList(facility));

        //when
        List<Facility> facilities = service.getFacilitiesByIds(CLIENT_ID);
        Client clientFromFacilityList = facilities.get(0).getClient();

        //then
        assertEquals(1, facilities.size());
        assertEquals(CLIENT_ID, clientFromFacilityList.getId());
        verify(facilityRepository, times(1)).findFacilitiesByClientId(CLIENT_ID);
    }

    @Test
    void deleteById() {
        service.deleteById(1L);
        verify(facilityRepository, times(1)).deleteById(anyLong());
    }
}
