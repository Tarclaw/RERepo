package web.example.realestate.repositories;

import org.springframework.data.repository.CrudRepository;
import web.example.realestate.domain.building.FacilityObject;

public interface FacilityObjectRepository extends CrudRepository<FacilityObject, Long> {
}
