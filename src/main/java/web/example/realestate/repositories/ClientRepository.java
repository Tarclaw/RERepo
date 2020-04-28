package web.example.realestate.repositories;

import org.springframework.data.repository.CrudRepository;
import web.example.realestate.domain.people.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {
}
