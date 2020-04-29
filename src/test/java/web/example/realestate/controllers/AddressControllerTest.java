package web.example.realestate.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import web.example.realestate.domain.building.Address;
import web.example.realestate.services.AddressService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class AddressControllerTest {

    private AddressController controller;

    @Mock
    private AddressService service;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new AddressController(service);
    }

    @Test
    void testMockMvc() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(get("/addresses"))
                .andExpect(status().isOk())
                .andExpect(view().name("address"));
    }

    @Test
    void getAddresses() {
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
    }
}