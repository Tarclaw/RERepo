package web.example.realestate.domain.building;

import web.example.realestate.domain.enums.Status;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "garages")
public class Garage extends Facility {

    private boolean hasPit;
    private boolean hasEquipment;

    public Garage() {}

    public Garage(Integer numberOfRooms, Integer totalArea, String description, LocalDateTime publishedDateTime,
                  BigInteger monthRent, BigInteger price, Status status, boolean hasPit, boolean hasEquipment) {
        super(numberOfRooms, totalArea, description, publishedDateTime, monthRent, price, status);
        this.hasPit = hasPit;
        this.hasEquipment = hasEquipment;
    }

    public Garage(Integer numberOfRooms, Integer totalArea, String description, LocalDateTime publishedDateTime,
                  BigInteger monthRent, BigInteger price, Status status, Address address,
                  boolean hasPit, boolean hasEquipment) {
        super(numberOfRooms, totalArea, description, publishedDateTime, monthRent, price, status, address);
        this.hasPit = hasPit;
        this.hasEquipment = hasEquipment;
    }

    public Garage(Integer numberOfRooms, Integer totalArea, String description,
                  LocalDateTime publishedDateTime, BigInteger monthRent, BigInteger price,
                  Status status, byte[] image, Address address, boolean hasPit, boolean hasEquipment) {
        super(numberOfRooms, totalArea, description, publishedDateTime, monthRent, price, status, image, address);
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
