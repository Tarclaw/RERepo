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
import web.example.realestate.commands.AddressCommand;
import web.example.realestate.domain.building.Address;
import web.example.realestate.exceptions.NotFoundException;
import web.example.realestate.services.AddressService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

class AddressControllerTest {

    private AddressController controller;

    private MockMvc mockMvc;

    @Mock
    private AddressService service;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new AddressController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    void getAddressById() throws Exception {
        when(service.getById(anyLong())).thenReturn(new Address());
        mockMvc.perform(get("/address/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("address/show"))
                .andExpect(model().attributeExists("address"));
    }

    @Test
    void getAddressByIdWhenThereIsNoThisAddressInDB() throws Exception {

        when(service.getById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/address/111/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));
    }

    @Test
    void getAddressByIdWhenNumberFormatException() throws Exception {

        mockMvc.perform(get("/address/abc/show"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }

    @Test
    void getAddresses() throws Exception {
        //given
        List<Address> addresses = new ArrayList<>(
                Collections.singletonList(new Address()));

        when(service.getAddresses()).thenReturn(addresses);

        ArgumentCaptor<List<Address>> argumentCaptor = ArgumentCaptor.forClass(List.class);

        //when
        String viewName = controller.getAddresses(model);

        //then
        assertEquals("address", viewName);
        verify(service, times(1)).getAddresses();
        verify(model, times(1)).addAttribute(eq("addresses"), argumentCaptor.capture());

        List<Address> listInController = argumentCaptor.getValue();
        assertEquals(1, listInController.size());

        mockMvc.perform(get("/addresses"))
                .andExpect(status().isOk())
                .andExpect(view().name("address"));
    }

    @Test
    void newAddress() throws Exception {
        mockMvc.perform(get("/address/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("address/addressForm"))
                .andExpect(model().attributeExists("address"));
    }

    @Test
    void updateAddress() throws Exception {
        when(service.findCommandById(anyLong())).thenReturn(new AddressCommand());
        mockMvc.perform(get("/address/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("address/addressForm"))
                .andExpect(model().attributeExists("address"));
    }

    @Test
    void saveOrUpdate() throws Exception {
        AddressCommand sourceCommand = new AddressCommand();
        sourceCommand.setId(1L);
        when(service.saveAddressCommand(any())).thenReturn(sourceCommand);
        mockMvc.perform(post("/address")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("postCode", "some integer")
        ).andExpect(status().is3xxRedirection())
         .andExpect(view().name("redirect:/address/1/show"));

    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(get("/address/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
        verify(service, times(1)).deleteById(anyLong());
    }
}