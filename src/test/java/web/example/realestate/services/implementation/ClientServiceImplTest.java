package web.example.realestate.services.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import web.example.realestate.commands.ClientCommand;
import web.example.realestate.converters.ClientCommandToClient;
import web.example.realestate.converters.ClientToClientCommand;
import web.example.realestate.domain.building.Facility;
import web.example.realestate.domain.people.Client;
import web.example.realestate.domain.people.RealEstateAgent;
import web.example.realestate.repositories.ClientRepository;
import web.example.realestate.services.ClientService;

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

class ClientServiceImplTest {

    private ClientService service;

    @Mock
    private ClientRepository repository;

    @Mock
    private ClientCommandToClient toClient;

    @Mock
    private ClientToClientCommand toClientCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new ClientServiceImpl(repository, toClient, toClientCommand);
    }

    @Test
    void getById() {
        //given
        Client source = new Client();
        source.setId(1L);
        when(repository.findClientByIdWithFacilitiesAndAgents(anyLong())).thenReturn(Optional.of(source));

        //when
        Client client = service.getById(1L);

        //then
        assertNotNull(client);
        assertEquals(1L, client.getId());
        verify(repository, times(1)).findClientByIdWithFacilitiesAndAgents(anyLong());
    }

    @Test
    void getByIdExceptionHandling() {
        assertThrows(RuntimeException.class, () -> service.getById(anyLong()));
    }

    @Test
    void getClients() {
        when(service.getClients()).
                thenReturn(new HashSet<>(Collections.singletonList(new Client())));

        Set<Client> clients = service.getClients();

        assertEquals(1, clients.size());
        verify(repository, times(1)).findAll();

    }

    @Test
    void findCommandById() {
        //given
        ClientCommand source = new ClientCommand();
        source.setId(1L);
        when(repository.findClientByIdWithFacilitiesAndAgents(anyLong())).thenReturn(Optional.of(new Client()));
        when(toClientCommand.convert(any())).thenReturn(source);

        //when
        ClientCommand command = service.findCommandById(1L);

        //then
        assertNotNull(command);
        assertEquals(1L, command.getId());
        verify(repository, times(1)).findClientByIdWithFacilitiesAndAgents(anyLong());
        verify(toClientCommand, times(1)).convert(any());
    }

    @Test
    void deleteById() {
        //given
        Set<Facility> facilities = new HashSet<>();
        facilities.add(new Facility());

        Client source = new Client();
        source.setFacilities(facilities);

        Set<Client> clients = new HashSet<>();
        clients.add(source);

        RealEstateAgent agent = new RealEstateAgent();
        agent.setClients(clients);

        Set<RealEstateAgent> agents = new HashSet<>();
        agents.add(agent);

        source.setRealEstateAgents(agents);

        when(repository.findClientByIdWithFacilitiesAndAgents(anyLong())).thenReturn(Optional.of(source));

        //when
        service.deleteById(anyLong());

        //then
        verify(repository, times(1)).findClientByIdWithFacilitiesAndAgents(anyLong());
        verify(repository, times(1)).deleteById(anyLong());
    }
}