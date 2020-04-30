package web.example.realestate.repositories;

import org.springframework.data.repository.CrudRepository;
import web.example.realestate.domain.building.Apartment;

import java.util.Optional;

public interface ApartmentRepository extends CrudRepository<Apartment, Long> {

    Optional<Apartment> findApartmentsByTotalArea(Integer totalArea);

}
