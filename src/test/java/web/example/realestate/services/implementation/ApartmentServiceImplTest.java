package web.example.realestate.services.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.converters.ApartmentCommandToApartment;
import web.example.realestate.converters.ApartmentToApartmentCommand;
import web.example.realestate.domain.building.Apartment;
import web.example.realestate.exceptions.ImageCorruptedException;
import web.example.realestate.exceptions.NotFoundException;
import web.example.realestate.repositories.ApartmentRepository;
import web.example.realestate.repositories.ClientRepository;
import web.example.realestate.services.ApartmentService;

import java.io.ByteArrayInputStream;
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

class ApartmentServiceImplTest {

    private ApartmentService service;

    @Mock
    private ApartmentRepository apartmentRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ApartmentToApartmentCommand toApartmentCommand;

    @Mock
    private ApartmentCommandToApartment toApartment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new ApartmentServiceImpl(apartmentRepository, clientRepository, toApartmentCommand, toApartment);
    }

    @Test
    void getById() {
        Apartment apartment = new Apartment();
        apartment.setId(1L);
        Optional<Apartment> source = Optional.of(apartment);
        when(apartmentRepository.findApartmentsByIdWithClients(anyLong())).thenReturn(source);

        Apartment apartmentFromRepo = service.getById(1L);

        assertNotNull(apartmentFromRepo);
        assertEquals(1L, apartmentFromRepo.getId());
        verify(apartmentRepository, times(1)).findApartmentsByIdWithClients(anyLong());
    }

    @Test
    void getByIdWhenThereIsNoApartmentInDB() {
        assertThrows(NotFoundException.class, () -> service.getById(anyLong()));
    }

    @Test
    void getApartments() {
        when(service.getApartments())
                .thenReturn(new HashSet<>(Collections.singletonList(new Apartment())));

        Set<Apartment> apartments = service.getApartments();

        assertEquals(1, apartments.size());
        verify(apartmentRepository, times(1)).findAll();
    }

    @Test
    void findCommandById() {
        //given
        when(apartmentRepository.findApartmentsByIdWithClients(anyLong())).thenReturn(Optional.of(new Apartment()));

        FacilityCommand sourceCommand = new FacilityCommand();
        sourceCommand.setId(1L);
        when(toApartmentCommand.convert(any())).thenReturn(sourceCommand);

        //when
        FacilityCommand command = service.findCommandById(1L);

        //then
        assertEquals(1L, command.getId());
        verify(apartmentRepository, times(1)).findApartmentsByIdWithClients(anyLong());
        verify(toApartmentCommand, times(1)).convert(any());
    }

    @Test
    void deleteById() {
        service.deleteById(1L);
        verify(apartmentRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void saveImage() throws IOException {
        //given
        Long id = 1L;
        Apartment apartment = new Apartment();
        apartment.setId(id);

        when(apartmentRepository.findById(id)).thenReturn(Optional.of(apartment));

        MockMultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt",
                "text/plain", "ApartmentImage".getBytes());
        ArgumentCaptor<Apartment> apartmentCaptor = ArgumentCaptor.forClass(Apartment.class);

        //when
        service.saveImage(id, multipartFile);

        //then
        verify(apartmentRepository, times(1)).save(apartmentCaptor.capture());
        Apartment saved = apartmentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, saved.getImage().length);
    }

}