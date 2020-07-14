package web.example.realestate.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private static final String APARTMENT_PATH = "classpath:static/images/IL/apartment.jpg";
    private static final String BASEMENT_PATH = "classpath:static/images/IL/basement.jpg";
    private static final String GARAGE_PATH = "classpath:static/images/IL/garage.jpg";
    private static final String HOUSE_PATH = "classpath:static/images/IL/house.jpg";
    private static final String STORAGE_PATH = "classpath:static/images/IL/storage.jpg";

    private final FacilityImageBootstrap facilityImageBootstrap;

    public DevBootstrap(FacilityImageBootstrap facilityImageBootstrap) {
        this.facilityImageBootstrap = facilityImageBootstrap;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        facilityImageBootstrap.saveImageInDB(1L, APARTMENT_PATH);
        facilityImageBootstrap.saveImageInDB(2L, BASEMENT_PATH);
        facilityImageBootstrap.saveImageInDB(3L, GARAGE_PATH);
        facilityImageBootstrap.saveImageInDB(4L, HOUSE_PATH);
        facilityImageBootstrap.saveImageInDB(5L, STORAGE_PATH);
    }

}
