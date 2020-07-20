package web.example.realestate.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@Profile("default")
public class RealEstateAgensyBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RealEstateAgensyBootstrap.class);

    private static final String APARTMENT_PATH = "classpath:static/images/NY/apartment.jpg";
    private static final String BASEMENT_PATH = "classpath:static/images/NY/basement.jpg";
    private static final String GARAGE_PATH = "classpath:static/images/NY/garage.jpg";
    private static final String HOUSE_PATH = "classpath:static/images/NY/house.jpg";
    private static final String STORAGE_PATH = "classpath:static/images/NY/storage.jpg";

    private final FacilityImageBootstrap facilityImageBootstrap;

    public RealEstateAgensyBootstrap(FacilityImageBootstrap facilityImageBootstrap) {
        this.facilityImageBootstrap = facilityImageBootstrap;
        LOGGER.info("New instance of RealEstateAgensyBootstrap created.");
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        LOGGER.trace("Enter in 'onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent)' method");

        facilityImageBootstrap.saveImageInDB(11L, APARTMENT_PATH);
        facilityImageBootstrap.saveImageInDB(12L, BASEMENT_PATH);
        facilityImageBootstrap.saveImageInDB(13L, GARAGE_PATH);
        facilityImageBootstrap.saveImageInDB(14L, HOUSE_PATH);
        facilityImageBootstrap.saveImageInDB(15L, STORAGE_PATH);

        LOGGER.trace("'onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent)' executed successfully.");
    }

}
