package web.example.realestate.repositories;

import org.springframework.data.repository.CrudRepository;
import web.example.realestate.domain.building.Apartment;

public interface ApartmentRepository extends CrudRepository<Apartment, Long> {
}
