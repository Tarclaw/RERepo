package web.example.realestate.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import web.example.realestate.domain.building.Garage;
import web.example.realestate.services.GarageService;

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

class GarageControllerTest {

    private GarageController controller;

    @Mock
    private GarageService service;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new GarageController(service);
    }

    @Test
    void testMockMvc() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(get("/garages"))
                .andExpect(status().isOk())
                .andExpect(view().name("garage"));
    }

    @Test
    void getAllGarages() {
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
    }
}