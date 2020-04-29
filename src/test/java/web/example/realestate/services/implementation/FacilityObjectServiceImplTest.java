package web.example.realestate.services.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import web.example.realestate.domain.building.FacilityObject;
import web.example.realestate.repositories.FacilityObjectRepository;
import web.example.realestate.services.FacilityObjectService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FacilityObjectServiceImplTest {

    private FacilityObjectService service;

    @Mock
    private FacilityObjectRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new FacilityObjectServiceImpl(repository);
    }

    @Test
    void getFacilityObjects() {
        when(service.getFacilityObjects()).
                thenReturn(new HashSet<>(Collections.singletonList(new FacilityObject())));

        Set<FacilityObject> facilityObjects = service.getFacilityObjects();

        assertEquals(1, facilityObjects.size());
        verify(repository, times(1)).findAll();
    }
}