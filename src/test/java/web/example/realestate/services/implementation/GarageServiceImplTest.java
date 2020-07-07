package web.example.realestate.services.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.converters.GarageCommandToGarage;
import web.example.realestate.converters.GarageToGarageCommand;
import web.example.realestate.domain.building.Garage;
import web.example.realestate.exceptions.NotFoundException;
import web.example.realestate.repositories.ClientRepository;
import web.example.realestate.repositories.GarageRepository;
import web.example.realestate.services.GarageService;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GarageServiceImplTest {

    private GarageService service;

    @Mock
    private GarageRepository garageRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private GarageCommandToGarage toGarage;

    @Mock
    private GarageToGarageCommand toGarageCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new GarageServiceImpl(garageRepository, clientRepository, toGarage, toGarageCommand);
    }

    @Test
    void getById() {
        //given
        Garage source = new Garage();
        source.setId(1L);
        when(garageRepository.findGaragesByIdWithClients(anyLong())).thenReturn(Optional.of(source));

        //when
        Garage garage = service.getById(1L);

        //then
        assertNotNull(garage);
        assertEquals(1L, garage.getId());
        verify(garageRepository, times(1)).findGaragesByIdWithClients(anyLong());
    }

    @Test
    void getByIdExceptionHandling() {
        assertThrows(NotFoundException.class, () -> service.getById(anyLong()));
    }

    @Test
    void getGarages() {
        when(service.getGarages()).
                thenReturn(new HashSet<>(Collections.singletonList(new Garage())));

        Set<Garage> garages = service.getGarages();

        assertEquals(1, garages.size());
        verify(garageRepository, times(1)).findAll();
    }

    @Test
    void findCommandById() {
        //given
        when(garageRepository.findGaragesByIdWithClients(anyLong())).thenReturn(Optional.of(new Garage()));
        FacilityCommand source = new FacilityCommand();
        source.setId(1L);
        when(toGarageCommand.convert(any())).thenReturn(source);

        //when
        FacilityCommand command = service.findCommandById(1L);

        //then
        assertNotNull(command);
        assertEquals(1L, command.getId());
        verify(garageRepository, times(1)).findGaragesByIdWithClients(anyLong());
        verify(toGarageCommand, times(1)).convert(any());
    }

    @Test
    void deleteById() {
        service.deleteById(anyLong());
        verify(garageRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void saveImage() throws IOException {
        //given
        Long id = 1L;
        Garage garage = new Garage();
        garage.setId(id);

        when(garageRepository.findById(id)).thenReturn(Optional.of(garage));

        ArgumentCaptor<Garage> garageCaptor = ArgumentCaptor.forClass(Garage.class);
        MockMultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt",
                "text/plain", "GarageImage".getBytes());

        //when
        service.saveImage(id, multipartFile);

        //then
        verify(garageRepository, times(1)).save(garageCaptor.capture());
        Garage saved = garageCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, saved.getImage().length);
    }
}