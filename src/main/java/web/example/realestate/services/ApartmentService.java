package web.example.realestate.services;

import web.example.realestate.domain.building.Apartment;

import java.util.Set;

public interface ApartmentService {
    Apartment getById(Long id);
    Set<Apartment> getApartments();
}
