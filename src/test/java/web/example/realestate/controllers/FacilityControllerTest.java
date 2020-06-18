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
import web.example.realestate.services.MappingService;

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
    private FacilityService facilityService;

    @Mock
    private MappingService mappingService;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new FacilityController(facilityService, mappingService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getAllFacilities() throws Exception {
        //given
        List<Facility> facilities = new ArrayList<>(
                Collections.singletonList(new Facility())
        );

        when(facilityService.getFacilities()).thenReturn(facilities);

        ArgumentCaptor<List<Facility>> argumentCaptor = ArgumentCaptor.forClass(List.class);

        //when
        String viewName = controller.getAllFacilities(model);

        //then
        assertEquals("facility", viewName);
        verify(facilityService, times(1)).getFacilities();
        verify(model, times(1)).addAttribute(eq("facilities"), argumentCaptor.capture());

        List<Facility> listInController = argumentCaptor.getValue();
        assertEquals(1, listInController.size());

        mockMvc.perform(get("/facilities"))
                .andExpect(status().isOk())
                .andExpect(view().name("facility"));
    }
}