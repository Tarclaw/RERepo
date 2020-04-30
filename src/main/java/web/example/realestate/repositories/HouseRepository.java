package web.example.realestate.repositories;

import org.springframework.data.repository.CrudRepository;
import web.example.realestate.domain.building.House;

import java.util.Optional;

public interface HouseRepository extends CrudRepository<House, Long> {

    Optional<House> findHousesByHasGarden(Boolean hasGarden);

}
