package web.example.realestate.repositories;

import org.springframework.data.repository.CrudRepository;
import web.example.realestate.domain.building.Garage;

public interface GarageRepository extends CrudRepository<Garage, Long> {
}
