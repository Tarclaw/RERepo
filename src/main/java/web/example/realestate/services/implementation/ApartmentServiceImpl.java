package web.example.realestate.services.implementation;

import org.springframework.stereotype.Service;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.converters.ApartmentCommandToApartment;
import web.example.realestate.converters.ApartmentToApartmentCommand;
import web.example.realestate.domain.building.Apartment;
import web.example.realestate.domain.people.Client;
import web.example.realestate.repositories.ApartmentRepository;
import web.example.realestate.repositories.ClientRepository;
import web.example.realestate.services.ApartmentService;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    private final ApartmentRepository apartmentRepository;
    private final ClientRepository clientRepository;
    private final ApartmentToApartmentCommand toApartmentCommand;
    private final ApartmentCommandToApartment toApartment;

    public ApartmentServiceImpl(ApartmentRepository apartmentRepository,
                                ClientRepository clientRepository,
                                ApartmentToApartmentCommand toApartmentCommand,
                                ApartmentCommandToApartment toApartment) {
        this.apartmentRepository = apartmentRepository;
        this.clientRepository = clientRepository;
        this.toApartmentCommand = toApartmentCommand;
        this.toApartment = toApartment;
    }

    @Override
    public Apartment getById(final Long id) {
        return apartmentRepository.findApartmentsByIdWithClients(id)
                .orElseThrow(
                        () -> new RuntimeException("We don't have apartment with id=" + id)
                );
    }

    @Override
    public Set<Apartment> getApartments() {
        Set<Apartment> apartments = new HashSet<>();
        apartmentRepository.findAll().iterator().forEachRemaining(apartments :: add);
        return apartments;
    }

    @Override
    @Transactional
    public FacilityCommand findCommandById(final Long id) {
        return toApartmentCommand.convert(getById(id));
    }

    @Override
    @Transactional
    public FacilityCommand saveDetached(final FacilityCommand command) {
        Client client = clientRepository.findById(command.getClientId()).get();
        Apartment detachedApartment = toApartment.convert(command);
        detachedApartment.setClient(client);
        Apartment savedApartment = apartmentRepository.save(detachedApartment);
        System.out.println("Save Apartment with id=" + savedApartment.getId());
        return toApartmentCommand.convert(savedApartment);
    }

    @Override
    @Transactional
    public FacilityCommand saveAttached(final FacilityCommand command) {
        Client client = clientRepository.findById(command.getClientId()).get();
        Apartment attachedApartment = getById(command.getId());
        Apartment updatedApartment = toApartment.convertWhenAttached(attachedApartment, command);
        updatedApartment.setClient(client);
        System.out.println("Update Apartment with id=" + updatedApartment.getId());
        return toApartmentCommand.convert(updatedApartment);
    }

    @Override
    public void deleteById(final Long id) {
        apartmentRepository.deleteById(id);
    }
}
