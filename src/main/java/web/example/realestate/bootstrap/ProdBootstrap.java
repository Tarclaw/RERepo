package web.example.realestate.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class ProdBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private static final String APARTMENT_PATH = "/home/andrew/IdeaProjects/realestate/src/main/resources/static/images/CA/apartment.jpg";
    private static final String BASEMENT_PATH = "/home/andrew/IdeaProjects/realestate/src/main/resources/static/images/CA/basement.jpg";
    private static final String GARAGE_PATH = "/home/andrew/IdeaProjects/realestate/src/main/resources/static/images/CA/garage.jpg";
    private static final String HOUSE_PATH = "/home/andrew/IdeaProjects/realestate/src/main/resources/static/images/CA/house.jpg";
    private static final String STORAGE_PATH = "/home/andrew/IdeaProjects/realestate/src/main/resources/static/images/CA/storage.jpg";

    private final FacilityImageBootstrap facilityImageBootstrap;

    public ProdBootstrap(FacilityImageBootstrap facilityImageBootstrap) {
        this.facilityImageBootstrap = facilityImageBootstrap;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        facilityImageBootstrap.saveImageInDB(6L, APARTMENT_PATH);
        facilityImageBootstrap.saveImageInDB(7L, BASEMENT_PATH);
        facilityImageBootstrap.saveImageInDB(8L, GARAGE_PATH);
        facilityImageBootstrap.saveImageInDB(9L, HOUSE_PATH);
        facilityImageBootstrap.saveImageInDB(10L, STORAGE_PATH);
    }

}
