package web.example.realestate.repositories;

import org.springframework.data.repository.CrudRepository;
import web.example.realestate.domain.building.Basement;

public interface BasementRepository extends CrudRepository<Basement, Long> {
}
