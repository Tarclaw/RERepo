package web.example.realestate.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import web.example.realestate.domain.building.Garage;

import java.util.Optional;

public interface GarageRepository extends CrudRepository<Garage, Long> {

    Optional<Garage> findGaragesByHasEquipment(Boolean hasEquipment);

    @Query("select g from Garage g join fetch g.client c where g.id = ?1")
    Optional<Garage> findGaragesByIdWithClients(Long id);

}
