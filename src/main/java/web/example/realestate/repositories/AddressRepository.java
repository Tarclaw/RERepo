package web.example.realestate.repositories;

import org.springframework.data.repository.CrudRepository;
import web.example.realestate.domain.building.Address;

public interface AddressRepository extends CrudRepository<Address, Long> {
}
