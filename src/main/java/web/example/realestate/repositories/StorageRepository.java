package web.example.realestate.repositories;

import org.springframework.data.repository.CrudRepository;
import web.example.realestate.domain.building.Storage;

import java.util.Optional;

public interface StorageRepository extends CrudRepository<Storage, Long> {

    Optional<Storage> findStoragesByCommercialCapacityGreaterThan(Integer commercialCapacity);

}
