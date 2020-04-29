package web.example.realestate.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import web.example.realestate.domain.people.RealEstateAgent;
import web.example.realestate.services.RealEstateAgentService;

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

class RealEstateAgentControllerTest {

    private RealEstateAgentController controller;

    @Mock
    private RealEstateAgentService service;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new RealEstateAgentController(service);
    }

    @Test
    void testMockMvc() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(get("/realEstateAgents"))
                .andExpect(status().isOk())
                .andExpect(view().name("realEstateAgent"));
    }

    @Test
    void getAllRealEstateAgents() {
        //given
        Set<RealEstateAgent> agents = new HashSet<>(
                Collections.singletonList(new RealEstateAgent())
        );

        when(service.getRealEstateAgents()).thenReturn(agents);

        ArgumentCaptor<Set<RealEstateAgent>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.getAllRealEstateAgents(model);

        //then
        assertEquals("realEstateAgent", viewName);
        verify(service, times(1)).getRealEstateAgents();
        verify(model, times(1)).addAttribute(eq("realEstateAgents"), argumentCaptor.capture());

        Set<RealEstateAgent> setInController = argumentCaptor.getValue();
        assertEquals(1, setInController.size());
    }
}