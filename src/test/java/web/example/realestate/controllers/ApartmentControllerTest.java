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
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Apartment;
import web.example.realestate.domain.people.Client;
import web.example.realestate.services.ApartmentService;
import web.example.realestate.services.ClientService;

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

class ApartmentControllerTest {

    private ApartmentController controller;

    private MockMvc mockMvc;

    @Mock
    private ApartmentService apartmentService;

    @Mock
    private ClientService clientService;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new ApartmentController(apartmentService, clientService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getApartmentById() throws Exception {
        //given
        when(apartmentService.getById(anyLong())).thenReturn(new Apartment());
        ArgumentCaptor<Apartment> argumentCaptor = ArgumentCaptor.forClass(Apartment.class);

        //when
        String viewName = controller.getApartmentById("1", model);

        //then
        assertEquals("apartment/show", viewName);
        verify(apartmentService, times(1)).getById(anyLong());
        verify(model, times(1)).addAttribute(eq("apartment"), argumentCaptor.capture());

        mockMvc.perform(get("/apartment/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("apartment/show"))
                .andExpect(model().attributeExists("apartment"));
    }

    @Test
    void getAllApartments() throws Exception {
        //given
        Set<Apartment> apartments = new HashSet<>(
                Collections.singletonList(new Apartment())
        );
        when(apartmentService.getApartments()).thenReturn(apartments);
        ArgumentCaptor<Set<Apartment>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.getAllApartments(model);

        //then
        assertEquals("apartments", viewName);
        verify(apartmentService, times(1)).getApartments();
        verify(model, times(1)).addAttribute(eq("apartments"), argumentCaptor.capture());
        Set<Apartment> setInController = argumentCaptor.getValue();
        assertEquals(1, setInController.size());

        mockMvc.perform(get("/apartment"))
                .andExpect(status().isOk())
                .andExpect(view().name("apartments"))
                .andExpect(model().attributeExists("apartments"));
    }

    @Test
    void newApartment() throws Exception {
        //given
        when(clientService.getClients()).thenReturn(
                new HashSet<>(
                        Collections.singletonList(new Client())
                )
        );

        //when
        String viewName = controller.newApartment(model);

        //then
        assertEquals("apartment/apartmentEmptyForm", viewName);
        mockMvc.perform(get("/apartment/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("apartment/apartmentEmptyForm"))
                .andExpect(model().attributeExists("apartment"))
                .andExpect(model().attributeExists("clients"));
    }

    @Test
    void updateApartment() throws Exception {
        //given
        Set<Client> clients = new HashSet<>(
                Collections.singletonList(new Client())
        );
        when(apartmentService.findCommandById(anyLong())).thenReturn(new FacilityCommand());
        when(clientService.getClients()).thenReturn(clients);
        ArgumentCaptor<FacilityCommand> argumentCaptor = ArgumentCaptor.forClass(FacilityCommand.class);
        ArgumentCaptor<Set<Client>> clientsCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.updateApartment("1", model);

        //then
        assertEquals("apartment/apartmentForm", viewName);
        verify(apartmentService, times(1)).findCommandById(anyLong());
        verify(clientService, times(1)).getClients();
        verify(model, times(1)).addAttribute(eq("apartment"), argumentCaptor.capture());
        verify(model, times(1)).addAttribute(eq("clients"), clientsCaptor.capture());

        mockMvc.perform(get("/apartment/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("apartment/apartmentForm"))
                .andExpect(model().attributeExists("apartment"))
                .andExpect(model().attributeExists("clients"));

    }

    @Test
    void saveNew() throws Exception {
        //given
        FacilityCommand source = new FacilityCommand();
        source.setId(1L);
        when(apartmentService.saveDetached(any())).thenReturn(source);

        //when
        String viewName = controller.saveNew(source);

        //then
        assertEquals("redirect:/apartment/1/show", viewName);
        verify(apartmentService, times(1)).saveDetached(any());

        mockMvc.perform(post("/apartment/save")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/apartment/1/show"));
    }

    @Test
    void updateExisting() throws Exception {
        //given
        FacilityCommand source = new FacilityCommand();
        source.setId(1L);
        when(apartmentService.saveAttached(any())).thenReturn(source);

        //when
        String viewName = controller.updateExisting(source);

        //then
        assertEquals("redirect:/apartment/1/show", viewName);
        verify(apartmentService, times(1)).saveAttached(any());

        mockMvc.perform(post("/apartment/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/apartment/1/show"));
    }

    @Test
    void deleteById() throws Exception {
        String viewName = controller.deleteById("1");
        assertEquals("redirect:/apartment", viewName);
        verify(apartmentService, times(1)).deleteById(anyLong());

        mockMvc.perform(get("/apartment/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/apartment"));
    }
}