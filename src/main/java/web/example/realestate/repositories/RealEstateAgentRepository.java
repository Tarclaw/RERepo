package web.example.realestate.repositories;

import org.springframework.data.repository.CrudRepository;
import web.example.realestate.domain.people.RealEstateAgent;

import java.util.Optional;

public interface RealEstateAgentRepository extends CrudRepository<RealEstateAgent, Long> {

    Optional<RealEstateAgent> findRealEstateAgentsByFirstNameAndLastName(String firstName, String lastName);

}
