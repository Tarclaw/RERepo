package web.example.realestate.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(FacilityImageBootstrap.class);

    private final FacilityRepository facilityRepository;

    public FacilityImageBootstrap(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
        LOGGER.info("New instance of FacilityImageBootstrap created.");
    }

    void saveImageInDB(Long id, String path) {
        LOGGER.trace("Enter in 'saveImageInDB(Long id, String path)' method");

        try {
            File file = ResourceUtils.getFile(path);
            LOGGER.debug("Created file with path: " + path);

            byte[] bytes = Files.readAllBytes(file.toPath());
            LOGGER.debug("Created array from file");

            Facility facility = facilityRepository.findById(id)
                    .orElseThrow(() -> {
                        LOGGER.warn("We didn't found facility with id: " + id + " in db");
                        return new NotFoundException("Please choose different facility.");
                    });

            facility.setImage(bytes);
            LOGGER.debug("Image saved into facility");

            facilityRepository.save(facility);
            LOGGER.debug("Facility saved into DB");
        } catch (IOException e) {
            LOGGER.error("WTF somebody damaged our images!!!", e);
            throw new ImageCorruptedException(e.getMessage());
        }

        LOGGER.trace("'saveImageInDB(Long id, String path)' executed successfully.");
    }
}
