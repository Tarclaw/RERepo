package web.example.realestate.services.implementation;

import org.springframework.stereotype.Service;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.converters.ApartmentCommandToApartment;
import web.example.realestate.converters.ApartmentToApartmentCommand;
import web.example.realestate.domain.building.Apartment;
import web.example.realestate.repositories.ApartmentRepository;
import web.example.realestate.services.ApartmentService;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    private final ApartmentRepository repository;
    private final ApartmentToApartmentCommand toApartmentCommand;
    private final ApartmentCommandToApartment toApartment;

    public ApartmentServiceImpl(ApartmentRepository repository,
                                ApartmentToApartmentCommand toApartmentCommand,
                                ApartmentCommandToApartment toApartment) {
        this.repository = repository;
        this.toApartmentCommand = toApartmentCommand;
        this.toApartment = toApartment;
    }

    @Override
    public Apartment getById(final Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("We don't have apartment with id=" + id)
                );
    }

    @Override
    public Set<Apartment> getApartments() {
        Set<Apartment> apartments = new HashSet<>();
        repository.findAll().iterator().forEachRemaining(apartments :: add);
        return apartments;
    }

    @Override
    @Transactional
    public FacilityCommand findCommandById(final Long id) {
        return toApartmentCommand.convert(getById(id));
    }

    @Override
    @Transactional
    public FacilityCommand saveApartmentCommand(final FacilityCommand command) {
        return command.getId() == null ? saveDetached(command) : saveAttached(command);
    }

    private FacilityCommand saveDetached(final FacilityCommand command) {
        Apartment detachedApartment = toApartment.convert(command);
        Apartment savedApartment = repository.save(detachedApartment);
        System.out.println("Save Apartment with id=" + savedApartment.getId());
        return toApartmentCommand.convert(savedApartment);
    }

    private FacilityCommand saveAttached(final FacilityCommand command) {
        Apartment attachedApartment = getById(command.getId());
        Apartment updatedApartment = toApartment.convertWhenAttached(attachedApartment, command);
        System.out.println("Update Apartment with id=" + updatedApartment.getId());
        return toApartmentCommand.convert(updatedApartment);
    }

    @Override
    public void deleteById(final Long id) {
        repository.deleteById(id);
    }
}
