package web.example.realestate.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import web.example.realestate.domain.building.Apartment;

import java.util.Optional;

public interface ApartmentRepository extends CrudRepository<Apartment, Long> {

    Optional<Apartment> findApartmentsByTotalArea(Integer totalArea);

    @Query("select a from Apartment a join fetch a.clients c where a.id = ?1")
    Optional<Apartment> findApartmentsByIdWithClients(Long id);

}
