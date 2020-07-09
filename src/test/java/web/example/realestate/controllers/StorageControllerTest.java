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
import web.example.realestate.domain.building.Storage;
import web.example.realestate.domain.people.Client;
import web.example.realestate.exceptions.NotFoundException;
import web.example.realestate.services.ClientService;
import web.example.realestate.services.StorageService;

import java.math.BigInteger;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
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

class StorageControllerTest {

    private StorageController controller;

    private MockMvc mockMvc;

    @Mock
    private StorageService storageService;

    @Mock
    private ClientService clientService;

    @Mock
    private Model model;

    @Mock
    private BindingResult storageBinding;

    @Mock
    private BindingResult addressBinding;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new StorageController(storageService, clientService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    void getStorageById() throws Exception {
        //given
        when(storageService.getById(anyLong())).thenReturn(new Storage());
        ArgumentCaptor<Storage> storageCaptor = ArgumentCaptor.forClass(Storage.class);

        //when
        String viewName = controller.getStorageById("1", model);

        //then
        assertEquals("storage/show", viewName);
        verify(storageService, times(1)).getById(anyLong());
        verify(model, times(1)).addAttribute(eq("storage"), storageCaptor.capture());

        mockMvc.perform(get("/storage/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("storage/show"))
                .andExpect(model().attributeExists("storage"));
    }

    @Test
    void getStorageByIdWhenThereIsNoThisStorageInDB() throws Exception {

        when(storageService.getById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/storage/111/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));
    }

    @Test
    void getStorageByIdWhenNumberFormatException() throws Exception {

        mockMvc.perform(get("/storage/abc/show"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }

    @Test
    void getAllStorages() throws Exception {
        // given
        Set<Storage> storages = new HashSet<>(
                Collections.singletonList(new Storage())
        );

        when(storageService.getStorages()).thenReturn(storages);

        ArgumentCaptor<Set<Storage>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.getAllStorages(model);

        //then
        assertEquals("storages", viewName);
        verify(storageService, times(1)).getStorages();
        verify(model, times(1)).addAttribute(eq("storages"), argumentCaptor.capture());

        Set<Storage> setInController = argumentCaptor.getValue();
        assertEquals(1, setInController.size());

        mockMvc.perform(get("/storage"))
                .andExpect(status().isOk())
                .andExpect(view().name("storages"));
    }

    @Test
    void newStorage() throws Exception {
        //given
        when(clientService.getClients()).thenReturn(
                new HashSet<>(
                        Collections.singletonList(new Client())
                )
        );
        ArgumentCaptor<FacilityCommand> storageCaptor = ArgumentCaptor.forClass(FacilityCommand.class);
        ArgumentCaptor<AddressCommand> addressCaptor = ArgumentCaptor.forClass(AddressCommand.class);
        ArgumentCaptor<Set<Client>> clientCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.newStorage(model);

        //then
        assertEquals("storage/storageEmptyForm", viewName);
        verify(model, times(1)).addAttribute(eq("storage"), storageCaptor.capture());
        verify(model, times(1)).addAttribute(eq("address"), addressCaptor.capture());
        verify(model, times(1)).addAttribute(eq("clients"), clientCaptor.capture());

        mockMvc.perform(get("/storage/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("storage/storageEmptyForm"))
                .andExpect(model().attributeExists("storage"))
                .andExpect(model().attributeExists("address"))
                .andExpect(model().attributeExists("clients"));
    }

    @Test
    void updateStorage() throws Exception {
        //given
        FacilityCommand facilityCommand = new FacilityCommand();
        facilityCommand.setAddress(new AddressCommand());
        when(storageService.findCommandById(anyLong())).thenReturn(facilityCommand);
        when(clientService.getClients()).thenReturn(
                new HashSet<>(
                        Collections.singletonList(new Client())
                )
        );
        ArgumentCaptor<FacilityCommand> storageCaptor = ArgumentCaptor.forClass(FacilityCommand.class);
        ArgumentCaptor<AddressCommand> addressCaptor = ArgumentCaptor.forClass(AddressCommand.class);
        ArgumentCaptor<Set<Client>> clientCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.updateStorage("1", model);

        //then
        assertEquals("storage/storageForm", viewName);
        verify(storageService, times(1)).findCommandById(anyLong());
        verify(model, times(1)).addAttribute(eq("storage"), storageCaptor.capture());
        verify(model, times(1)).addAttribute(eq("address"), addressCaptor.capture());
        verify(model, times(1)).addAttribute(eq("clients"), clientCaptor.capture());

        mockMvc.perform(get("/storage/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("storage/storageForm"))
                .andExpect(model().attributeExists("storage"))
                .andExpect(model().attributeExists("address"))
                .andExpect(model().attributeExists("clients"));
    }

    @Test
    void saveNew() throws Exception {
        //given
        FacilityCommand facilityCommand = new FacilityCommand();
        facilityCommand.setId(1L);
        AddressCommand addressCommand = new AddressCommand();

        when(storageBinding.hasErrors()).thenReturn(false);
        when(addressBinding.hasErrors()).thenReturn(false);
        when(storageService.saveDetached(any())).thenReturn(facilityCommand);

        //when
        String viewName = controller.saveNew(facilityCommand, storageBinding, addressCommand, addressBinding, model);

        //then
        assertEquals("redirect:/storage/1/show", viewName);
        verify(storageBinding, times(1)).hasErrors();
        verify(addressBinding, times(1)).hasErrors();
        verify(storageService, times(1)).saveDetached(any());

        mockMvc.perform(post("/storage/save")
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
            .andExpect(view().name("redirect:/storage/1/show"));
    }

    @Test
    void saveNewWhenCommandValuesAreNotValid() throws Exception {
        //given
        when(storageBinding.hasErrors()).thenReturn(true);

        ArgumentCaptor<FacilityCommand> storageCaptor = ArgumentCaptor.forClass(FacilityCommand.class);
        ArgumentCaptor<AddressCommand> addressCaptor = ArgumentCaptor.forClass(AddressCommand.class);
        ArgumentCaptor<Set<Client>> clientsCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.saveNew(new FacilityCommand(), storageBinding, new AddressCommand(), addressBinding, model);
        assertEquals("storage/storageEmptyForm", viewName);
        verify(storageBinding, times(1)).hasErrors();
        verify(storageBinding, times(1)).getAllErrors();
        verify(addressBinding, times(1)).getAllErrors();
        verify(model, times(1)).addAttribute(eq("storage"), storageCaptor.capture());
        verify(model, times(1)).addAttribute(eq("address"), addressCaptor.capture());
        verify(model, times(1)).addAttribute(eq("clients"), clientsCaptor.capture());
        verify(clientService, times(1)).getClients();

        mockMvc.perform(post("/storage/save")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("storage/storageEmptyForm"));
    }

    @Test
    void updateExisting() throws Exception {
        //given
        FacilityCommand facilityCommand = new FacilityCommand();
        facilityCommand.setId(1L);
        AddressCommand addressCommand = new AddressCommand();

        when(storageBinding.hasErrors()).thenReturn(false);
        when(addressBinding.hasErrors()).thenReturn(false);
        when(storageService.saveAttached(any())).thenReturn(facilityCommand);

        //when
        String viewName = controller.updateExisting(facilityCommand, storageBinding, addressCommand, addressBinding, model);

        //then
        assertEquals("redirect:/storage/1/show", viewName);
        verify(storageBinding, times(1)).hasErrors();
        verify(addressBinding, times(1)).hasErrors();
        verify(storageService, times(1)).saveAttached(any());

        mockMvc.perform(post("/storage/update")
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
                .andExpect(view().name("redirect:/storage/1/show"));
    }

    @Test
    void updateExistingWhenCommandValuesAreNotValid() throws Exception {
        //given
        when(storageBinding.hasErrors()).thenReturn(true);

        ArgumentCaptor<FacilityCommand> storageCaptor = ArgumentCaptor.forClass(FacilityCommand.class);
        ArgumentCaptor<AddressCommand> addressCaptor = ArgumentCaptor.forClass(AddressCommand.class);
        ArgumentCaptor<Set<Client>> clientsCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.updateExisting(new FacilityCommand(), storageBinding, new AddressCommand(), addressBinding, model);
        assertEquals("storage/storageForm", viewName);
        verify(storageBinding, times(1)).hasErrors();
        verify(storageBinding, times(1)).getAllErrors();
        verify(addressBinding, times(1)).getAllErrors();
        verify(model, times(1)).addAttribute(eq("storage"), storageCaptor.capture());
        verify(model, times(1)).addAttribute(eq("address"), addressCaptor.capture());
        verify(model, times(1)).addAttribute(eq("clients"), clientsCaptor.capture());
        verify(clientService, times(1)).getClients();

        mockMvc.perform(post("/storage/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("storage/storageForm"));
    }

    @Test
    void deleteById() throws Exception {
        String viewName = controller.deleteById("1");
        assertEquals("redirect:/storage", viewName);

        mockMvc.perform(get("/storage/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/storage"));
    }

    @Test
    void uploadStorageImage() throws Exception {
        //given
        Storage storage = new Storage();
        storage.setId(1L);

        when(storageService.getById(anyLong())).thenReturn(storage);

        ArgumentCaptor<Storage> storageCaptor = ArgumentCaptor.forClass(Storage.class);

        //when
        String viewName = controller.storageImageUpload("1", model);

        //then
        assertEquals("storage/storageImageUpload", viewName);
        verify(storageService, times(1)).getById(anyLong());
        verify(model, times(1)).addAttribute(eq("storage"), storageCaptor.capture());

        mockMvc.perform(get("/storage/1/image")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("storage/storageImageUpload"));
    }

    @Test
    void renderStorageImage() throws Exception {
        //given
        FacilityCommand storage = new FacilityCommand();
        storage.setId(1L);
        storage.setImage("StorageImageStub".getBytes());

        when(storageService.findCommandById(anyLong())).thenReturn(storage);

        //when
        MockHttpServletResponse response = mockMvc.perform(get("/storage/1/storageimage"))
                .andExpect(status().isOk()).andReturn().getResponse();

        //then
        assertEquals(storage.getImage().length, response.getContentAsByteArray().length);
    }

    @Test
    void saveStorageImage() throws Exception {
        //given
        MockMultipartFile multipartFile = new MockMultipartFile("imagefile", "test.txt",
                "text/plain", "StorageImageStub".getBytes());

        //when
        String viewName = controller.saveStorageImage("1", multipartFile);

        //then
        assertEquals("redirect:/storage/1/show", viewName);
        verify(storageService, times(1)).saveImage(1L, multipartFile);

        mockMvc.perform(multipart("/storage/1/image")
                .file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/storage/1/show"));
    }
}