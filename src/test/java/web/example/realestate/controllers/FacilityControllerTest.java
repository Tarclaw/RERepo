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
import web.example.realestate.domain.building.Facility;
import web.example.realestate.services.FacilityService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

class FacilityControllerTest {

    private FacilityController controller;

    private MockMvc mockMvc;

    @Mock
    private FacilityService service;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new FacilityController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getFacilityById() throws Exception {
        //given
        when(service.getById(anyLong())).thenReturn(new Facility());
        ArgumentCaptor<Facility> argumentCaptor = ArgumentCaptor.forClass(Facility.class);

        //when
        String viewName = controller.getFacilityById("1", model);

        //then
        assertEquals("facility/show", viewName);
        verify(service, times(1)).getById(anyLong());
        verify(model, times(1)).addAttribute(eq("facility"), argumentCaptor.capture());

        mockMvc.perform(get("/facility/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("facility/show"))
                .andExpect(model().attributeExists("facility"));
    }

    @Test
    void getAllFacilities() throws Exception {
        //given
        List<Facility> facilities = new ArrayList<>(
                Collections.singletonList(new Facility())
        );

        when(service.getFacilities()).thenReturn(facilities);

        ArgumentCaptor<List<Facility>> argumentCaptor = ArgumentCaptor.forClass(List.class);

        //when
        String viewName = controller.getAllFacilities(model);

        //then
        assertEquals("facility", viewName);
        verify(service, times(1)).getFacilities();
        verify(model, times(1)).addAttribute(eq("facilities"), argumentCaptor.capture());

        List<Facility> listInController = argumentCaptor.getValue();
        assertEquals(1, listInController.size());

        mockMvc.perform(get("/facilities"))
                .andExpect(status().isOk())
                .andExpect(view().name("facility"));
    }

    @Test
    void newFacility() throws Exception {
        assertEquals("facility/facilityForm", controller.newFacility(model));
        mockMvc.perform(get("/facility/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("facility/facilityForm"))
                .andExpect(model().attributeExists("facility"));
    }

    @Test
    void updateFacility() throws Exception {
        //given
        when(service.findCommandById(anyLong())).thenReturn(new FacilityCommand());
        ArgumentCaptor<FacilityCommand> argumentCaptor = ArgumentCaptor.forClass(FacilityCommand.class);

        //when
        String viewName = controller.updateFacility("1", model);

        //then
        assertEquals("facility/facilityForm", viewName);
        verify(service, times(1)).findCommandById(anyLong());
        verify(model, times(1)).addAttribute(eq("facility"), argumentCaptor.capture());

        mockMvc.perform(get("/facility/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("facility/facilityForm"))
                .andExpect(model().attributeExists("facility"));
    }

    @Test
    void saveOrUpdate() throws Exception {
        //given
        FacilityCommand sourceCommand = new FacilityCommand();
        sourceCommand.setId(1L);
        when(service.saveFacilityCommand(any())).thenReturn(sourceCommand);

        //when
        String viewName = controller.saveOrUpdate(sourceCommand);

        //then
        assertEquals("redirect:/facility/1/show", viewName);
        verify(service, times(1)).saveFacilityCommand(any());

        mockMvc.perform(post("/facility")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
        ).andExpect(status().is3xxRedirection())
         .andExpect(view().name("redirect:/facility/1/show"));
    }

    @Test
    void deleteById() throws Exception {
        String viewName = controller.deleteById("1");
        assertEquals("redirect:/facilities", viewName);
        verify(service, times(1)).deleteById(anyLong());

        mockMvc.perform(get("/facility/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/facilities"));
    }
}