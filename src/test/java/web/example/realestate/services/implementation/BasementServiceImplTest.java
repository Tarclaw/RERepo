package web.example.realestate.services.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import web.example.realestate.domain.building.Basement;
import web.example.realestate.repositories.BasementRepository;
import web.example.realestate.services.BasementService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BasementServiceImplTest {

    private BasementService service;

    @Mock
    private BasementRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new BasementServiceImpl(repository);
    }

    @Test
    void getBasements() {
        when(service.getBasements())
                .thenReturn(new HashSet<>(Collections.singletonList(new Basement())));

        Set<Basement> basements = service.getBasements();

        assertEquals(1, basements.size());
        verify(repository, times(1)).findAll();
    }
}