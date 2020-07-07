package web.example.realestate.services.implementation;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.converters.HouseCommandToHouse;
import web.example.realestate.converters.HouseToHouseCommand;
import web.example.realestate.domain.building.House;
import web.example.realestate.domain.people.Client;
import web.example.realestate.exceptions.ImageCorruptedException;
import web.example.realestate.exceptions.NotFoundException;
import web.example.realestate.repositories.ClientRepository;
import web.example.realestate.repositories.HouseRepository;
import web.example.realestate.services.HouseService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;
    private final ClientRepository clientRepository;
    private final HouseCommandToHouse toHouse;
    private final HouseToHouseCommand toHouseCommand;

    public HouseServiceImpl(HouseRepository houseRepository, ClientRepository clientRepository,
                            HouseCommandToHouse toHouse, HouseToHouseCommand toHouseCommand) {
        this.houseRepository = houseRepository;
        this.clientRepository = clientRepository;
        this.toHouse = toHouse;
        this.toHouseCommand = toHouseCommand;
    }

    @Override
    public House getById(final Long id) {
        return houseRepository.findHousesByIdWithClients(id)
                .orElseThrow(
                        () -> new NotFoundException("We don't have house with id=" + id)
                );
    }

    @Override
    public Set<House> getHouses() {
        Set<House> houses = new HashSet<>();
        houseRepository.findAll().iterator().forEachRemaining(houses :: add);
        return houses;
    }

    @Override
    @Transactional
    public FacilityCommand findCommandById(Long id) {
        return toHouseCommand.convert(getById(id));
    }

    @Override
    @Transactional
    public FacilityCommand saveDetached(final FacilityCommand command) {
        Client client = clientRepository.findById(command.getClientId()).get();
        House detachedHouse = toHouse.convert(command);
        detachedHouse.setClient(client);
        House savedHouse = houseRepository.save(detachedHouse);
        System.out.println("Save House with id=" + savedHouse.getId());
        return toHouseCommand.convert(savedHouse);
    }

    @Override
    @Transactional
    public FacilityCommand saveAttached(final FacilityCommand command) {
        Client client = clientRepository.findById(command.getClientId()).get();
        House attachedHouse = getById(command.getId());
        House updatedHouse = toHouse.convertWhenAttached(attachedHouse, command);
        updatedHouse.setClient(client);
        System.out.println("Update House with id=" + updatedHouse.getId());
        return toHouseCommand.convert(updatedHouse);
    }

    @Override
    public void deleteById(Long id) {
        houseRepository.deleteById(id);
    }

    @Override
    public void saveImage(Long id, MultipartFile multipartFile) {
        try {
            House house = houseRepository.findById(id).get();
            house.setImage(multipartFile.getBytes());
            houseRepository.save(house);
        } catch (IOException e) {
            throw new ImageCorruptedException(e.getMessage());
        }
    }
}
