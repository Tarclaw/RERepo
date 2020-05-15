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
import web.example.realestate.commands.FacilityObjectCommand;
import web.example.realestate.domain.building.FacilityObject;
import web.example.realestate.services.FacilityObjectService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

class FacilityObjectControllerTest {

    private FacilityObjectController controller;

    private MockMvc mockMvc;

    @Mock
    private FacilityObjectService service;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new FacilityObjectController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getFacilityObjectById() throws Exception {
        //given
        when(service.getById(anyLong())).thenReturn(new FacilityObject());
        ArgumentCaptor<FacilityObject> captor = ArgumentCaptor.forClass(FacilityObject.class);

        //when
        String viewName = controller.getFacilityObjectById("1", model);

        //then
        assertEquals("facilityObject/show", viewName);
        verify(service, times(1)).getById(anyLong());
        verify(model, times(1)).addAttribute(eq("facilityObject"), captor.capture());

        mockMvc.perform(get("/facilityObject/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("facilityObject/show"))
                .andExpect(model().attributeExists("facilityObject"));
    }

    @Test
    void getAllFacilityObjects() throws Exception {
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

        mockMvc.perform(get("/facilityObjects"))
                .andExpect(status().isOk())
                .andExpect(view().name("facilityObject"));
    }

    @Test
    void newFacilityObject() throws Exception {
        //given
        ArgumentCaptor<FacilityObjectCommand> commandCaptor = ArgumentCaptor.forClass(FacilityObjectCommand.class);

        //when
        String viewName = controller.newFacilityObject(model);

        //then
        assertEquals("facilityObject/facilityObjectForm", viewName);
        verify(model, times(1)).addAttribute(eq("facilityObject"), commandCaptor.capture());

        mockMvc.perform(get("/facilityObject/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("facilityObject/facilityObjectForm"))
                .andExpect(model().attributeExists("facilityObject"));
    }

    @Test
    void updateFacilityObject() throws Exception {
        //given
        when(service.findCommandById(anyLong())).thenReturn(new FacilityObjectCommand());
        ArgumentCaptor<FacilityObjectCommand> commandCaptor = ArgumentCaptor.forClass(FacilityObjectCommand.class);

        //when
        String viewName = controller.updateFacilityObject("1", model);

        //then
        assertEquals("facilityObject/facilityObjectForm", viewName);
        verify(service, times(1)).findCommandById(anyLong());
        verify(model, times(1)).addAttribute(eq("facilityObject"), commandCaptor.capture());

        mockMvc.perform(get("/facilityObject/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("facilityObject/facilityObjectForm"))
                .andExpect(model().attributeExists("facilityObject"));
    }

    @Test
    void saveOrUpdate() throws Exception {
        //given
        FacilityObjectCommand source = new FacilityObjectCommand();
        source.setId(1L);
        when(service.saveFacilityObjectCommand(any())).thenReturn(source);

        //when
        String viewName = controller.saveOrUpdate(source);

        //then
        assertEquals("redirect:/facilityObject/1/show", viewName);
        verify(service, times(1)).saveFacilityObjectCommand(any());

        mockMvc.perform(post("/facilityObject")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/facilityObject/1/show"));
    }

    @Test
    void deleteById() throws Exception {
        String viewName = controller.deleteById("1");

        assertEquals("redirect:/", viewName);
        verify(service, times(1)).deleteById(anyLong());

        mockMvc.perform(get("/facilityObject/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }
}