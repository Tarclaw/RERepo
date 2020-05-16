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
import web.example.realestate.commands.RealEstateAgentCommand;
import web.example.realestate.domain.people.RealEstateAgent;
import web.example.realestate.services.RealEstateAgentService;

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

class RealEstateAgentControllerTest {

    private RealEstateAgentController controller;

    private MockMvc mockMvc;

    @Mock
    private RealEstateAgentService service;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new RealEstateAgentController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getRealEstateAgentById() throws Exception {
        //given
        when(service.getById(anyLong())).thenReturn(new RealEstateAgent());
        ArgumentCaptor<RealEstateAgent> agentCaptor = ArgumentCaptor.forClass(RealEstateAgent.class);

        //when
        String viewName = controller.getRealEstateAgentById("1", model);

        //then
        assertEquals("realEstateAgent/show", viewName);
        verify(service, times(1)).getById(anyLong());
        verify(model, times(1)).addAttribute(eq("realEstateAgent"), agentCaptor.capture());

        mockMvc.perform(get("/realEstateAgent/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("realEstateAgent/show"))
                .andExpect(model().attributeExists("realEstateAgent"));
    }

    @Test
    void getAllRealEstateAgents() throws Exception {
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

        mockMvc.perform(get("/realEstateAgents"))
                .andExpect(status().isOk())
                .andExpect(view().name("realEstateAgent"));
    }

    @Test
    void saveOrUpdate() throws Exception {
        //given
        RealEstateAgentCommand source = new RealEstateAgentCommand();
        source.setId(1L);
        when(service.saveRealEstateAgentCommand(any())).thenReturn(source);

        //when
        String viewName = controller.saveOrUpdate(source);

        //then
        assertEquals("redirect:/realEstateAgent/1/show", viewName);
        verify(service, times(1)).saveRealEstateAgentCommand(any());

        mockMvc.perform(post("/realEstateAgent")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/realEstateAgent/1/show"));
    }

    @Test
    void deleteById() throws Exception {
        String viewName = controller.deleteById("1");

        assertEquals("redirect:/", viewName);
        verify(service, times(1)).deleteById(anyLong());

        mockMvc.perform(get("/realEstateAgent/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }
}