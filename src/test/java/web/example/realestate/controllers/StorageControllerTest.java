package web.example.realestate.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import web.example.realestate.domain.building.Storage;
import web.example.realestate.services.StorageService;

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

class StorageControllerTest {

    private StorageController controller;

    @Mock
    private StorageService service;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new StorageController(service);
    }

    @Test
    void testMockMvc() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(get("/storages"))
                .andExpect(status().isOk())
                .andExpect(view().name("storage"));
    }

    @Test
    void getAllStorages() {
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
    }
}