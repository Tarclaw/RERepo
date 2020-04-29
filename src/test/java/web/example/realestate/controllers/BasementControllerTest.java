package web.example.realestate.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import web.example.realestate.domain.building.Basement;
import web.example.realestate.services.BasementService;

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

class BasementControllerTest {

    private BasementController controller;

    @Mock
    private BasementService service;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new BasementController(service);
    }

    @Test
    void testMockMvc() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(get("/basements"))
                .andExpect(status().isOk())
                .andExpect(view().name("basement"));
    }

    @Test
    void getAllBasements() {
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
    }
}