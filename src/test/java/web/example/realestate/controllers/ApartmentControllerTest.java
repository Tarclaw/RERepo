package web.example.realestate.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import web.example.realestate.domain.building.Apartment;
import web.example.realestate.services.ApartmentService;

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

class ApartmentControllerTest {

    private ApartmentController controller;

    @Mock
    private ApartmentService service;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new ApartmentController(service);
    }

    @Test
    void testMockMvc() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(get("/apartments"))
                .andExpect(status().isOk())
                .andExpect(view().name("apartment"));
    }

    @Test
    void getAllApartments() {
        //given
        Set<Apartment> apartments = new HashSet<>(
                Collections.singletonList(new Apartment())
        );
        when(service.getApartments()).thenReturn(apartments);
        ArgumentCaptor<Set<Apartment>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.getAllApartments(model);

        //then
        assertEquals("apartment", viewName);
        verify(service, times(1)).getApartments();
        verify(model, times(1)).addAttribute(eq("apartments"), argumentCaptor.capture());
        Set<Apartment> setInController = argumentCaptor.getValue();
        assertEquals(1, setInController.size());
    }
}