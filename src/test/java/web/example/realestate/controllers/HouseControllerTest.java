package web.example.realestate.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import web.example.realestate.domain.building.House;
import web.example.realestate.services.HouseService;

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

class HouseControllerTest {

    private HouseController controller;

    @Mock
    private HouseService service;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new HouseController(service);
    }

    @Test
    void testMockMvc() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(get("/houses"))
                .andExpect(status().isOk())
                .andExpect(view().name("house"));
    }

    @Test
    void getAllHouses() {
        //given
        Set<House> houses = new HashSet<>(
                Collections.singletonList(new House())
        );

        when(service.getHouses()).thenReturn(houses);

        ArgumentCaptor<Set<House>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.getAllHouses(model);

        //then
        assertEquals("house", viewName);
        verify(service, times(1)).getHouses();
        verify(model, times(1)).addAttribute(eq("houses"), argumentCaptor.capture());

        Set<House> setInController = argumentCaptor.getValue();
        assertEquals(1, setInController.size());
    }
}