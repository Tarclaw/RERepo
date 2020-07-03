package web.example.realestate.services;

import org.springframework.web.multipart.MultipartFile;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Garage;

import java.util.Set;

public interface GarageService {

    Garage getById(Long id);

    Set<Garage> getGarages();

    FacilityCommand findCommandById(Long id);

    FacilityCommand saveDetached(FacilityCommand command);

    FacilityCommand saveAttached(FacilityCommand command);

    void deleteById(Long id);

    void saveImage(Long id, MultipartFile multipartFile);
}
