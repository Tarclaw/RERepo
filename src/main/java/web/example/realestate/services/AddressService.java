package web.example.realestate.services;

import web.example.realestate.domain.building.Address;
import java.util.List;

public interface AddressService {
    Address getById(Long id);
    List<Address> getAddresses();
}
