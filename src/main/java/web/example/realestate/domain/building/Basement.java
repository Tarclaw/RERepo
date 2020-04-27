package web.example.realestate.domain.building;

import java.time.LocalDateTime;
import java.util.List;

public class Basement extends Facility {

    private boolean itCommercial;

    public Basement() {}

    public Basement(Integer numberOfRooms, Integer totalArea, String description,
                    LocalDateTime publishedDateTime, boolean itCommercial) {
        super(numberOfRooms, totalArea, description, publishedDateTime);
        this.itCommercial = itCommercial;
    }

    public Basement(Integer numberOfRooms, Integer totalArea, String description,
                    LocalDateTime publishedDateTime, Address address, boolean itCommercial) {
        super(numberOfRooms, totalArea, description, publishedDateTime, address);
        this.itCommercial = itCommercial;
    }

    public Basement(Integer numberOfRooms, Integer totalArea, String description,
                    LocalDateTime publishedDateTime, FacilityObject facilityObject, boolean itCommercial) {
        super(numberOfRooms, totalArea, description, publishedDateTime, facilityObject);
        this.itCommercial = itCommercial;
    }

    public Basement(Integer numberOfRooms, Integer totalArea, String description,
                    LocalDateTime publishedDateTime, Address address,
                    FacilityObject facilityObject, boolean itCommercial) {
        super(numberOfRooms, totalArea, description, publishedDateTime, address, facilityObject);
        this.itCommercial = itCommercial;
    }

    public Basement(Integer numberOfRooms, Integer totalArea, String description,
                    LocalDateTime publishedDateTime, List<Byte[]> photos, List<Byte[]> videos,
                    Address address, FacilityObject facilityObject, boolean itCommercial) {
        super(numberOfRooms, totalArea, description, publishedDateTime, photos, videos, address, facilityObject);
        this.itCommercial = itCommercial;
    }

    public boolean isItCommercial() {
        return itCommercial;
    }

    public void setItCommercial(boolean itCommercial) {
        this.itCommercial = itCommercial;
    }

    @Override
    public String toString() {
        return "Basement{" +
                "itCommercial=" + itCommercial +
                '}';
    }
}
