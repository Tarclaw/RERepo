package web.example.realestate.services.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.converters.ApartmentCommandToApartment;
import web.example.realestate.converters.ApartmentToApartmentCommand;
import web.example.realestate.domain.building.Apartment;
import web.example.realestate.repositories.ApartmentRepository;
import web.example.realestate.services.ApartmentService;

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
    private ApartmentRepository repository;

    @Mock
    private ApartmentToApartmentCommand toApartmentCommand;

    @Mock
    private ApartmentCommandToApartment toApartment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new ApartmentServiceImpl(repository, toApartmentCommand, toApartment);
    }

    @Test
    void getById() {
        Apartment apartment = new Apartment();
        apartment.setId(1L);
        Optional<Apartment> source = Optional.of(apartment);
        when(repository.findApartmentsByIdWithClients(anyLong())).thenReturn(source);

        Apartment apartmentFromRepo = service.getById(1L);

        assertNotNull(apartmentFromRepo);
        assertEquals(1L, apartmentFromRepo.getId());
        verify(repository, times(1)).findApartmentsByIdWithClients(anyLong());
    }

    @Test
    void getByIdWhenThereIsNoApartmentInDB() {
        assertThrows(RuntimeException.class, () -> service.getById(anyLong()));
    }

    @Test
    void getApartments() {
        when(service.getApartments())
                .thenReturn(new HashSet<>(Collections.singletonList(new Apartment())));

        Set<Apartment> apartments = service.getApartments();

        assertEquals(1, apartments.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void findCommandById() {
        //given
        when(repository.findApartmentsByIdWithClients(anyLong())).thenReturn(Optional.of(new Apartment()));

        FacilityCommand sourceCommand = new FacilityCommand();
        sourceCommand.setId(1L);
        when(toApartmentCommand.convert(any())).thenReturn(sourceCommand);

        //when
        FacilityCommand command = service.findCommandById(1L);

        //then
        assertEquals(1L, command.getId());
        verify(repository, times(1)).findApartmentsByIdWithClients(anyLong());
        verify(toApartmentCommand, times(1)).convert(any());
    }

    @Test
    void deleteById() {
        service.deleteById(1L);
        verify(repository, times(1)).deleteById(anyLong());
    }
}