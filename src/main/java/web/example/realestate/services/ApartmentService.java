package web.example.realestate.services;

import org.springframework.web.multipart.MultipartFile;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.building.Apartment;
import web.example.realestate.exceptions.ImageCorruptedException;

import java.util.Set;

public interface ApartmentService {

    Apartment getById(Long id);

    Set<Apartment> getApartments();

    FacilityCommand findCommandById(Long id);

    FacilityCommand saveDetached(FacilityCommand command);

    FacilityCommand saveAttached(FacilityCommand command);

    void deleteById(Long id);

    void saveImage(Long id, MultipartFile multipartFile);
}
