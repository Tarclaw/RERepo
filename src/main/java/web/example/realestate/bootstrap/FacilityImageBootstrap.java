package web.example.realestate.bootstrap;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import web.example.realestate.domain.building.Facility;
import web.example.realestate.exceptions.ImageCorruptedException;
import web.example.realestate.exceptions.NotFoundException;
import web.example.realestate.repositories.FacilityRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Component
public class FacilityImageBootstrap {

    private final FacilityRepository facilityRepository;

    public FacilityImageBootstrap(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    void saveImageInDB(Long id, String path) {
        try {
            File file = ResourceUtils.getFile(path);
            byte[] bytes = Files.readAllBytes(file.toPath());

            Facility facility = facilityRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("there is no entity with id=" + id + " in db"));
            facility.setImage(bytes);

            facilityRepository.save(facility);
        } catch (IOException e) {
            throw new ImageCorruptedException(e.getMessage());
        }
    }
}
