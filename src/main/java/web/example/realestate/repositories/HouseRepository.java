package web.example.realestate.repositories;

import org.springframework.data.repository.CrudRepository;
import web.example.realestate.domain.building.House;

public interface HouseRepository extends CrudRepository<House, Long> {
}
