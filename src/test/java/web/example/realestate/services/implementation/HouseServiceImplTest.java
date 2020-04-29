package web.example.realestate.services.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import web.example.realestate.domain.building.House;
import web.example.realestate.repositories.HouseRepository;
import web.example.realestate.services.HouseService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class HouseServiceImplTest {

    private HouseService service;

    @Mock
    private HouseRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new HouseServiceImpl(repository);
    }

    @Test
    void getHouses() {
        when(service.getHouses()).
                thenReturn(new HashSet<>(Collections.singletonList(new House())));

        Set<House> houses = service.getHouses();

        assertEquals(1, houses.size());
        verify(repository, times(1)).findAll();
    }
}