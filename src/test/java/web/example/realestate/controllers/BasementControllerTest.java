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
import web.example.realestate.domain.building.Basement;
import web.example.realestate.services.BasementService;

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

class BasementControllerTest {

    private BasementController controller;

    private MockMvc mockMvc;

    @Mock
    private BasementService service;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new BasementController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getBasementById() throws Exception {
        //given
        when(service.getById(anyLong())).thenReturn(new Basement());
        ArgumentCaptor<Basement> basementCaptor = ArgumentCaptor.forClass(Basement.class);

        //when
        String viewName = controller.getBasementById("1", model);

        //then
        assertEquals("basements/show", viewName);
        verify(service, times(1)).getById(anyLong());
        verify(model, times(1)).addAttribute(eq("basement"), basementCaptor.capture());

        mockMvc.perform(get("/basements/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("basements/show"))
                .andExpect(model().attributeExists("basement"));
    }

    @Test
    void getAllBasements() throws Exception {
        //given
        Set<Basement> basements = new HashSet<>(
                Collections.singletonList(new Basement())
        );
        when(service.getBasements()).thenReturn(basements);

        ArgumentCaptor<Set<Basement>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.getAllBasements(model);

        //then
        assertEquals("basement", viewName);
        verify(service, times(1)).getBasements();
        verify(model, times(1)).addAttribute(eq("basements"), argumentCaptor.capture());

        Set<Basement> setInController = argumentCaptor.getValue();
        assertEquals(1, setInController.size());

        mockMvc.perform(get("/basements"))
                .andExpect(status().isOk())
                .andExpect(view().name("basement"));
    }

    @Test
    void newBasement() throws Exception {
        String viewName = controller.newBasement(model);
        assertEquals("basement/basementForm", viewName);

        mockMvc.perform(get("/basement/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("basement/basementForm"));
    }

    @Test
    void updateBasement() throws Exception {
        //given
        when(service.findCommandById(anyLong())).thenReturn(new FacilityCommand());
        ArgumentCaptor<FacilityCommand> commandCaptor = ArgumentCaptor.forClass(FacilityCommand.class);

        //when
        String viewName = controller.updateBasement("1", model);

        //then
        assertEquals("basement/basementForm", viewName);
        verify(service, times(1)).findCommandById(anyLong());
        verify(model, times(1)).addAttribute(eq("basement"), commandCaptor.capture());

        mockMvc.perform(get("/basement/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("basement/basementForm"))
                .andExpect(model().attributeExists("basement"));
    }

    @Test
    void saveOrUpdate() throws Exception {
        //given
        FacilityCommand sourceCommand = new FacilityCommand();
        sourceCommand.setId(1L);

        when(service.saveBasementCommand(any())).thenReturn(sourceCommand);
        ArgumentCaptor<FacilityCommand> commandCaptor = ArgumentCaptor.forClass(FacilityCommand.class);

        //when
        String viewName = controller.saveOrUpdate(sourceCommand);

        //then
        assertEquals("redirect:/basement/1/show", viewName);
        verify(service, times(1)).saveBasementCommand(any());

        mockMvc.perform(post("/basement")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/basement/1/show"));

    }

    @Test
    void deleteById() throws Exception {
        String viewName = controller.deleteById("1");
        assertEquals("redirect:/", viewName);
        verify(service, times(1)).deleteById(anyLong());

        mockMvc.perform(get("/basement/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

    }
}