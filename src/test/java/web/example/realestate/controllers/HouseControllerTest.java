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
import web.example.realestate.domain.building.House;
import web.example.realestate.domain.people.Client;
import web.example.realestate.exceptions.NotFoundException;
import web.example.realestate.services.ClientService;
import web.example.realestate.services.HouseService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
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

class HouseControllerTest {

    private HouseController controller;

    private MockMvc mockMvc;

    @Mock
    private HouseService houseService;

    @Mock
    private ClientService clientService;

    @Mock
    private Model model;

    @Mock
    private BindingResult houseBinding;

    @Mock
    private BindingResult addressBinding;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new HouseController(houseService, clientService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
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
    void getHouseByIdWhenThereIsNoThisHouseInDB() throws Exception {

        when(houseService.getById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/house/111/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));
    }

    @Test
    void getHouseByIdWhenNumberFormatException() throws Exception {

        mockMvc.perform(get("/house/abc/show"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
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
        when(clientService.getClients()).thenReturn(
                new HashSet<>(
                        Collections.singletonList(new Client())
                )
        );
        ArgumentCaptor<FacilityCommand> houseCaptor = ArgumentCaptor.forClass(FacilityCommand.class);
        ArgumentCaptor<AddressCommand> addressCaptor = ArgumentCaptor.forClass(AddressCommand.class);
        ArgumentCaptor<Set<Client>> clientCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.newHouse(model);

        //then
        assertEquals("house/houseEmptyForm", viewName);
        verify(model, times(1)).addAttribute(eq("house"), houseCaptor.capture());
        verify(model, times(1)).addAttribute(eq("address"), addressCaptor.capture());
        verify(model, times(1)).addAttribute(eq("clients"), clientCaptor.capture());

        mockMvc.perform(get("/house/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("house/houseEmptyForm"))
                .andExpect(model().attributeExists("house"))
                .andExpect(model().attributeExists("address"))
                .andExpect(model().attributeExists("clients"));
    }

    @Test
    void updateHouse() throws Exception {
        //given
        FacilityCommand facilityCommand = new FacilityCommand();
        facilityCommand.setAddress(new AddressCommand());
        when(houseService.findCommandById(anyLong())).thenReturn(facilityCommand);
        when(clientService.getClients()).thenReturn(
                new HashSet<>(
                        Collections.singletonList(new Client())
                )
        );
        ArgumentCaptor<FacilityCommand> houseCaptor = ArgumentCaptor.forClass(FacilityCommand.class);
        ArgumentCaptor<AddressCommand> addressCaptor = ArgumentCaptor.forClass(AddressCommand.class);
        ArgumentCaptor<Set<Client>> clientCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.updateHouse("1", model);

        //then
        assertEquals("house/houseForm", viewName);
        verify(houseService, times(1)).findCommandById(anyLong());
        verify(model, times(1)).addAttribute(eq("house"), houseCaptor.capture());
        verify(model, times(1)).addAttribute(eq("address"), addressCaptor.capture());
        verify(model, times(1)).addAttribute(eq("clients"), clientCaptor.capture());

        mockMvc.perform(get("/house/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("house/houseForm"))
                .andExpect(model().attributeExists("house"))
                .andExpect(model().attributeExists("address"))
                .andExpect(model().attributeExists("clients"));
    }

    @Test
    void saveNew() throws Exception {
        //given
        FacilityCommand facilityCommand = new FacilityCommand();
        facilityCommand.setId(1L);
        AddressCommand addressCommand = new AddressCommand();

        when(houseBinding.hasErrors()).thenReturn(false);
        when(addressBinding.hasErrors()).thenReturn(false);
        when(houseService.saveDetached(any())).thenReturn(facilityCommand);

        //when
        String viewName = controller.saveNew(facilityCommand, houseBinding, addressCommand, addressBinding, model);

        //then
        assertEquals("redirect:/house/1/show", viewName);
        verify(houseBinding, times(1)).hasErrors();
        verify(addressBinding, times(1)).hasErrors();
        verify(houseService, times(1)).saveDetached(any());

        mockMvc.perform(post("/house/save")
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
                .andExpect(view().name("redirect:/house/1/show"));
    }

    @Test
    void saveNewWhenCommandValuesAreNotValid() throws Exception {
        //given
        when(houseBinding.hasErrors()).thenReturn(true);

        ArgumentCaptor<FacilityCommand> houseCaptor = ArgumentCaptor.forClass(FacilityCommand.class);
        ArgumentCaptor<AddressCommand> addressCaptor = ArgumentCaptor.forClass(AddressCommand.class);
        ArgumentCaptor<Set<Client>> clientsCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.saveNew(new FacilityCommand(), houseBinding, new AddressCommand(), addressBinding, model);

        //then
        assertEquals("house/houseEmptyForm", viewName);
        verify(houseBinding, times(1)).hasErrors();
        verify(houseBinding, times(1)).getAllErrors();
        verify(addressBinding, times(1)).getAllErrors();
        verify(model, times(1)).addAttribute(eq("house"), houseCaptor.capture());
        verify(model, times(1)).addAttribute(eq("address"), addressCaptor.capture());
        verify(model, times(1)).addAttribute(eq("clients"), clientsCaptor.capture());
        verify(clientService, times(1)).getClients();

        mockMvc.perform(post("/house/save")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("house/houseEmptyForm"));
    }

    @Test
    void updateExisting() throws Exception {
        //given
        FacilityCommand facilityCommand = new FacilityCommand();
        facilityCommand.setId(1L);
        AddressCommand addressCommand = new AddressCommand();

        when(houseBinding.hasErrors()).thenReturn(false);
        when(addressBinding.hasErrors()).thenReturn(false);
        when(houseService.saveDetached(any())).thenReturn(facilityCommand);

        //when
        String viewName = controller.updateExisting(facilityCommand, houseBinding, addressCommand, addressBinding, model);

        //then
        assertEquals("redirect:/house/1/show", viewName);
        verify(houseBinding, times(1)).hasErrors();
        verify(addressBinding, times(1)).hasErrors();
        verify(houseService, times(1)).saveDetached(any());

        mockMvc.perform(post("/house/update")
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
                .andExpect(view().name("redirect:/house/1/show"));
    }

    @Test
    void updateExistingWhenCommandValuesAreNotValid() throws Exception {
        //given
        when(houseBinding.hasErrors()).thenReturn(true);

        ArgumentCaptor<FacilityCommand> houseCaptor = ArgumentCaptor.forClass(FacilityCommand.class);
        ArgumentCaptor<AddressCommand> addressCaptor = ArgumentCaptor.forClass(AddressCommand.class);
        ArgumentCaptor<Set<Client>> clientsCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = controller.updateExisting(new FacilityCommand(), houseBinding, new AddressCommand(), addressBinding, model);

        //then
        assertEquals("house/houseForm", viewName);
        verify(houseBinding, times(1)).hasErrors();
        verify(houseBinding, times(1)).getAllErrors();
        verify(addressBinding, times(1)).getAllErrors();
        verify(model, times(1)).addAttribute(eq("house"), houseCaptor.capture());
        verify(model, times(1)).addAttribute(eq("address"), addressCaptor.capture());
        verify(model, times(1)).addAttribute(eq("clients"), clientsCaptor.capture());
        verify(clientService, times(1)).getClients();

        mockMvc.perform(post("/house/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("house/houseForm"));
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

    @Test
    void uploadHouseImage() throws Exception {
        //given
        House house = new House();
        house.setId(1L);

        when(houseService.getById(anyLong())).thenReturn(house);

        ArgumentCaptor<House> houseCaptor = ArgumentCaptor.forClass(House.class);

        //when
        String viewName = controller.houseImageUpload("1", model);

        //then
        assertEquals("house/houseImageUpload", viewName);
        verify(houseService, times(1)).getById(anyLong());
        verify(model, times(1)).addAttribute(eq("house"), houseCaptor.capture());

        mockMvc.perform(get("/house/1/image")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("house/houseImageUpload"));
    }

    @Test
    void renderHouseImage() throws Exception {
        //given
        FacilityCommand command = new FacilityCommand();
        command.setId(1L);
        command.setImage("HouseImageStub".getBytes());

        when(houseService.findCommandById(anyLong())).thenReturn(command);

        //when
        MockHttpServletResponse response = mockMvc.perform(get("/house/1/houseimage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        //then
        assertEquals(command.getImage().length, response.getContentAsByteArray().length);
    }

    @Test
    void saveHouseImage() throws Exception {
        //given
        MockMultipartFile multipartFile = new MockMultipartFile("imagefile","testing.txt",
                "text/plain", "HouseImageStub".getBytes());

        //when
        String viewName = controller.saveHouseImage("1", multipartFile);

        //then
        assertEquals("redirect:/house/1/show", viewName);
        verify(houseService, times(1)).saveImage(1L, multipartFile);

        mockMvc.perform(multipart("/house/1/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/house/1/show"));
    }

}