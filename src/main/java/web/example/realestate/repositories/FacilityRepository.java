package web.example.realestate.repositories;

import org.springframework.data.repository.CrudRepository;
import web.example.realestate.domain.building.Facility;

import java.util.Optional;

public interface FacilityRepository extends CrudRepository<Facility, Long> {

    Optional<Facility> findFacilitiesByDescription(String description);

}
