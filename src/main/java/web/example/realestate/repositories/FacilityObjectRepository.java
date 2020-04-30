package web.example.realestate.repositories;

import org.springframework.data.repository.CrudRepository;
import web.example.realestate.domain.building.FacilityObject;

import java.math.BigInteger;
import java.util.Optional;

public interface FacilityObjectRepository extends CrudRepository<FacilityObject, Long> {

    Optional<FacilityObject> findFacilityObjectsByPriceIsLessThan(BigInteger price);

}
