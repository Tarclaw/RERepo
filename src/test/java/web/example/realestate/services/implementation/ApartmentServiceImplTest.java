package web.example.realestate.services.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import web.example.realestate.domain.building.Apartment;
import web.example.realestate.repositories.ApartmentRepository;
import web.example.realestate.services.ApartmentService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ApartmentServiceImplTest {

    private ApartmentService service;

    @Mock
    private ApartmentRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new ApartmentServiceImpl(repository);
    }

    @Test
    void getApartments() {
        when(service.getApartments())
                .thenReturn(new HashSet<>(Collections.singletonList(new Apartment())));

        Set<Apartment> apartments = service.getApartments();

        assertEquals(1, apartments.size());
        verify(repository, times(1)).findAll();
    }
}