package web.example.realestate.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import web.example.realestate.domain.building.Storage;

import java.util.Optional;

public interface StorageRepository extends CrudRepository<Storage, Long> {

    Optional<Storage> findStoragesByCommercialCapacityGreaterThan(Integer commercialCapacity);

    @Query("select s from Storage s join fetch s.clients where s.id = ?1")
    Optional<Storage> findStoragesByIdWithClients(Long id);

}
