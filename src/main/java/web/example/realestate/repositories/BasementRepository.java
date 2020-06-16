package web.example.realestate.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import web.example.realestate.domain.building.Basement;

import java.util.Optional;

public interface BasementRepository extends CrudRepository<Basement, Long> {

    Optional<Basement> findBasementsByItCommercial(Boolean itCommercial);

    @Query("select b from Basement b join fetch b.client c where b.id = ?1")
    Optional<Basement> findBasementByIdWithClients(Long id);

}
