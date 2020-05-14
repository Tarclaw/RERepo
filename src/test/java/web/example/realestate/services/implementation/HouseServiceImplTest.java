package web.example.realestate.services.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.converters.HouseCommandToHouse;
import web.example.realestate.converters.HouseToHouseCommand;
import web.example.realestate.domain.building.House;
import web.example.realestate.repositories.HouseRepository;
import web.example.realestate.services.HouseService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class HouseServiceImplTest {

    private HouseService service;

    @Mock
    private HouseRepository repository;

    @Mock
    private HouseCommandToHouse toHouse;

    @Mock
    private HouseToHouseCommand toHouseCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new HouseServiceImpl(repository, toHouse, toHouseCommand);
    }

    @Test
    void getById() {
        //given
        House source = new House();
        source.setId(1L);
        when(repository.findById(anyLong())).thenReturn(Optional.of(source));

        //when
        House house = service.getById(1L);

        //then
        assertNotNull(house);
        assertEquals(1L, house.getId());
        verify(repository, times(1)).findById(anyLong());

    }

    @Test
    void getByIdExceptionHandling() {
        assertThrows(RuntimeException.class, () -> service.getById(anyLong()));
    }

    @Test
    void getHouses() {
        when(service.getHouses()).
                thenReturn(new HashSet<>(Collections.singletonList(new House())));

        Set<House> houses = service.getHouses();

        assertEquals(1, houses.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void findCommandById() {
        //given
        when(repository.findById(anyLong())).thenReturn(Optional.of(new House()));
        FacilityCommand source = new FacilityCommand();
        source.setId(1L);
        when(toHouseCommand.convert(any())).thenReturn(source);

        //when
        FacilityCommand command = service.findCommandById(1L);

        //then
        assertNotNull(command);
        assertEquals(1L, command.getId());
        verify(repository, times(1)).findById(anyLong());
        verify(toHouseCommand, times(1)).convert(any());
    }

    @Test
    void deleteById() {
        service.deleteById(anyLong());
        verify(repository, times(1)).deleteById(anyLong());
    }
}