package web.example.realestate.repositories;

import org.springframework.data.repository.CrudRepository;
import web.example.realestate.domain.building.Facility;

public interface FacilityRepository extends CrudRepository<Facility, Long> {
}
