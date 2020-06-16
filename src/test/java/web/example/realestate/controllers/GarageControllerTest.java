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
import web.example.realestate.domain.building.Garage;
import web.example.realestate.services.GarageService;

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

class GarageControllerTest {

    private GarageController controller;

    private MockMvc mockMvc;

    @Mock
    private GarageService service;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new GarageController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getGarageById() throws Exception {
        //given
        when(service.getById(anyLong())).thenReturn(new Garage());
        ArgumentCaptor<Garage> garageCaptor = ArgumentCaptor.forClass(Garage.class);

        //when
        String viewName = controller.getGarageById("1", model);

        //then
        assertEquals("garage/show", viewName);
        verify(service, times(1)).getById(anyLong());
        verify(model, times(1)).addAttribute(eq("garage"), garageCaptor.capture());

        mockMvc.perform(get("/garage/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("garage/show"))
                .andExpect(model().attributeExists("garage"));
    }

    @Test
    void getAllGarages() throws Exception {
        //given
        Set<Garage> garages = new HashSet<>(
                Collections.singletonList(new Garage())
        );

        when(service.getGarages()).thenReturn(garages);

        ArgumentCaptor<Set<Garage>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.getAllGarages(model);

        //then
        assertEquals("garage", viewName);
        verify(service, times(1)).getGarages();
        verify(model, times(1)).addAttribute(eq("garages"), argumentCaptor.capture());

        Set<Garage> setInController = argumentCaptor.getValue();
        assertEquals(1, setInController.size());

        mockMvc.perform(get("/garages"))
                .andExpect(status().isOk())
                .andExpect(view().name("garage"));
    }

    @Test
    void newGarage() throws Exception {
        //given
        ArgumentCaptor<FacilityCommand> commandCaptor = ArgumentCaptor.forClass(FacilityCommand.class);

        //when
        String viewName = controller.newGarage(model);

        //then
        assertEquals("garage/garageForm", viewName);
        verify(model, times(1)).addAttribute(eq("garage"), commandCaptor.capture());

        mockMvc.perform(get("/garage/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("garage/garageForm"))
                .andExpect(model().attributeExists("garage"));
    }

    @Test
    void updateGarage() throws Exception {
        //given
        when(service.findCommandById(anyLong())).thenReturn(new FacilityCommand());
        ArgumentCaptor<FacilityCommand> commandCaptor = ArgumentCaptor.forClass(FacilityCommand.class);

        //when
        String viewName = controller.updateGarage("1", model);

        //then
        assertEquals("garage/garageForm", viewName);
        verify(service, times(1)).findCommandById(anyLong());
        verify(model, times(1)).addAttribute(eq("garage"), commandCaptor.capture());

        mockMvc.perform(get("/garage/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("garage/garageForm"))
                .andExpect(model().attributeExists("garage"));
    }

    @Test
    void saveOrUpdate() throws Exception {
        //given
        FacilityCommand source = new FacilityCommand();
        source.setId(1L);
        when(service.saveGarageCommand(any())).thenReturn(source);

        //when
        String viewName = controller.saveOrUpdate(source);

        //then
        assertEquals("redirect:/garage/1/show", viewName);
        verify(service, times(1)).saveGarageCommand(any());

        mockMvc.perform(post("/garage")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/garage/1/show"));
    }

    @Test
    void deleteById() throws Exception {
        String viewName = controller.deleteById("1");
        assertEquals("redirect:/garages", viewName);
        verify(service, times(1)).deleteById(anyLong());

        mockMvc.perform(get("/garage/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/garages"));
    }
}