package web.example.realestate.services.implementation;

import org.springframework.stereotype.Service;
import web.example.realestate.domain.building.Apartment;
import web.example.realestate.repositories.ApartmentRepository;
import web.example.realestate.services.ApartmentService;

import java.util.HashSet;
import java.util.Set;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    private final ApartmentRepository repository;

    public ApartmentServiceImpl(ApartmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Apartment getById(final Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("We don't have apartment with id=" + id)
                );
    }

    @Override
    public Set<Apartment> getApartments() {
        Set<Apartment> apartments = new HashSet<>();
        repository.findAll().iterator().forEachRemaining(apartments :: add);
        return apartments;
    }
}
