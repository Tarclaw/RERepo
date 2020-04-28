package web.example.realestate.repositories;

import org.springframework.data.repository.CrudRepository;
import web.example.realestate.domain.building.Storage;

public interface StorageRepository extends CrudRepository<Storage, Long> {
}
