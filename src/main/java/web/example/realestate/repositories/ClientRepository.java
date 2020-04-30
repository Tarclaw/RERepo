package web.example.realestate.repositories;

import org.springframework.data.repository.CrudRepository;
import web.example.realestate.domain.people.Client;

import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, Long> {

    Optional<Client> findClientsByLastName(String lastName);

}
