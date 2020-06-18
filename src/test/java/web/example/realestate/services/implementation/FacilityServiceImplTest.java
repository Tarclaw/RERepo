package web.example.realestate.services.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.converters.FacilityCommandToFacility;
import web.example.realestate.converters.FacilityToFacilityCommand;
import web.example.realestate.domain.building.Facility;
import web.example.realestate.repositories.FacilityRepository;
import web.example.realestate.services.FacilityService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FacilityServiceImplTest {

    private FacilityService service;

    @Mock
    private FacilityRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new FacilityServiceImpl(repository);
    }

    @Test
    void getFacilities() {
        //given
        when(service.getFacilities()).thenReturn(Collections.singletonList(new Facility()));

        //when
        List<Facility> facilities = service.getFacilities();

        //then
        assertEquals(1, facilities.size());
        verify(repository, times(1)).findAll();
    }
}