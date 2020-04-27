package web.example.realestate.domain.building;

import java.time.LocalDateTime;
import java.util.List;

public class Garage extends Facility {

    private boolean hasPit;
    private boolean hasEquipment;

    public Garage() {}

    public Garage(Integer numberOfRooms, Integer totalArea, String description,
                  LocalDateTime publishedDateTime, boolean hasPit, boolean hasEquipment) {
        super(numberOfRooms, totalArea, description, publishedDateTime);
        this.hasPit = hasPit;
        this.hasEquipment = hasEquipment;
    }

    public Garage(Integer numberOfRooms, Integer totalArea, String description,
                  LocalDateTime publishedDateTime, Address address, boolean hasPit, boolean hasEquipment) {
        super(numberOfRooms, totalArea, description, publishedDateTime, address);
        this.hasPit = hasPit;
        this.hasEquipment = hasEquipment;
    }

    public Garage(Integer numberOfRooms, Integer totalArea, String description,
                  LocalDateTime publishedDateTime, FacilityObject facilityObject,
                  boolean hasPit, boolean hasEquipment) {
        super(numberOfRooms, totalArea, description, publishedDateTime, facilityObject);
        this.hasPit = hasPit;
        this.hasEquipment = hasEquipment;
    }

    public Garage(Integer numberOfRooms, Integer totalArea, String description, LocalDateTime publishedDateTime,
                  Address address, FacilityObject facilityObject, boolean hasPit, boolean hasEquipment) {
        super(numberOfRooms, totalArea, description, publishedDateTime, address, facilityObject);
        this.hasPit = hasPit;
        this.hasEquipment = hasEquipment;
    }

    public Garage(Integer numberOfRooms, Integer totalArea, String description,
                  LocalDateTime publishedDateTime, List<Byte[]> photos, List<Byte[]> videos,
                  Address address, FacilityObject facilityObject, boolean hasPit, boolean hasEquipment) {
        super(numberOfRooms, totalArea, description, publishedDateTime, photos, videos, address, facilityObject);
        this.hasPit = hasPit;
        this.hasEquipment = hasEquipment;
    }

    public boolean isHasPit() {
        return hasPit;
    }

    public void setHasPit(boolean hasPit) {
        this.hasPit = hasPit;
    }

    public boolean isHasEquipment() {
        return hasEquipment;
    }

    public void setHasEquipment(boolean hasEquipment) {
        this.hasEquipment = hasEquipment;
    }

    @Override
    public String toString() {
        return "Garage{" +
                "hasPit=" + hasPit +
                ", hasEquipment=" + hasEquipment +
                '}';
    }
}
