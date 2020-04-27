package web.example.realestate.domain.building;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "storages")
public class Storage extends Facility {

    private Integer commercialCapacity;
    private boolean hasCargoEquipment;

    public Storage() {}

    public Storage(Integer numberOfRooms, Integer totalArea, String description,
                   LocalDateTime publishedDateTime, Integer commercialCapacity, boolean hasCargoEquipment) {
        super(numberOfRooms, totalArea, description, publishedDateTime);
        this.commercialCapacity = commercialCapacity;
        this.hasCargoEquipment = hasCargoEquipment;
    }

    public Storage(Integer numberOfRooms, Integer totalArea, String description, LocalDateTime publishedDateTime,
                   Address address, Integer commercialCapacity, boolean hasCargoEquipment) {
        super(numberOfRooms, totalArea, description, publishedDateTime, address);
        this.commercialCapacity = commercialCapacity;
        this.hasCargoEquipment = hasCargoEquipment;
    }

    public Storage(Integer numberOfRooms, Integer totalArea, String description, LocalDateTime publishedDateTime,
                   FacilityObject facilityObject, Integer commercialCapacity, boolean hasCargoEquipment) {
        super(numberOfRooms, totalArea, description, publishedDateTime, facilityObject);
        this.commercialCapacity = commercialCapacity;
        this.hasCargoEquipment = hasCargoEquipment;
    }

    public Storage(Integer numberOfRooms, Integer totalArea, String description,
                   LocalDateTime publishedDateTime, Address address, FacilityObject facilityObject,
                   Integer commercialCapacity, boolean hasCargoEquipment) {
        super(numberOfRooms, totalArea, description, publishedDateTime, address, facilityObject);
        this.commercialCapacity = commercialCapacity;
        this.hasCargoEquipment = hasCargoEquipment;
    }

    public Storage(Integer numberOfRooms, Integer totalArea, String description, LocalDateTime publishedDateTime,
                   List<Byte[]> photos, List<Byte[]> videos, Address address, FacilityObject facilityObject,
                   Integer commercialCapacity, boolean hasCargoEquipment) {
        super(numberOfRooms, totalArea, description, publishedDateTime, photos, videos, address, facilityObject);
        this.commercialCapacity = commercialCapacity;
        this.hasCargoEquipment = hasCargoEquipment;
    }

    public Integer getCommercialCapacity() {
        return commercialCapacity;
    }

    public void setCommercialCapacity(Integer commercialCapacity) {
        this.commercialCapacity = commercialCapacity;
    }

    public boolean isHasCargoEquipment() {
        return hasCargoEquipment;
    }

    public void setHasCargoEquipment(boolean hasCargoEquipment) {
        this.hasCargoEquipment = hasCargoEquipment;
    }

    @Override
    public String toString() {
        return "Storage{" +
                "commercialCapacity=" + commercialCapacity +
                ", hasCargoEquipment=" + hasCargoEquipment +
                '}';
    }
}
