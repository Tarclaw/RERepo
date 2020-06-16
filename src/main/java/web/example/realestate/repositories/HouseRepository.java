package web.example.realestate.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import web.example.realestate.domain.building.House;

import java.util.Optional;

public interface HouseRepository extends CrudRepository<House, Long> {

    Optional<House> findHousesByHasGarden(Boolean hasGarden);

    @Query("select h from House h join fetch h.client c where h.id = ?1")
    Optional<House> findHousesByIdWithClients(Long id);

}
