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
import web.example.realestate.commands.AddressCommand;
import web.example.realestate.commands.ClientCommand;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.commands.MappingCommand;
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

class ClientControllerTest {

    private ClientController controller;

    private MockMvc mockMvc;

    @Mock
    private ClientService clientService;

    @Mock
    private RealEstateAgentService agentService;

    @Mock
    private Model model;

    @Mock
    private BindingResult clientResult;

    @Mock
    private BindingResult facilityResult;

    @Mock
    private BindingResult addressResult;

    @Mock
    private MappingCommand mappingCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new ClientController(clientService, agentService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    void getClientById() throws Exception {
        //given
        when(clientService.getById(anyLong())).thenReturn(new Client());
        ArgumentCaptor<Client> clientCaptor = ArgumentCaptor.forClass(Client.class);

        //when
        String viewName = controller.getClientById("1", model);

        //then
        assertEquals("client/show", viewName);
        verify(clientService, times(1)).getById(anyLong());
        verify(model, times(1)).addAttribute(eq("client"), clientCaptor.capture());

        mockMvc.perform(get("/client/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("client/show"))
                .andExpect(model().attributeExists("client"));
    }

    @Test
    void getClientByIdWhenThereIsNoThisClientInDB() throws Exception {

        when(clientService.getById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/client/111/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));
    }

    @Test
    void getClientByIdWhenNumberFormatException() throws Exception {

        mockMvc.perform(get("/client/abc/show"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }

    @Test
    void getAllClients() throws Exception {
        //given
        Set<Client> clients = new HashSet<>(
                Collections.singletonList(new Client())
        );
        when(clientService.getClients()).thenReturn(clients);

        ArgumentCaptor<Set<Client>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.getAllClients(model);

        //then
        assertEquals("client", viewName);
        verify(clientService, times(1)).getClients();
        verify(model, times(1)).addAttribute(eq("clients"), argumentCaptor.capture());

        Set<Client> setInController = argumentCaptor.getValue();
        assertEquals(1, setInController.size());

        mockMvc.perform(get("/clients"))
                .andExpect(status().isOk())
                .andExpect(view().name("client"));
    }

    @Test
    void newClient() throws Exception {
        //given
        ArgumentCaptor<ClientCommand> commandCaptor = ArgumentCaptor.forClass(ClientCommand.class);

        //when
        String viewName = controller.newClient(model);

        //then
        assertEquals("client/clientEmptyForm", viewName);
        verify(model, times(1)).addAttribute(eq("client"), commandCaptor.capture());

        mockMvc.perform(get("/client/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("client/clientEmptyForm"))
                .andExpect(model().attributeExists("client"));
    }

    @Test
    void updateClient() throws Exception {
        //given
        when(clientService.findCommandById(anyLong())).thenReturn(new ClientCommand());
        ArgumentCaptor<ClientCommand> commandCaptor = ArgumentCaptor.forClass(ClientCommand.class);

        //when
        String viewName = controller.updateClient("1", model);

        //then
        assertEquals("client/clientForm", viewName);
        verify(clientService, times(1)).findCommandById(anyLong());
        verify(model, times(1)).addAttribute(eq("client"), commandCaptor.capture());

        mockMvc.perform(get("/client/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("client/clientForm"))
                .andExpect(model().attributeExists("client"));
    }

    @Test
    void saveNew() throws Exception {
        //given
        ClientCommand clientCommand = new ClientCommand();
        FacilityCommand facilityCommand = new FacilityCommand();
        AddressCommand addressCommand = new AddressCommand();

        when(clientResult.hasErrors()).thenReturn(false);
        when(facilityResult.hasErrors()).thenReturn(false);
        when(addressResult.hasErrors()).thenReturn(false);
        when(clientService.saveDetached(clientCommand, facilityCommand)).thenReturn(clientCommand);

        //when
        String viewName = controller.saveNew(clientCommand, clientResult,
                                             facilityCommand, facilityResult,
                                             addressCommand, addressResult, model);

        //then
        assertEquals("redirect:/clients", viewName);
        verify(clientResult, times(1)).hasErrors();
        verify(facilityResult, times(1)).hasErrors();
        verify(addressResult, times(1)).hasErrors();
        verify(clientService, times(1)).saveDetached(any(), any());

        mockMvc.perform(post("/client/save")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", "First Name")
                .param("lastName", "Last Name")
                .param("login", "login")
                .param("password", "Password")
                .param("email", "some@email.com")
                .param("skype", "Skype")
                .param("mobileNumber", "+1 078 777 88 99")
                .param("customerRequirements", "customer requires smth")
                .param("numberOfRooms", "1")
                .param("totalArea", "20")
                .param("description", "some description")
                .param("monthRent", "3000")
                .param("price", "300000")
                .param("postcode", "88550")
                .param("facilityNumber", "33")
                .param("city", "some city")
                .param("district", "some district")
                .param("street", "some street")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/clients"));
    }

    @Test
    void saveNewWhenCommandVariablesAreNotValid() throws Exception {
        //given
        ClientCommand clientCommand = new ClientCommand();
        FacilityCommand facilityCommand = new FacilityCommand();
        AddressCommand addressCommand = new AddressCommand();

        when(clientResult.hasErrors()).thenReturn(true);

        ArgumentCaptor<ClientCommand> clientCaptor = ArgumentCaptor.forClass(ClientCommand.class);
        ArgumentCaptor<FacilityCommand> facilityCaptor = ArgumentCaptor.forClass(FacilityCommand.class);
        ArgumentCaptor<AddressCommand> addressCaptor = ArgumentCaptor.forClass(AddressCommand.class);
        ArgumentCaptor<Set<RealEstateAgent>> agentsCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.saveNew(clientCommand, clientResult,
                facilityCommand, facilityResult,
                addressCommand, addressResult, model);

        //then
        assertEquals("client/clientEmptyForm", viewName);
        verify(clientResult, times(1)).hasErrors();
        verify(clientResult, times(1)).getAllErrors();
        verify(facilityResult, times(1)).getAllErrors();
        verify(addressResult, times(1)).getAllErrors();
        verify(model, times(1)).addAttribute(eq("client"), clientCaptor.capture());
        verify(model, times(1)).addAttribute(eq("facility"), facilityCaptor.capture());
        verify(model, times(1)).addAttribute(eq("address"), addressCaptor.capture());
        verify(model, times(1)).addAttribute(eq("realEstateAgents"), agentsCaptor.capture());
        verify(agentService, times(1)).getRealEstateAgents();

        mockMvc.perform(post("/client/save")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("client/clientEmptyForm"));
    }

    @Test
    void updateExisting() throws Exception {
        //given
        ClientCommand clientCommand = new ClientCommand();
        clientCommand.setId(1L);
        when(clientResult.hasErrors()).thenReturn(false);
        when(clientService.saveAttached(any())).thenReturn(clientCommand);

        //when
        String viewName = controller.updateExisting(clientCommand, clientResult, model);

        //then
        assertEquals("redirect:/client/1/show", viewName);
        verify(clientResult, times(1)).hasErrors();
        verify(clientService, times(1)).saveAttached(any());

        mockMvc.perform(post("/client/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1")
                .param("firstName", "First Name")
                .param("lastName", "Last Name")
                .param("login", "login")
                .param("password", "Password")
                .param("email", "some@email.com")
                .param("skype", "Skype")
                .param("mobileNumber", "+1 078 777 88 99")
                .param("customerRequirements", "customer requires smth")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/client/1/show"));
    }

    @Test
    void deleteById() throws Exception {
        String viewName = controller.deleteById("1");

        assertEquals("redirect:/clients", viewName);
        verify(clientService, times(1)).deleteById(anyLong());

        mockMvc.perform(get("/client/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/clients"));
    }

    @Test
    void newClientForApartment() throws Exception {
        //given
        ArgumentCaptor<ClientCommand> commandCaptor = ArgumentCaptor.forClass(ClientCommand.class);
        ArgumentCaptor<MappingCommand> mappingCaptor = ArgumentCaptor.forClass(MappingCommand.class);
        ArgumentCaptor<Set<RealEstateAgent>> agentsCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.newClientForApartment(model);

        //then
        assertEquals("client/clientForFacilityForm", viewName);
        verify(model, times(1)).addAttribute(eq("client"), commandCaptor.capture());
        verify(model, times(1)).addAttribute(eq("mapping"), mappingCaptor.capture());
        verify(model, times(1)).addAttribute(eq("realEstateAgents"), agentsCaptor.capture());

        mockMvc.perform(get("/apartment/client/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("client/clientForFacilityForm"))
                .andExpect(model().attributeExists("client"))
                .andExpect(model().attributeExists("mapping"))
                .andExpect(model().attributeExists("realEstateAgents"));
    }

    @Test
    void newClientForBasement() throws Exception {
        //given
        ArgumentCaptor<ClientCommand> commandCaptor = ArgumentCaptor.forClass(ClientCommand.class);
        ArgumentCaptor<MappingCommand> mappingCaptor = ArgumentCaptor.forClass(MappingCommand.class);
        ArgumentCaptor<Set<RealEstateAgent>> agentsCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.newClientForBasement(model);

        //then
        assertEquals("client/clientForFacilityForm", viewName);
        verify(model, times(1)).addAttribute(eq("client"), commandCaptor.capture());
        verify(model, times(1)).addAttribute(eq("mapping"), mappingCaptor.capture());
        verify(model, times(1)).addAttribute(eq("realEstateAgents"), agentsCaptor.capture());

        mockMvc.perform(get("/basement/client/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("client/clientForFacilityForm"))
                .andExpect(model().attributeExists("client"))
                .andExpect(model().attributeExists("mapping"))
                .andExpect(model().attributeExists("realEstateAgents"));
    }

    @Test
    void newClientForGarage() throws Exception {
        //given
        ArgumentCaptor<ClientCommand> commandCaptor = ArgumentCaptor.forClass(ClientCommand.class);
        ArgumentCaptor<MappingCommand> mappingCaptor = ArgumentCaptor.forClass(MappingCommand.class);
        ArgumentCaptor<Set<RealEstateAgent>> agentsCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.newClientForGarage(model);

        //then
        assertEquals("client/clientForFacilityForm", viewName);
        verify(model, times(1)).addAttribute(eq("client"), commandCaptor.capture());
        verify(model, times(1)).addAttribute(eq("mapping"), mappingCaptor.capture());
        verify(model, times(1)).addAttribute(eq("realEstateAgents"), agentsCaptor.capture());

        mockMvc.perform(get("/garage/client/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("client/clientForFacilityForm"))
                .andExpect(model().attributeExists("client"))
                .andExpect(model().attributeExists("mapping"))
                .andExpect(model().attributeExists("realEstateAgents"));
    }

    @Test
    void newClientForHouse() throws Exception {
        //given
        ArgumentCaptor<ClientCommand> commandCaptor = ArgumentCaptor.forClass(ClientCommand.class);
        ArgumentCaptor<MappingCommand> mappingCaptor = ArgumentCaptor.forClass(MappingCommand.class);
        ArgumentCaptor<Set<RealEstateAgent>> agentsCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.newClientForHouse(model);

        //then
        assertEquals("client/clientForFacilityForm", viewName);
        verify(model, times(1)).addAttribute(eq("client"), commandCaptor.capture());
        verify(model, times(1)).addAttribute(eq("mapping"), mappingCaptor.capture());
        verify(model, times(1)).addAttribute(eq("realEstateAgents"), agentsCaptor.capture());

        mockMvc.perform(get("/house/client/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("client/clientForFacilityForm"))
                .andExpect(model().attributeExists("client"))
                .andExpect(model().attributeExists("mapping"))
                .andExpect(model().attributeExists("realEstateAgents"));
    }

    @Test
    void newClientForStorage() throws Exception {
        //given
        ArgumentCaptor<ClientCommand> commandCaptor = ArgumentCaptor.forClass(ClientCommand.class);
        ArgumentCaptor<MappingCommand> mappingCaptor = ArgumentCaptor.forClass(MappingCommand.class);
        ArgumentCaptor<Set<RealEstateAgent>> agentsCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.newClientForStorage(model);

        //then
        assertEquals("client/clientForFacilityForm", viewName);
        verify(model, times(1)).addAttribute(eq("client"), commandCaptor.capture());
        verify(model, times(1)).addAttribute(eq("mapping"), mappingCaptor.capture());
        verify(model, times(1)).addAttribute(eq("realEstateAgents"), agentsCaptor.capture());

        mockMvc.perform(get("/storage/client/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("client/clientForFacilityForm"))
                .andExpect(model().attributeExists("client"))
                .andExpect(model().attributeExists("mapping"))
                .andExpect(model().attributeExists("realEstateAgents"));
    }

    @Test
    void saveForFacility() throws Exception {
        //given
        ClientCommand clientCommand = new ClientCommand();
        clientCommand.setId(1L);
        clientCommand.setFirstName("First Name");
        clientCommand.setLastName("Last Name");
        clientCommand.setLogin("Login");
        clientCommand.setPassword("Password");
        clientCommand.setEmail("some@email.com");
        clientCommand.setSkype("Skype");
        clientCommand.setMobileNumber("+1 078 777 88 99");
        clientCommand.setCustomerRequirements("customer requires smth");

        when(clientResult.hasErrors()).thenReturn(false);
        when(clientService.saveAttached(any())).thenReturn(clientCommand);
        when(mappingCommand.getPageName()).thenReturn("apartment");

        //when
        String viewName = controller.saveForFacility(clientCommand, clientResult, mappingCommand, model);

        //then
        assertEquals("redirect:/apartment/new", viewName);
        verify(clientResult, times(1)).hasErrors();
        verify(clientService, times(1)).saveAttached(any());
        verify(mappingCommand, times(1)).getPageName();

        mockMvc.perform(post("/client/facility/save")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1")
                .param("firstName", "First Name")
                .param("lastName", "Last Name")
                .param("login", "login")
                .param("password", "Password")
                .param("email", "some@email.com")
                .param("skype", "Skype")
                .param("mobileNumber", "+1 078 777 88 99")
                .param("customerRequirements", "customer requires smth")
                .param("pageName", "apartment")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/apartment/new"));
    }

    @Test
    void saveForFacilityWhenCommandVariablesAreNotValid() throws Exception {
        //given
        when(clientResult.hasErrors()).thenReturn(true);
        ArgumentCaptor<ClientCommand> commandCaptor = ArgumentCaptor.forClass(ClientCommand.class);
        ArgumentCaptor<MappingCommand> mappingCaptor = ArgumentCaptor.forClass(MappingCommand.class);
        ArgumentCaptor<Set<RealEstateAgent>> agentsCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.saveForFacility(new ClientCommand(), clientResult, mappingCommand, model);

        //then
        assertEquals("client/clientForFacilityForm", viewName);
        verify(clientResult, times(1)).getAllErrors();
        verify(model, times(1)).addAttribute(eq("client"), commandCaptor.capture());
        verify(model, times(1)).addAttribute(eq("mapping"), mappingCaptor.capture());
        verify(model, times(1)).addAttribute(eq("realEstateAgents"), agentsCaptor.capture());
        verify(agentService, times(1)).getRealEstateAgents();

        mockMvc.perform(post("/client/facility/save")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("client/clientForFacilityForm"));
    }

}
