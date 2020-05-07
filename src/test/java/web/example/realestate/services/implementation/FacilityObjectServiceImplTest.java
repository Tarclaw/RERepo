package web.example.realestate.services.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import web.example.realestate.commands.FacilityObjectCommand;
import web.example.realestate.converters.FacilityObjectCommandToFacilityObject;
import web.example.realestate.converters.FacilityObjectToFacilityObjectCommand;
import web.example.realestate.domain.building.FacilityObject;
import web.example.realestate.repositories.FacilityObjectRepository;
import web.example.realestate.services.FacilityObjectService;

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

class FacilityObjectServiceImplTest {

    private FacilityObjectService service;

    @Mock
    private FacilityObjectRepository repository;

    @Mock
    private FacilityObjectCommandToFacilityObject toFacilityObject;

    @Mock
    private FacilityObjectToFacilityObjectCommand toFacilityObjectCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new FacilityObjectServiceImpl(repository, toFacilityObject, toFacilityObjectCommand);
    }

    @Test
    void getById() {
        //given
        FacilityObject source = new FacilityObject();
        source.setId(1L);
        when(repository.findById(anyLong())).thenReturn(Optional.of(source));

        //when
        FacilityObject facilityObject = service.getById(1L);

        //then
        assertNotNull(facilityObject);
        assertEquals(source.getId(), facilityObject.getId());
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void getByIdExceptionHandling() {
        assertThrows(RuntimeException.class, () -> service.getById(anyLong()));
    }

    @Test
    void getFacilityObjects() {
        when(service.getFacilityObjects()).
                thenReturn(new HashSet<>(Collections.singletonList(new FacilityObject())));

        Set<FacilityObject> facilityObjects = service.getFacilityObjects();

        assertEquals(1, facilityObjects.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void findCommandById() {
        //given
        FacilityObjectCommand source = new FacilityObjectCommand();
        source.setId(1L);
        when(repository.findById(anyLong())).thenReturn(Optional.of(new FacilityObject()));
        when(toFacilityObjectCommand.convert(any())).thenReturn(source);

        //when
        FacilityObjectCommand command = service.findCommandById(1L);

        //then
        assertNotNull(command);
        assertEquals(source.getId(), command.getId());
        verify(repository, times(1)).findById(anyLong());
        verify(toFacilityObjectCommand, times(1)).convert(any());
    }

    @Test
    void deleteById() {
        service.deleteById(anyLong());
        verify(repository, times(1)).deleteById(anyLong());
    }
}