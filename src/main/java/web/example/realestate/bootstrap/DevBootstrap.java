package web.example.realestate.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DevBootstrap.class);

    private static final String APARTMENT_PATH = "classpath:static/images/IL/apartment.jpg";
    private static final String BASEMENT_PATH = "classpath:static/images/IL/basement.jpg";
    private static final String GARAGE_PATH = "classpath:static/images/IL/garage.jpg";
    private static final String HOUSE_PATH = "classpath:static/images/IL/house.jpg";
    private static final String STORAGE_PATH = "classpath:static/images/IL/storage.jpg";

    private final FacilityImageBootstrap facilityImageBootstrap;

    public DevBootstrap(FacilityImageBootstrap facilityImageBootstrap) {
        this.facilityImageBootstrap = facilityImageBootstrap;
        LOGGER.info("New instance of DevBootstrap created.");
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        LOGGER.trace("Enter in 'onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent)' method");

        facilityImageBootstrap.saveImageInDB(1L, APARTMENT_PATH);
        facilityImageBootstrap.saveImageInDB(2L, BASEMENT_PATH);
        facilityImageBootstrap.saveImageInDB(3L, GARAGE_PATH);
        facilityImageBootstrap.saveImageInDB(4L, HOUSE_PATH);
        facilityImageBootstrap.saveImageInDB(5L, STORAGE_PATH);

        LOGGER.trace("'onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent)' executed successfully.");
    }

}
