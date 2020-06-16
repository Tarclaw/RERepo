package web.example.realestate.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import web.example.realestate.domain.people.Client;

import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, Long> {

    Optional<Client> findClientsByLastName(String lastName);

    @Query("select c from Client c join fetch c.facilities f join fetch c.realEstateAgents a where c.id = ?1")
    Optional<Client> findClientByIdWithFacilitiesAndAgents(Long id);

}
