package web.example.realestate.services.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import web.example.realestate.domain.building.Garage;
import web.example.realestate.repositories.GarageRepository;
import web.example.realestate.services.GarageService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GarageServiceImplTest {

    private GarageService service;

    @Mock
    private GarageRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new GarageServiceImpl(repository);
    }

    @Test
    void getGarages() {
        when(service.getGarages()).
                thenReturn(new HashSet<>(Collections.singletonList(new Garage())));

        Set<Garage> garages = service.getGarages();

        assertEquals(1, garages.size());
        verify(repository, times(1)).findAll();
    }
}