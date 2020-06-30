package web.example.realestate.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import web.example.realestate.commands.ClientCommand;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.people.Client;
import web.example.realestate.services.ClientService;
import web.example.realestate.services.RealEstateAgentService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class ClientControllerTest {

    private ClientController controller;

    private MockMvc mockMvc;

    @Mock
    private ClientService clientService;

    @Mock
    private RealEstateAgentService agentService;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new ClientController(clientService, agentService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getClientById() throws Exception {
        //given
        when(clientService.getById(anyLong())).thenReturn(new Client());
        ArgumentCaptor<Client> clientCaptor = ArgumentCaptor.forClass(Client.class);

        //when
        String viewName = controller.getClientById("1", model);

        //then
        assertEquals("client/show", viewName);
        verify(clientService, times(1)).getById(anyLong());
        verify(model, times(1)).addAttribute(eq("client"), clientCaptor.capture());

        mockMvc.perform(get("/client/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("client/show"))
                .andExpect(model().attributeExists("client"));
    }

    @Test
    void getAllClients() throws Exception {
        //given
        Set<Client> clients = new HashSet<>(
                Collections.singletonList(new Client())
        );
        when(clientService.getClients()).thenReturn(clients);

        ArgumentCaptor<Set<Client>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.getAllClients(model);

        //then
        assertEquals("client", viewName);
        verify(clientService, times(1)).getClients();
        verify(model, times(1)).addAttribute(eq("clients"), argumentCaptor.capture());

        Set<Client> setInController = argumentCaptor.getValue();
        assertEquals(1, setInController.size());

        mockMvc.perform(get("/clients"))
                .andExpect(status().isOk())
                .andExpect(view().name("client"));
    }

    @Test
    void newClient() throws Exception {
        //given
        ArgumentCaptor<ClientCommand> commandCaptor = ArgumentCaptor.forClass(ClientCommand.class);

        //when
        String viewName = controller.newClient(model);

        //then
        assertEquals("client/clientEmptyForm", viewName);
        verify(model, times(1)).addAttribute(eq("client"), commandCaptor.capture());

        mockMvc.perform(get("/client/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("client/clientEmptyForm"))
                .andExpect(model().attributeExists("client"));
    }

    @Test
    void newClientForApartment() throws Exception {
        //given
        ArgumentCaptor<ClientCommand> commandCaptor = ArgumentCaptor.forClass(ClientCommand.class);
        ArgumentCaptor<String> mappingCaptor = ArgumentCaptor.forClass(String.class);

        //when
        String viewName = controller.newClientForApartment(model);

        //then
        assertEquals("client/clientEmptyForm", viewName);
        verify(model, times(1)).addAttribute(eq("client"), commandCaptor.capture());
        verify(model, times(1)).addAttribute(eq("mapping"), mappingCaptor.capture());

        mockMvc.perform(get("/apartment/client/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("client/clientEmptyForm"))
                .andExpect(model().attributeExists("client"))
                .andExpect(model().attributeExists("mapping"));
    }


    @Test
    void updateClient() throws Exception {
        //given
        when(clientService.findCommandById(anyLong())).thenReturn(new ClientCommand());
        ArgumentCaptor<ClientCommand> commandCaptor = ArgumentCaptor.forClass(ClientCommand.class);

        //when
        String viewName = controller.updateClient("1", model);

        //then
        assertEquals("client/clientForm", viewName);
        verify(clientService, times(1)).findCommandById(anyLong());
        verify(model, times(1)).addAttribute(eq("client"), commandCaptor.capture());

        mockMvc.perform(get("/client/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("client/clientForm"))
                .andExpect(model().attributeExists("client"));
    }

    @Test
    void save() throws Exception {
        //given
        ClientCommand source = new ClientCommand();
        source.setId(1L);
        when(clientService.saveAttached(any())).thenReturn(source);

        //when
        String viewName = controller.saveOrUpdate(source);

        //then
        assertEquals("redirect:/client/1/show", viewName);
        verify(clientService, times(1)).saveAttached(any());

        mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/client/1/show"));
    }

    @Test
    void saveForApartment() throws Exception {
        //given
        ClientCommand source = new ClientCommand();
        when(clientService.saveAttached(any())).thenReturn(source);

        ArgumentCaptor<FacilityCommand> commandCaptor = ArgumentCaptor.forClass(FacilityCommand.class);
        ArgumentCaptor<Set<Client>> clientsCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.saveForApartment(source, model);

        //then
        assertEquals("apartment/apartmentEmptyForm", viewName);
        verify(clientService, times(1)).saveAttached(any());
        verify(clientService, times(1)).getClients();
        verify(model, times(1)).addAttribute(eq("apartment"), commandCaptor.capture());
        verify(model, times(1)).addAttribute(eq("clients"), clientsCaptor.capture());

        mockMvc.perform(post("/client/apartment")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("apartment/apartmentEmptyForm"));
    }

    @Test
    void deleteById() throws Exception {
        String viewName = controller.deleteById("1");

        assertEquals("redirect:/clients", viewName);
        verify(clientService, times(1)).deleteById(anyLong());

        mockMvc.perform(get("/client/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/clients"));
    }
}
