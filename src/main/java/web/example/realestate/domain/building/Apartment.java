package web.example.realestate.domain.building;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "apartments")
public class Apartment extends Facility {

    private Integer apartmentNumber;
    private Integer floor;

    public Apartment() {}

    public Apartment(Integer numberOfRooms, Integer totalArea, String description,
                     LocalDateTime publishedDateTime, Integer apartmentNumber, Integer floor) {
        super(numberOfRooms, totalArea, description, publishedDateTime);
        this.apartmentNumber = apartmentNumber;
        this.floor = floor;
    }

    public Apartment(Integer numberOfRooms, Integer totalArea, String description,
                     LocalDateTime publishedDateTime, Address address, Integer apartmentNumber, Integer floor) {
        super(numberOfRooms, totalArea, description, publishedDateTime, address);
        this.apartmentNumber = apartmentNumber;
        this.floor = floor;
    }

    public Apartment(Integer numberOfRooms, Integer totalArea, String description,
                     LocalDateTime publishedDateTime, FacilityObject facilityObject,
                     Integer apartmentNumber, Integer floor) {
        super(numberOfRooms, totalArea, description, publishedDateTime, facilityObject);
        this.apartmentNumber = apartmentNumber;
        this.floor = floor;
    }

    public Apartment(Integer numberOfRooms, Integer totalArea, String description,
                     LocalDateTime publishedDateTime, Address address,
                     FacilityObject facilityObject, Integer apartmentNumber, Integer floor) {
        super(numberOfRooms, totalArea, description, publishedDateTime, address, facilityObject);
        this.apartmentNumber = apartmentNumber;
        this.floor = floor;
    }

    public Apartment(Integer numberOfRooms, Integer totalArea, String description,
                     LocalDateTime publishedDateTime, List<Byte[]> photos, List<Byte[]> videos,
                     Address address, FacilityObject facilityObject, Integer apartmentNumber, Integer floor) {
        super(numberOfRooms, totalArea, description, publishedDateTime, photos, videos, address, facilityObject);
        this.apartmentNumber = apartmentNumber;
        this.floor = floor;
    }

    public Integer getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(Integer apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "apartmentNumber=" + apartmentNumber +
                ", floor=" + floor +
                '}';
    }
}
