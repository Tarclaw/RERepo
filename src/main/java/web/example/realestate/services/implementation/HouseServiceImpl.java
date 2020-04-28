package web.example.realestate.services.implementation;

import org.springframework.stereotype.Service;
import web.example.realestate.domain.building.House;
import web.example.realestate.repositories.HouseRepository;
import web.example.realestate.services.HouseService;

import java.util.HashSet;
import java.util.Set;

@Service
public class HouseServiceImpl implements HouseService {

    private final HouseRepository repository;

    public HouseServiceImpl(HouseRepository repository) {
        this.repository = repository;
    }

    @Override
    public House getById(final Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("We don't have house with id=" + id)
                );
    }

    @Override
    public Set<House> getHouses() {
        Set<House> houses = new HashSet<>();
        repository.findAll().iterator().forEachRemaining(houses :: add);
        return houses;
    }
}
