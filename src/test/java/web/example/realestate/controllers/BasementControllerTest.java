package web.example.realestate.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import web.example.realestate.commands.AddressCommand;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Basement;
import web.example.realestate.domain.people.Client;
import web.example.realestate.exceptions.NotFoundException;
import web.example.realestate.services.BasementService;
import web.example.realestate.services.ClientService;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class BasementControllerTest {

    private BasementController controller;

    private MockMvc mockMvc;

    @Mock
    private BasementService basementService;

    @Mock
    private ClientService clientService;

    @Mock
    private Model model;

    @Mock
    private BindingResult basementBinding;

    @Mock
    private BindingResult addressBinding;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new BasementController(basementService, clientService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    void getBasementById() throws Exception {
        //given
        when(basementService.getById(anyLong())).thenReturn(new Basement());
        ArgumentCaptor<Basement> basementCaptor = ArgumentCaptor.forClass(Basement.class);

        //when
        String viewName = controller.getBasementById("1", model);

        //then
        assertEquals("basement/show", viewName);
        verify(basementService, times(1)).getById(anyLong());
        verify(model, times(1)).addAttribute(eq("basement"), basementCaptor.capture());

        mockMvc.perform(get("/basement/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("basement/show"))
                .andExpect(model().attributeExists("basement"));
    }

    @Test
    void getBasementByIdWhenThereIsNoThisBasementInDB() throws Exception {

        when(basementService.getById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/basement/111/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));
    }

    @Test
    void getBasementByIdWhenNumberFormatException() throws Exception {

        mockMvc.perform(get("/basement/abc/show"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }

    @Test
    void getAllBasements() throws Exception {
        //given
        Set<Basement> basements = new HashSet<>(
                Collections.singletonList(new Basement())
        );
        when(basementService.getBasements()).thenReturn(basements);

        ArgumentCaptor<Set<Basement>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.getAllBasements(model);

        //then
        assertEquals("basements", viewName);
        verify(basementService, times(1)).getBasements();
        verify(model, times(1)).addAttribute(eq("basements"), argumentCaptor.capture());

        Set<Basement> setInController = argumentCaptor.getValue();
        assertEquals(1, setInController.size());

        mockMvc.perform(get("/basement"))
                .andExpect(status().isOk())
                .andExpect(view().name("basements"));
    }

    @Test
    void newBasement() throws Exception {
        when(clientService.getClients()).thenReturn(
                new HashSet<>(
                        Collections.singletonList(new Client())
                )
        );
        ArgumentCaptor<FacilityCommand> basementCaptor = ArgumentCaptor.forClass(FacilityCommand.class);
        ArgumentCaptor<AddressCommand> addressCaptor = ArgumentCaptor.forClass(AddressCommand.class);
        ArgumentCaptor<Set<Client>> clientCaptor = ArgumentCaptor.forClass(Set.class);

        String viewName = controller.newBasement(model);
        assertEquals("basement/basementEmptyForm", viewName);
        verify(model, times(1)).addAttribute(eq("basement"), basementCaptor.capture());
        verify(model, times(1)).addAttribute(eq("address"), addressCaptor.capture());
        verify(model, times(1)).addAttribute(eq("clients"), clientCaptor.capture());

        mockMvc.perform(get("/basement/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("basement/basementEmptyForm"))
                .andExpect(model().attributeExists("basement"))
                .andExpect(model().attributeExists("clients"));
    }

    @Test
    void updateBasement() throws Exception {
        //given
        when(basementService.findCommandById(anyLong())).thenReturn(new FacilityCommand());
        when(clientService.getClients()).thenReturn(
                new HashSet<>(
                        Collections.singletonList(new Client())
                )
        );
        ArgumentCaptor<FacilityCommand> basementCaptor = ArgumentCaptor.forClass(FacilityCommand.class);
        ArgumentCaptor<AddressCommand> addressCaptor = ArgumentCaptor.forClass(AddressCommand.class);
        ArgumentCaptor<Set<Client>> clientCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.updateBasement("1", model);

        //then
        assertEquals("basement/basementForm", viewName);
        verify(basementService, times(1)).findCommandById(anyLong());
        verify(model, times(1)).addAttribute(eq("basement"), basementCaptor.capture());
        verify(model, times(1)).addAttribute(eq("address"), addressCaptor.capture());
        verify(model, times(1)).addAttribute(eq("clients"), clientCaptor.capture());

        mockMvc.perform(get("/basement/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("basement/basementForm"))
                .andExpect(model().attributeExists("basement"))
                .andExpect(model().attributeExists("clients"));
    }

    @Test
    void saveNew() throws Exception {
        //given
        FacilityCommand facilityCommand = new FacilityCommand();
        facilityCommand.setId(1L);
        AddressCommand addressCommand = new AddressCommand();

        when(basementBinding.hasErrors()).thenReturn(false);
        when(addressBinding.hasErrors()).thenReturn(false);
        when(basementService.saveDetached(any())).thenReturn(facilityCommand);

        //when
        String viewName = controller.saveNew(facilityCommand, basementBinding, addressCommand, addressBinding, model);

        //then
        assertEquals("redirect:/basement/1/show", viewName);
        verify(basementBinding, times(1)).hasErrors();
        verify(addressBinding, times(1)).hasErrors();
        verify(basementService, times(1)).saveDetached(any());

        mockMvc.perform(post("/basement/save")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1")
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
            .andExpect(view().name("redirect:/basement/1/show"));

    }

    @Test
    void saveNewWhenCommandValuesAreNotValid() throws Exception {
        //given
        when(basementBinding.hasErrors()).thenReturn(true);

        ArgumentCaptor<FacilityCommand> basementCaptor = ArgumentCaptor.forClass(FacilityCommand.class);
        ArgumentCaptor<AddressCommand> addressCaptor = ArgumentCaptor.forClass(AddressCommand.class);
        ArgumentCaptor<Set<Client>> clientsCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.saveNew(new FacilityCommand(), basementBinding, new AddressCommand(), addressBinding, model);
        assertEquals("basement/basementEmptyForm", viewName);
        verify(basementBinding, times(1)).hasErrors();
        verify(basementBinding, times(1)).getAllErrors();
        verify(addressBinding, times(1)).getAllErrors();
        verify(model, times(1)).addAttribute(eq("basement"), basementCaptor.capture());
        verify(model, times(1)).addAttribute(eq("address"), addressCaptor.capture());
        verify(model, times(1)).addAttribute(eq("clients"), clientsCaptor.capture());
        verify(clientService, times(1)).getClients();

        mockMvc.perform(post("/basement/save")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("basement/basementEmptyForm"));
    }

    @Test
    void updateExisting() throws Exception {
        //given
        FacilityCommand facilityCommand = new FacilityCommand();
        facilityCommand.setId(1L);
        AddressCommand addressCommand = new AddressCommand();

        when(basementBinding.hasErrors()).thenReturn(false);
        when(addressBinding.hasErrors()).thenReturn(false);
        when(basementService.saveAttached(any())).thenReturn(facilityCommand);

        //when
        String viewName = controller.updateExisting(facilityCommand, basementBinding, addressCommand, addressBinding, model);

        //then
        assertEquals("redirect:/basement/1/show", viewName);
        verify(basementBinding, times(1)).hasErrors();
        verify(addressBinding, times(1)).hasErrors();
        verify(basementService, times(1)).saveAttached(any());

        mockMvc.perform(post("/basement/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1")
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
                .andExpect(view().name("redirect:/basement/1/show"));
    }

    @Test
    void updateExistingWhenCommandValuesAreNotValid() throws Exception {
        //given
        when(basementBinding.hasErrors()).thenReturn(true);

        ArgumentCaptor<FacilityCommand> basementCaptor = ArgumentCaptor.forClass(FacilityCommand.class);
        ArgumentCaptor<AddressCommand> addressCaptor = ArgumentCaptor.forClass(AddressCommand.class);
        ArgumentCaptor<Set<Client>> clientsCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.updateExisting(new FacilityCommand(), basementBinding, new AddressCommand(), addressBinding, model);
        assertEquals("basement/basementForm", viewName);
        verify(basementBinding, times(1)).hasErrors();
        verify(basementBinding, times(1)).getAllErrors();
        verify(addressBinding, times(1)).getAllErrors();
        verify(model, times(1)).addAttribute(eq("basement"), basementCaptor.capture());
        verify(model, times(1)).addAttribute(eq("address"), addressCaptor.capture());
        verify(model, times(1)).addAttribute(eq("clients"), clientsCaptor.capture());
        verify(clientService, times(1)).getClients();

        //then
        mockMvc.perform(post("/basement/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("basement/basementForm"));
    }

    @Test
    void deleteById() throws Exception {
        String viewName = controller.deleteById("1");
        assertEquals("redirect:/basement", viewName);
        verify(basementService, times(1)).deleteById(anyLong());

        mockMvc.perform(get("/basement/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/basement"));

    }

    @Test
    void uploadBasementImage() throws Exception {
        //given
        Basement basement = new Basement();
        basement.setId(1L);
        when(basementService.getById(anyLong())).thenReturn(basement);
        ArgumentCaptor<Basement> basementCaptor = ArgumentCaptor.forClass(Basement.class);

        //when
        String viewName = controller.basementImageUpload("1", model);

        //then
        assertEquals("basement/basementImageUpload", viewName);
        verify(basementService, times(1)).getById(anyLong());
        verify(model, times(1)).addAttribute(eq("basement"), basementCaptor.capture());

        mockMvc.perform(get("/basement/1/image")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("basement/basementImageUpload"));
    }

    @Test
    void renderBasementImage() throws Exception {
        //given
        FacilityCommand command = new FacilityCommand();
        command.setId(1L);
        command.setImage("BasementImageStub".getBytes());

        when(basementService.findCommandById(anyLong())).thenReturn(command);

        //when
        MockHttpServletResponse response = mockMvc.perform(get("/basement/1/basementimage"))
                .andExpect(status().isOk()).andReturn().getResponse();

        //then
        assertEquals(command.getImage().length, response.getContentAsByteArray().length);
    }

    @Test
    void saveBasementImage() throws Exception {
        //given
        MockMultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt",
                "text/plan", "BasementImageStub".getBytes());

        //when
        String viewName = controller.saveBasementImage("1", multipartFile);

        //then
        assertEquals("redirect:/basement/1/show", viewName);
        verify(basementService, times(1)).saveImage(1L, multipartFile);

        mockMvc.perform(multipart("/basement/1/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/basement/1/show"));
    }
}