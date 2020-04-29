package web.example.realestate.services.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import web.example.realestate.domain.people.Client;
import web.example.realestate.repositories.ClientRepository;
import web.example.realestate.services.ClientService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ClientServiceImplTest {

    private ClientService service;

    @Mock
    private ClientRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new ClientServiceImpl(repository);
    }

    @Test
    void getClients() {
        when(service.getClients()).
                thenReturn(new HashSet<>(Collections.singletonList(new Client())));

        Set<Client> clients = service.getClients();

        assertEquals(1, clients.size());
        verify(repository, times(1)).findAll();

    }
}