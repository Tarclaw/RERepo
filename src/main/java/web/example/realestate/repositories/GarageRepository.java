package web.example.realestate.repositories;

import org.springframework.data.repository.CrudRepository;
import web.example.realestate.domain.building.Garage;

import java.util.Optional;

public interface GarageRepository extends CrudRepository<Garage, Long> {

    Optional<Garage> findGaragesByHasEquipment(Boolean hasEquipment);

}
