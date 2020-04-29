package web.example.realestate.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import web.example.realestate.domain.building.Facility;
import web.example.realestate.services.FacilityService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class FacilityControllerTest {

    private FacilityController controller;

    @Mock
    private FacilityService service;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new FacilityController(service);
    }

    @Test
    void testMockMvc() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(get("/facilities"))
                .andExpect(status().isOk())
                .andExpect(view().name("facility"));
    }

    @Test
    void getAllFacilities() {
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
    }
}