package web.example.realestate.services.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import web.example.realestate.domain.people.RealEstateAgent;
import web.example.realestate.repositories.RealEstateAgentRepository;
import web.example.realestate.services.RealEstateAgentService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RealEstateAgentServiceImplTest {

    private RealEstateAgentService service;

    @Mock
    private RealEstateAgentRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new RealEstateAgentServiceImpl(repository);
    }

    @Test
    void getRealEstateAgents() {
        when(service.getRealEstateAgents()).
                thenReturn(new HashSet<>(Collections.singletonList(new RealEstateAgent())));

        Set<RealEstateAgent> agents = service.getRealEstateAgents();

        assertEquals(1, agents.size());
        verify(repository, times(1)).findAll();
    }
}