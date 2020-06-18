package web.example.realestate.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import web.example.realestate.domain.building.Facility;

import java.util.List;

public interface FacilityRepository extends CrudRepository<Facility, Long> {

    @Query("select f.id, f.description from Facility f")
    List<Facility> findAllWithIdAndDescription();

}
