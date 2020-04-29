package web.example.realestate.services.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import web.example.realestate.domain.building.Address;
import web.example.realestate.repositories.AddressRepository;
import web.example.realestate.services.AddressService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AddressServiceImplTest {

    private AddressService service;

    @Mock
    private AddressRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new AddressServiceImpl(repository);
    }

    @Test
    void getAddresses() {
        when(service.getAddresses()).thenReturn(Collections.singletonList(new Address()));
        List<Address> addresses = service.getAddresses();
        assertEquals(1, addresses.size());
        verify(repository, times(1)).findAll();
    }
}