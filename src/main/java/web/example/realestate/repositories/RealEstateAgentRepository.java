package web.example.realestate.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import web.example.realestate.domain.people.RealEstateAgent;

import java.util.Optional;

public interface RealEstateAgentRepository extends CrudRepository<RealEstateAgent, Long> {

    Optional<RealEstateAgent> findRealEstateAgentsByFirstNameAndLastName(String firstName, String lastName);

    @Query("select r from RealEstateAgent r join fetch r.clients join fetch r.facilityObjects where r.id = ?1")
    Optional<RealEstateAgent> findRealEstateAgentsByIdWithEntities(Long id);

}
