package web.example.realestate.services.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import web.example.realestate.commands.RealEstateAgentCommand;
import web.example.realestate.converters.RealEstateAgentCommandToRealEstateAgent;
import web.example.realestate.converters.RealEstateAgentToRealEstateAgentCommand;
import web.example.realestate.domain.people.RealEstateAgent;
import web.example.realestate.exceptions.NotFoundException;
import web.example.realestate.repositories.ClientRepository;
import web.example.realestate.repositories.RealEstateAgentRepository;
import web.example.realestate.services.RealEstateAgentService;

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

class RealEstateAgentServiceImplTest {

    private RealEstateAgentService service;

    @Mock
    private RealEstateAgentRepository agentRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private RealEstateAgentCommandToRealEstateAgent toAgent;

    @Mock
    private RealEstateAgentToRealEstateAgentCommand toAgentCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new RealEstateAgentServiceImpl(agentRepository, clientRepository, toAgent, toAgentCommand);
    }

    @Test
    void getById() {
        //given
        RealEstateAgent source = new RealEstateAgent();
        source.setId(1L);
        when(agentRepository.findRealEstateAgentsByIdWithEntities(anyLong())).thenReturn(Optional.of(source));

        //when
        RealEstateAgent agent = service.getById(1L);

        //then
        assertNotNull(agent);
        assertEquals(source.getId(), agent.getId());
        verify(agentRepository, times(1)).findRealEstateAgentsByIdWithEntities(anyLong());
    }

    @Test
    void getByIdExceptionHandling() {
        assertThrows(NotFoundException.class, () -> service.getById(anyLong()));
    }

    @Test
    void getRealEstateAgents() {
        when(service.getRealEstateAgents()).
                thenReturn(new HashSet<>(Collections.singletonList(new RealEstateAgent())));

        Set<RealEstateAgent> agents = service.getRealEstateAgents();

        assertEquals(1, agents.size());
        verify(agentRepository, times(1)).findAll();
    }

    @Test
    void findCommandById() {
        //given
        RealEstateAgentCommand source = new RealEstateAgentCommand();
        source.setId(1L);
        when(agentRepository.findRealEstateAgentsByIdWithEntities(anyLong())).thenReturn(Optional.of(new RealEstateAgent()));
        when(toAgentCommand.convert(any())).thenReturn(source);

        //when
        RealEstateAgentCommand command = service.findCommandById(1L);

        //then
        assertNotNull(command);
        assertEquals(source.getId(), command.getId());
        verify(agentRepository, times(1)).findRealEstateAgentsByIdWithEntities(anyLong());
        verify(toAgentCommand, times(1)).convert(any());
    }

    @Test
    void deleteById() {
        service.deleteById(anyLong());
        verify(agentRepository, times(1)).deleteById(anyLong());
    }


}