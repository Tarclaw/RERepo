package web.example.realestate.domain.building;

import java.time.LocalDateTime;
import java.util.List;

public class House extends Facility {

    private Integer numberOfStoreys;
    private boolean hasBackyard;
    private boolean hasGarden;


    public House() {}

    public House(Integer numberOfRooms, Integer totalArea, String description, LocalDateTime publishedDateTime,
                 Integer numberOfStoreys, boolean hasBackyard, boolean hasGarden) {
        super(numberOfRooms, totalArea, description, publishedDateTime);
        this.numberOfStoreys = numberOfStoreys;
        this.hasBackyard = hasBackyard;
        this.hasGarden = hasGarden;
    }

    public House(Integer numberOfRooms, Integer totalArea, String description, LocalDateTime publishedDateTime,
                 Address address, Integer numberOfStoreys, boolean hasBackyard, boolean hasGarden) {
        super(numberOfRooms, totalArea, description,publishedDateTime, address);
        this.numberOfStoreys = numberOfStoreys;
        this.hasBackyard = hasBackyard;
        this.hasGarden = hasGarden;
    }

    public House(Integer numberOfRooms, Integer totalArea, String description, LocalDateTime publishedDateTime,
                 FacilityObject facilityObject, Integer numberOfStoreys, boolean hasBackyard, boolean hasGarden) {
        super(numberOfRooms, totalArea, description,publishedDateTime, facilityObject);
        this.numberOfStoreys = numberOfStoreys;
        this.hasBackyard = hasBackyard;
        this.hasGarden = hasGarden;
    }

    public House(Integer numberOfRooms, Integer totalArea, String description,
                 LocalDateTime publishedDateTime, Address address, FacilityObject facilityObject,
                 Integer numberOfStoreys, boolean hasBackyard, boolean hasGarden) {
        super(numberOfRooms, totalArea, description,publishedDateTime, address, facilityObject);
        this.numberOfStoreys = numberOfStoreys;
        this.hasBackyard = hasBackyard;
        this.hasGarden = hasGarden;
    }

    public House(Integer numberOfRooms, Integer totalArea, String description,
                 LocalDateTime publishedDateTime, List<Byte[]> photos, List<Byte[]> videos,
                 Address address, FacilityObject facilityObject, Integer numberOfStoreys,
                 boolean hasBackyard, boolean hasGarden) {
        super(numberOfRooms, totalArea, description,publishedDateTime, photos, videos, address, facilityObject);
        this.numberOfStoreys = numberOfStoreys;
        this.hasBackyard = hasBackyard;
        this.hasGarden = hasGarden;
    }

    public Integer getNumberOfStoreys() {
        return numberOfStoreys;
    }

    public void setNumberOfStoreys(Integer numberOfStoreys) {
        this.numberOfStoreys = numberOfStoreys;
    }

    public boolean isHasBackyard() {
        return hasBackyard;
    }

    public void setHasBackyard(boolean hasBackyard) {
        this.hasBackyard = hasBackyard;
    }

    public boolean isHasGarden() {
        return hasGarden;
    }

    public void setHasGarden(boolean hasGarden) {
        this.hasGarden = hasGarden;
    }

    @Override
    public String toString() {
        return "House{" +
                "numberOfStoreys=" + numberOfStoreys +
                ", hasBackyard=" + hasBackyard +
                ", hasGarden=" + hasGarden +
                '}';
    }
}
