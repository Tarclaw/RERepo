package web.example.realestate.repositories;

import org.springframework.data.repository.CrudRepository;
import web.example.realestate.domain.people.RealEstateAgent;

public interface RealEstateAgentRepository extends CrudRepository<RealEstateAgent, Long> {
}
