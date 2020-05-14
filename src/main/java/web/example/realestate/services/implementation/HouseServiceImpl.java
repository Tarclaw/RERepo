package web.example.realestate.services.implementation;

import org.springframework.stereotype.Service;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.converters.HouseCommandToHouse;
import web.example.realestate.converters.HouseToHouseCommand;
import web.example.realestate.domain.building.House;
import web.example.realestate.repositories.HouseRepository;
import web.example.realestate.services.HouseService;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class HouseServiceImpl implements HouseService {

    private final HouseRepository repository;
    private final HouseCommandToHouse toHouse;
    private final HouseToHouseCommand toHouseCommand;

    public HouseServiceImpl(HouseRepository repository,
                            HouseCommandToHouse toHouse,
                            HouseToHouseCommand toHouseCommand) {
        this.repository = repository;
        this.toHouse = toHouse;
        this.toHouseCommand = toHouseCommand;
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

    @Override
    @Transactional
    public FacilityCommand findCommandById(Long id) {
        return toHouseCommand.convert(getById(id));
    }

    @Override
    @Transactional
    public FacilityCommand saveHouseCommand(FacilityCommand command) {
        House detachedHouse = toHouse.convert(command);
        House savedHouse = repository.save(detachedHouse);
        System.out.println("Save House with id=" + savedHouse.getId());
        return toHouseCommand.convert(savedHouse);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
