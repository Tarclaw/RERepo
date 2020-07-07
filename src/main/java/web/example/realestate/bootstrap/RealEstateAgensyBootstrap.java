package web.example.realestate.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import web.example.realestate.domain.building.Facility;
import web.example.realestate.repositories.FacilityRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class RealEstateAgensyBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private static final String APARTMENT_PATH = "/home/andrew/IdeaProjects/realestate/src/main/resources/static/images/apartment.jpg";
    private static final String BASEMENT_PATH = "/home/andrew/IdeaProjects/realestate/src/main/resources/static/images/basement.jpg";
    private static final String GARAGE_PATH = "/home/andrew/IdeaProjects/realestate/src/main/resources/static/images/garage.jpg";
    private static final String HOUSE_PATH = "/home/andrew/IdeaProjects/realestate/src/main/resources/static/images/house.jpg";
    private static final String STORAGE_PATH = "/home/andrew/IdeaProjects/realestate/src/main/resources/static/images/storage.jpg";

    private final FacilityRepository facilityRepository;

    public RealEstateAgensyBootstrap(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initFacilitiesImages();
    }

    private void initFacilitiesImages() {

        try {
            Facility apartment = facilityRepository.findById(1L)
                    .orElseThrow(() -> new RuntimeException("there is no entity with id=1 in db"));
            Facility basement = facilityRepository.findById(2L)
                    .orElseThrow(() -> new RuntimeException("there is no entity with id=2 in db"));
            Facility garage = facilityRepository.findById(3L)
                    .orElseThrow(() -> new RuntimeException("there is no entity with id=3 in db"));
            Facility house = facilityRepository.findById(4L)
                    .orElseThrow(() -> new RuntimeException("there is no entity with id=4 in db"));
            Facility storage = facilityRepository.findById(5L)
                    .orElseThrow(() -> new RuntimeException("there is no entity with id=5 in db"));

            apartment.setImage(Files.readAllBytes(Paths.get(APARTMENT_PATH)));
            basement.setImage(Files.readAllBytes(Paths.get(BASEMENT_PATH)));
            garage.setImage(Files.readAllBytes(Paths.get(GARAGE_PATH)));
            house.setImage(Files.readAllBytes(Paths.get(HOUSE_PATH)));
            storage.setImage(Files.readAllBytes(Paths.get(STORAGE_PATH)));

            facilityRepository.save(apartment);
            facilityRepository.save(basement);
            facilityRepository.save(garage);
            facilityRepository.save(house);
            facilityRepository.save(storage);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
