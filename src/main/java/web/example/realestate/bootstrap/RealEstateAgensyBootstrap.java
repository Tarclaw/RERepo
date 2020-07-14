package web.example.realestate.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@Profile("default")
public class RealEstateAgensyBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private static final String APARTMENT_PATH = "/home/andrew/IdeaProjects/realestate/src/main/resources/static/images/NY/apartment.jpg";
    private static final String BASEMENT_PATH = "/home/andrew/IdeaProjects/realestate/src/main/resources/static/images/NY/basement.jpg";
    private static final String GARAGE_PATH = "/home/andrew/IdeaProjects/realestate/src/main/resources/static/images/NY/garage.jpg";
    private static final String HOUSE_PATH = "/home/andrew/IdeaProjects/realestate/src/main/resources/static/images/NY/house.jpg";
    private static final String STORAGE_PATH = "/home/andrew/IdeaProjects/realestate/src/main/resources/static/images/NY/storage.jpg";

    private final FacilityImageBootstrap facilityImageBootstrap;

    public RealEstateAgensyBootstrap(FacilityImageBootstrap facilityImageBootstrap) {
        this.facilityImageBootstrap = facilityImageBootstrap;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        facilityImageBootstrap.saveImageInDB(11L, APARTMENT_PATH);
        facilityImageBootstrap.saveImageInDB(12L, BASEMENT_PATH);
        facilityImageBootstrap.saveImageInDB(13L, GARAGE_PATH);
        facilityImageBootstrap.saveImageInDB(14L, HOUSE_PATH);
        facilityImageBootstrap.saveImageInDB(15L, STORAGE_PATH);
    }

}
