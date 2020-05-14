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
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Storage;
import web.example.realestate.services.StorageService;

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

class StorageControllerTest {

    private StorageController controller;

    private MockMvc mockMvc;

    @Mock
    private StorageService service;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new StorageController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getStorageById() throws Exception {
        //given
        when(service.getById(anyLong())).thenReturn(new Storage());
        ArgumentCaptor<Storage> storageCaptor = ArgumentCaptor.forClass(Storage.class);

        //when
        String viewName = controller.getStorageById("1", model);

        //then
        assertEquals("storages/show", viewName);
        verify(service, times(1)).getById(anyLong());
        verify(model, times(1)).addAttribute(eq("storage"), storageCaptor.capture());

        mockMvc.perform(get("/storages/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("storages/show"))
                .andExpect(model().attributeExists("storage"));
    }

    @Test
    void getAllStorages() throws Exception {
        // given
        Set<Storage> storages = new HashSet<>(
                Collections.singletonList(new Storage())
        );

        when(service.getStorages()).thenReturn(storages);

        ArgumentCaptor<Set<Storage>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.getAllStorages(model);

        //then
        assertEquals("storage", viewName);
        verify(service, times(1)).getStorages();
        verify(model, times(1)).addAttribute(eq("storages"), argumentCaptor.capture());

        Set<Storage> setInController = argumentCaptor.getValue();
        assertEquals(1, setInController.size());

        mockMvc.perform(get("/storages"))
                .andExpect(status().isOk())
                .andExpect(view().name("storage"));
    }

    @Test
    void newStorage() throws Exception {
        //given
        ArgumentCaptor<FacilityCommand> commandCaptor = ArgumentCaptor.forClass(FacilityCommand.class);

        //when
        String viewName = controller.newStorage(model);

        //then
        assertEquals("storage/storageForm", viewName);
        verify(model, times(1)).addAttribute(eq("storage"), commandCaptor.capture());

        mockMvc.perform(get("/storage/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("storage/storageForm"))
                .andExpect(model().attributeExists("storage"));
    }

    @Test
    void updateStorage() throws Exception {
        //given
        when(service.findCommandById(anyLong())).thenReturn(new FacilityCommand());
        ArgumentCaptor<FacilityCommand> commandCaptor = ArgumentCaptor.forClass(FacilityCommand.class);

        //when
        String viewName = controller.updateStorage("1", model);

        //then
        assertEquals("storage/storageForm", viewName);
        verify(service, times(1)).findCommandById(anyLong());
        verify(model, times(1)).addAttribute(eq("storage"), commandCaptor.capture());

        mockMvc.perform(get("/storage/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("storage/storageForm"))
                .andExpect(model().attributeExists("storage"));
    }

    @Test
    void saveOrUpdate() throws Exception {
        //given
        FacilityCommand source = new FacilityCommand();
        source.setId(1L);

        when(service.saveStorageCommand(any())).thenReturn(source);

        //when
        String viewName = controller.saveOrUpdate(source);

        //then
        assertEquals("redirect:/storage/1/show", viewName);
        verify(service, times(1)).saveStorageCommand(any());

        mockMvc.perform(post("/storage")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/storage/1/show"));
    }

    @Test
    void deleteById() throws Exception {
        String viewName = controller.deleteById("1");
        assertEquals("redirect:/", viewName);

        mockMvc.perform(get("/storage/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }
}