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
import web.example.realestate.domain.building.House;
import web.example.realestate.domain.people.Client;
import web.example.realestate.services.ClientService;
import web.example.realestate.services.HouseService;

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

class HouseControllerTest {

    private HouseController controller;

    private MockMvc mockMvc;

    @Mock
    private HouseService houseService;

    @Mock
    private ClientService clientService;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new HouseController(houseService, clientService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getHouseById() throws Exception {
        //given
        when(houseService.getById(anyLong())).thenReturn(new House());
        ArgumentCaptor<House> houseCaptor = ArgumentCaptor.forClass(House.class);

        //when
        String viewName = controller.getHouseById("1", model);

        //then
        assertEquals("house/show", viewName);
        verify(houseService, times(1)).getById(anyLong());
        verify(model, times(1)).addAttribute(eq("house"), houseCaptor.capture());

        mockMvc.perform(get("/house/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("house/show"))
                .andExpect(model().attributeExists("house"));
    }

    @Test
    void getAllHouses() throws Exception {
        //given
        Set<House> houses = new HashSet<>(
                Collections.singletonList(new House())
        );

        when(houseService.getHouses()).thenReturn(houses);

        ArgumentCaptor<Set<House>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.getAllHouses(model);

        //then
        assertEquals("houses", viewName);
        verify(houseService, times(1)).getHouses();
        verify(model, times(1)).addAttribute(eq("houses"), argumentCaptor.capture());

        Set<House> setInController = argumentCaptor.getValue();
        assertEquals(1, setInController.size());

        mockMvc.perform(get("/house"))
                .andExpect(status().isOk())
                .andExpect(view().name("houses"));
    }

    @Test
    void newHouse() throws Exception {
        //given
        ArgumentCaptor<FacilityCommand> commandCaptor = ArgumentCaptor.forClass(FacilityCommand.class);
        ArgumentCaptor<Set<Client>> clientCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.newHouse(model);

        //then
        assertEquals("house/houseEmptyForm", viewName);
        verify(model, times(1)).addAttribute(eq("house"), commandCaptor.capture());
        verify(model, times(1)).addAttribute(eq("clients"), clientCaptor.capture());

        mockMvc.perform(get("/house/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("house/houseEmptyForm"))
                .andExpect(model().attributeExists("house"))
                .andExpect(model().attributeExists("clients"));
    }

    @Test
    void updateHouse() throws Exception {
        //given
        when(houseService.findCommandById(anyLong())).thenReturn(new FacilityCommand());
        ArgumentCaptor<FacilityCommand> commandCaptor = ArgumentCaptor.forClass(FacilityCommand.class);
        ArgumentCaptor<Set<Client>> clientCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.updateHouse("1", model);

        //then
        assertEquals("house/houseForm", viewName);
        verify(houseService, times(1)).findCommandById(anyLong());
        verify(model, times(1)).addAttribute(eq("house"), commandCaptor.capture());
        verify(model, times(1)).addAttribute(eq("clients"), clientCaptor.capture());

        mockMvc.perform(get("/house/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("house/houseForm"))
                .andExpect(model().attributeExists("house"))
                .andExpect(model().attributeExists("clients"));
    }

    @Test
    void saveNew() throws Exception {
        //given
        FacilityCommand source = new FacilityCommand();
        source.setId(1L);

        when(houseService.saveDetached(any())).thenReturn(source);

        //when
        String viewName = controller.saveNew(source);

        //then
        assertEquals("redirect:/house/1/show", viewName);
        verify(houseService, times(1)).saveDetached(any());

        mockMvc.perform(post("/house/save")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/house/1/show"));
    }

    @Test
    void updateExisting() throws Exception {
        //given
        FacilityCommand source = new FacilityCommand();
        source.setId(1L);

        when(houseService.saveAttached(any())).thenReturn(source);

        //when
        String viewName = controller.updateExisting(source);

        //then
        assertEquals("redirect:/house/1/show", viewName);
        verify(houseService, times(1)).saveAttached(any());

        mockMvc.perform(post("/house/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/house/1/show"));
    }

    @Test
    void deleteById() throws Exception {
        String viewName = controller.deleteById("1");
        assertEquals("redirect:/house", viewName);
        verify(houseService, times(1)).deleteById(anyLong());

        mockMvc.perform(get("/house/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/house"));
    }
}