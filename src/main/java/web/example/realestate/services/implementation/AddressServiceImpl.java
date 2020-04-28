package web.example.realestate.services.implementation;

import org.springframework.stereotype.Service;
import web.example.realestate.domain.building.Address;
import web.example.realestate.repositories.AddressRepository;
import web.example.realestate.services.AddressService;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository repository;

    public AddressServiceImpl(AddressRepository repository) {
        this.repository = repository;
    }

    @Override
    public Address getById(final Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("We don't have address with id="+id)
                );
    }

    @Override
    public List<Address> getAddresses() {
        List<Address> addresses = new ArrayList<>();
        repository.findAll().iterator().forEachRemaining(addresses :: add);
        return addresses;
    }
}
