package web.example.realestate.domain.building;

import web.example.realestate.domain.enums.Status;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "basements")
public class Basement extends Facility {

    private boolean itCommercial;

    public Basement() {}

    public Basement(Integer numberOfRooms, Integer totalArea, String description, LocalDateTime publishedDateTime,
                    BigInteger monthRent, BigInteger price, Status status, boolean itCommercial) {
        super(numberOfRooms, totalArea, description, publishedDateTime, monthRent, price, status);
        this.itCommercial = itCommercial;
    }

    public Basement(Integer numberOfRooms, Integer totalArea, String description, LocalDateTime publishedDateTime,
                    BigInteger monthRent, BigInteger price, Status status, Address address, boolean itCommercial) {
        super(numberOfRooms, totalArea, description, publishedDateTime, monthRent, price, status, address);
        this.itCommercial = itCommercial;
    }

    public Basement(Integer numberOfRooms, Integer totalArea, String description,
                    LocalDateTime publishedDateTime, BigInteger monthRent, BigInteger price,
                    Status status, List<Byte[]> photos, List<Byte[]> videos,
                    Address address, boolean itCommercial) {
        super(numberOfRooms, totalArea, description, publishedDateTime, monthRent, price, status, photos, videos, address);
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
