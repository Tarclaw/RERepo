package web.example.realestate.services.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.converters.BasementCommandToBasement;
import web.example.realestate.converters.BasementToBasementCommand;
import web.example.realestate.domain.building.Basement;
import web.example.realestate.repositories.BasementRepository;
import web.example.realestate.repositories.ClientRepository;
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
    private BasementRepository basementRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private BasementCommandToBasement toBasement;

    @Mock
    private BasementToBasementCommand toBasementCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new BasementServiceImpl(basementRepository, clientRepository, toBasement, toBasementCommand);
    }

    @Test
    void getById() {
        //given
        Basement source = new Basement();
        source.setId(1L);
        when(basementRepository.findBasementByIdWithClients(anyLong())).thenReturn(Optional.of(source));

        //when
        Basement basement = service.getById(1L);

        //then
        assertNotNull(basement);
        assertEquals(1, basement.getId());
        verify(basementRepository, times(1)).findBasementByIdWithClients(anyLong());
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
        verify(basementRepository, times(1)).findAll();
    }

    @Test
    void findCommandById() {
        //given
        when(basementRepository.findBasementByIdWithClients(anyLong())).thenReturn(Optional.of(new Basement()));

        FacilityCommand sourceCommand = new FacilityCommand();
        sourceCommand.setId(1L);
        when(toBasementCommand.convert(any())).thenReturn(sourceCommand);

        //when
        FacilityCommand command = service.findCommandById(1L);

        //then
        assertNotNull(command);
        assertEquals(1L, command.getId());
        verify(basementRepository, times(1)).findBasementByIdWithClients(anyLong());
    }

    @Test
    void deleteById() {
        service.deleteById(anyLong());
        verify(basementRepository, times(1)).deleteById(anyLong());
    }
}