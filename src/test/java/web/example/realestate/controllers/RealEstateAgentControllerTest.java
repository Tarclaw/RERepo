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
import org.springframework.validation.BindingResult;
import web.example.realestate.commands.RealEstateAgentCommand;
import web.example.realestate.domain.people.Client;
import web.example.realestate.domain.people.RealEstateAgent;
import web.example.realestate.exceptions.NotFoundException;
import web.example.realestate.services.ClientService;
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
    private RealEstateAgentService agentService;

    @Mock
    private ClientService clientService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new RealEstateAgentController(agentService, clientService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    void getRealEstateAgentById() throws Exception {
        //given
        when(agentService.getById(anyLong())).thenReturn(new RealEstateAgent());
        ArgumentCaptor<RealEstateAgent> agentCaptor = ArgumentCaptor.forClass(RealEstateAgent.class);

        //when
        String viewName = controller.getRealEstateAgentById("1", model);

        //then
        assertEquals("realEstateAgent/show", viewName);
        verify(agentService, times(1)).getById(anyLong());
        verify(model, times(1)).addAttribute(eq("realEstateAgent"), agentCaptor.capture());

        mockMvc.perform(get("/realEstateAgent/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("realEstateAgent/show"))
                .andExpect(model().attributeExists("realEstateAgent"));
    }

    @Test
    void getAgentByIdWhenThereIsNoThisAgentInDB() throws Exception {

        when(agentService.getById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/realEstateAgent/111/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));
    }

    @Test
    void getAgentByIdWhenNumberFormatException() throws Exception {

        mockMvc.perform(get("/realEstateAgent/abc/show"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }

    @Test
    void getAllRealEstateAgents() throws Exception {
        //given
        Set<RealEstateAgent> agents = new HashSet<>(
                Collections.singletonList(new RealEstateAgent())
        );

        when(agentService.getRealEstateAgents()).thenReturn(agents);

        ArgumentCaptor<Set<RealEstateAgent>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.getAllRealEstateAgents(model);

        //then
        assertEquals("realEstateAgent", viewName);
        verify(agentService, times(1)).getRealEstateAgents();
        verify(model, times(1)).addAttribute(eq("realEstateAgents"), argumentCaptor.capture());

        Set<RealEstateAgent> setInController = argumentCaptor.getValue();
        assertEquals(1, setInController.size());

        mockMvc.perform(get("/realEstateAgents"))
                .andExpect(status().isOk())
                .andExpect(view().name("realEstateAgent"));
    }

    @Test
    void newAgent() throws Exception {
        //given
        when(clientService.getClients()).thenReturn(
                new HashSet<>(
                        Collections.singletonList(new Client())
                )
        );
        ArgumentCaptor<RealEstateAgentCommand> commandCaptor = ArgumentCaptor.forClass(RealEstateAgentCommand.class);
        ArgumentCaptor<Set<Client>> clientsCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.newAgent(model);

        //then
        assertEquals("realEstateAgent/realEstateAgentForm", viewName);
        verify(clientService, times(1)).getClients();
        verify(model, times(1)).addAttribute(eq("realEstateAgent"), commandCaptor.capture());
        verify(model, times(1)).addAttribute(eq("clients"), clientsCaptor.capture());
        mockMvc.perform(get("/realEstateAgent/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("realEstateAgent/realEstateAgentForm"))
                .andExpect(model().attributeExists("realEstateAgent"))
                .andExpect(model().attributeExists("clients"));
    }

    @Test
    void updateAgent() throws Exception {
        //given
        Set<Client> clients = new HashSet<>(Collections.singletonList(new Client()));
        when(agentService.findCommandById(1L)).thenReturn(new RealEstateAgentCommand());
        when(clientService.getClients()).thenReturn(clients);
        ArgumentCaptor<RealEstateAgentCommand> commandCaptor = ArgumentCaptor.forClass(RealEstateAgentCommand.class);
        ArgumentCaptor<Set<Client>> clientsCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.updateAgent("1", model);

        //then
        assertEquals("realEstateAgent/realEstateAgentForm", viewName);
        verify(agentService, times(1)).findCommandById(anyLong());
        verify(clientService, times(1)).getClients();
        verify(model, times(1)).addAttribute(eq("realEstateAgent"), commandCaptor.capture());
        verify(model, times(1)).addAttribute(eq("clients"), clientsCaptor.capture());

        mockMvc.perform(get("/realEstateAgent/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("realEstateAgent/realEstateAgentForm"))
                .andExpect(model().attributeExists("realEstateAgent"))
                .andExpect(model().attributeExists("clients"));
    }

    @Test
    void saveOrUpdate() throws Exception {
        //given
        RealEstateAgentCommand agentCommand = new RealEstateAgentCommand();
        agentCommand.setId(1L);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(agentService.saveRealEstateAgentCommand(any())).thenReturn(agentCommand);

        //when
        String viewName = controller.saveOrUpdate(agentCommand, bindingResult, model);

        //then
        assertEquals("redirect:/realEstateAgent/1/show", viewName);
        verify(bindingResult, times(1)).hasErrors();
        verify(agentService, times(1)).saveRealEstateAgentCommand(any());

        mockMvc.perform(post("/realEstateAgent")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1")
                .param("firstName", "First Name")
                .param("lastName", "Last Name")
                .param("login", "login")
                .param("password", "Password")
                .param("email", "some@email.com")
                .param("skype", "Skype")
                .param("mobileNumber", "+1 078 777 88 99")
                .param("salary", "1 000 000")
        )
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/realEstateAgent/1/show"));
    }

    @Test
    void saveOrUpdateWhenCommandVariablesAreNotValid() throws Exception {
        //given
        when(bindingResult.hasErrors()).thenReturn(true);

        ArgumentCaptor<RealEstateAgentCommand> agentCaptor = ArgumentCaptor.forClass(RealEstateAgentCommand.class);
        ArgumentCaptor<Set<Client>> clientsCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.saveOrUpdate(new RealEstateAgentCommand(), bindingResult, model);

        //then
        assertEquals("realEstateAgent/realEstateAgentForm", viewName);
        verify(bindingResult, times(1)).hasErrors();
        verify(bindingResult, times(1)).getAllErrors();
        verify(model, times(1)).addAttribute(eq("realEstateAgent"), agentCaptor.capture());
        verify(model, times(1)).addAttribute(eq("clients"), clientsCaptor.capture());
        verify(clientService, times(1)).getClients();

        mockMvc.perform(post("/realEstateAgent")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("realEstateAgent/realEstateAgentForm"));
    }

    @Test
    void deleteById() throws Exception {
        String viewName = controller.deleteById("1");

        assertEquals("redirect:/realEstateAgents", viewName);
        verify(agentService, times(1)).deleteById(anyLong());

        mockMvc.perform(get("/realEstateAgent/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/realEstateAgents"));
    }
}