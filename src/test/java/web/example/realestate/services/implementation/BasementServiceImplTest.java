package web.example.realestate.services.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import web.example.realestate.commands.BasementCommand;
import web.example.realestate.converters.BasementCommandToBasement;
import web.example.realestate.converters.BasementToBasementCommand;
import web.example.realestate.domain.building.Basement;
import web.example.realestate.repositories.BasementRepository;
import web.example.realestate.services.BasementService;

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

class BasementServiceImplTest {

    private BasementService service;

    @Mock
    private BasementRepository repository;

    @Mock
    private BasementCommandToBasement toBasement;

    @Mock
    private BasementToBasementCommand toBasementCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new BasementServiceImpl(repository, toBasement, toBasementCommand);
    }

    @Test
    void getById() {
        //given
        Basement source = new Basement();
        source.setId(1L);
        when(repository.findById(anyLong())).thenReturn(Optional.of(source));

        //when
        Basement basement = service.getById(1L);

        //then
        assertNotNull(basement);
        assertEquals(1, basement.getId());
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void getByIdThrowException() {
        assertThrows(RuntimeException.class, () -> service.getById(anyLong()));
    }

    @Test
    void getBasements() {
        //given
        when(service.getBasements())
                .thenReturn(new HashSet<>(Collections.singletonList(new Basement())));

        //when
        Set<Basement> basements = service.getBasements();

        //then
        assertEquals(1, basements.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void findCommandById() {
        //given
        when(repository.findById(anyLong())).thenReturn(Optional.of(new Basement()));

        BasementCommand sourceCommand = new BasementCommand();
        sourceCommand.setId(1L);
        when(toBasementCommand.convert(any())).thenReturn(sourceCommand);

        //when
        BasementCommand command = service.findCommandById(1L);

        //then
        assertNotNull(command);
        assertEquals(1L, command.getId());
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void deleteById() {
        service.deleteById(anyLong());
        verify(repository, times(1)).deleteById(anyLong());
    }
}