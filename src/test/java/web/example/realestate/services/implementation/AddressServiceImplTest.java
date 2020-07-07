package web.example.realestate.services.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import web.example.realestate.commands.AddressCommand;
import web.example.realestate.converters.AddressCommandToAddress;
import web.example.realestate.converters.AddressToAddressCommand;
import web.example.realestate.domain.building.Address;
import web.example.realestate.exceptions.NotFoundException;
import web.example.realestate.repositories.AddressRepository;
import web.example.realestate.services.AddressService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AddressServiceImplTest {

    private AddressService service;

    @Mock
    private AddressRepository repository;

    @Mock
    private AddressCommandToAddress toAddress;

    @Mock
    private AddressToAddressCommand toAddressCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new AddressServiceImpl(repository, toAddressCommand, toAddress);
    }

    @Test
    void getById() {
        //given
        Optional<Address> sourceAddress = Optional.of(new Address());

        when(repository.findById(1L)).thenReturn(sourceAddress);

        //when
        Address address = service.getById(1L);

        //then
        assertNotNull(address);
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void getByIdWhenAddressDoesNotExist() {
        assertThrows(NotFoundException.class, () -> service.getById(anyLong()));
    }

    @Test
    void getAddresses() {
        //given
        when(service.getAddresses()).thenReturn(Collections.singletonList(new Address()));

        //when
        List<Address> addresses = service.getAddresses();

        //then
        assertEquals(1, addresses.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void findCommandById() {
        //given
        Optional<Address> sourceAddress = Optional.of(new Address());
        when(repository.findById(anyLong())).thenReturn(sourceAddress);

        AddressCommand sourceCommand = new AddressCommand();
        when(toAddressCommand.convert(any())).thenReturn(sourceCommand);

        //when
        AddressCommand command = service.findCommandById(1L);

        //then
        assertNotNull(command);
        verify(repository, times(1)).findById(anyLong());
        verify(toAddressCommand, times(1)).convert(any());
    }

    @Test
    void deleteById() {
        service.deleteById(anyLong());
        verify(repository, times(1)).deleteById(anyLong());
    }
}