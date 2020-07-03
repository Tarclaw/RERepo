package web.example.realestate.services.implementation;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.converters.GarageCommandToGarage;
import web.example.realestate.converters.GarageToGarageCommand;
import web.example.realestate.domain.building.Garage;
import web.example.realestate.domain.people.Client;
import web.example.realestate.repositories.ClientRepository;
import web.example.realestate.repositories.GarageRepository;
import web.example.realestate.services.GarageService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class GarageServiceImpl implements GarageService {

    private final GarageRepository garageRepository;
    private final ClientRepository clientRepository;
    private final GarageCommandToGarage toGarage;
    private final GarageToGarageCommand toGarageCommand;

    public GarageServiceImpl(GarageRepository garageRepository, ClientRepository clientRepository,
                             GarageCommandToGarage toGarage, GarageToGarageCommand toGarageCommand) {
        this.garageRepository = garageRepository;
        this.clientRepository = clientRepository;
        this.toGarage = toGarage;
        this.toGarageCommand = toGarageCommand;
    }

    @Override
    public Garage getById(final Long id) {
        return garageRepository.findGaragesByIdWithClients(id)
                .orElseThrow(
                        () -> new RuntimeException("We don't have garage with id=" + id)
                );
    }

    @Override
    public Set<Garage> getGarages() {
        Set<Garage> garages = new HashSet<>();
        garageRepository.findAll().iterator().forEachRemaining(garages :: add);
        return garages;
    }

    @Override
    @Transactional
    public FacilityCommand findCommandById(Long id) {
        return toGarageCommand.convert(getById(id));
    }

    @Override
    @Transactional
    public FacilityCommand saveDetached(final FacilityCommand command) {
        Client client = clientRepository.findById(command.getClientId()).get();
        Garage detachedGarage = toGarage.convert(command);
        detachedGarage.setClient(client);
        Garage savedGarage = garageRepository.save(detachedGarage);
        System.out.println("Save Garage with id=" + savedGarage.getId());
        return toGarageCommand.convert(savedGarage);
    }

    @Override
    @Transactional
    public FacilityCommand saveAttached(final FacilityCommand command) {
        Client client = clientRepository.findById(command.getClientId()).get();
        Garage attachedGarage = getById(command.getId());
        Garage updatedGarage = toGarage.convertWhenAttached(attachedGarage, command);
        updatedGarage.setClient(client);
        System.out.println("Update Garage with id=" + updatedGarage.getId());
        return toGarageCommand.convert(updatedGarage);
    }

    @Override
    public void deleteById(Long id) {
        garageRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void saveImage(Long id, MultipartFile multipartFile) {
        try {
            Garage garage = garageRepository.findById(id).get();
            garage.setImage(multipartFile.getBytes());
            garageRepository.save(garage);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
