package web.example.realestate.domain.building;

import web.example.realestate.domain.enums.Status;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "houses")
public class House extends Facility {

    private Integer numberOfStoreys;
    private boolean hasBackyard;
    private boolean hasGarden;

    public House() {}

    public House(Integer numberOfRooms, Integer totalArea, String description,
                 LocalDateTime publishedDateTime, BigInteger monthRent, BigInteger price,
                 Status status, Integer numberOfStoreys, boolean hasBackyard, boolean hasGarden) {
        super(numberOfRooms, totalArea, description, publishedDateTime, monthRent, price, status);
        this.numberOfStoreys = numberOfStoreys;
        this.hasBackyard = hasBackyard;
        this.hasGarden = hasGarden;
    }

    public House(Integer numberOfRooms, Integer totalArea, String description,
                 LocalDateTime publishedDateTime, BigInteger monthRent, BigInteger price,
                 Status status, Address address, Integer numberOfStoreys, boolean hasBackyard, boolean hasGarden) {
        super(numberOfRooms, totalArea, description, publishedDateTime, monthRent, price, status, address);
        this.numberOfStoreys = numberOfStoreys;
        this.hasBackyard = hasBackyard;
        this.hasGarden = hasGarden;
    }

    public House(Integer numberOfRooms, Integer totalArea, String description,
                 LocalDateTime publishedDateTime, BigInteger monthRent, BigInteger price,
                 Status status, List<Byte[]> photos, List<Byte[]> videos, Address address,
                 Integer numberOfStoreys, boolean hasBackyard, boolean hasGarden) {
        super(numberOfRooms, totalArea, description, publishedDateTime, monthRent, price, status, photos, videos, address);
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
