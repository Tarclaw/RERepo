package web.example.realestate.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import web.example.realestate.domain.people.Client;
import web.example.realestate.services.ClientService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class ClientControllerTest {

    private ClientController controller;

    @Mock
    private ClientService service;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new ClientController(service);
    }

    @Test
    void testMockMvc() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(get("/clients"))
                .andExpect(status().isOk())
                .andExpect(view().name("client"));
    }

    @Test
    void getAllClients() {
        //given
        Set<Client> clients = new HashSet<>(
                Collections.singletonList(new Client())
        );
        when(service.getClients()).thenReturn(clients);

        ArgumentCaptor<Set<Client>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.getAllClients(model);

        //then
        assertEquals("client", viewName);
        verify(service, times(1)).getClients();
        verify(model, times(1)).addAttribute(eq("clients"), argumentCaptor.capture());

        Set<Client> setInController = argumentCaptor.getValue();
        assertEquals(1, setInController.size());
    }
}