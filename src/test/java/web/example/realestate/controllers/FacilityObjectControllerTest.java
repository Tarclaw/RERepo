package web.example.realestate.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import web.example.realestate.domain.building.FacilityObject;
import web.example.realestate.services.FacilityObjectService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class FacilityObjectControllerTest {

    private FacilityObjectController controller;

    @Mock
    private FacilityObjectService service;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new FacilityObjectController(service);
    }

    @Test
    void testMockMvc() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(get("/facilityObjects"))
                .andExpect(status().isOk())
                .andExpect(view().name("facilityObject"));
    }

    @Test
    void getAllFacilityObjects() {
        //given
        Set<FacilityObject> facilityObjects = new HashSet<>(
                Collections.singletonList(new FacilityObject())
        );

        when(service.getFacilityObjects()).thenReturn(facilityObjects);

        ArgumentCaptor<Set<FacilityObject>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.getAllFacilityObjects(model);

        //then
        assertEquals("facilityObject", viewName);
        verify(service, times(1)).getFacilityObjects();
        verify(model, times(1)).addAttribute(eq("facilityObjects"), argumentCaptor.capture());

        Set<FacilityObject> setInController = argumentCaptor.getValue();
        assertEquals(1, setInController.size());
    }
}