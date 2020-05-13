package web.example.realestate.domain.building;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "addresses")
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer postcode;
    private Integer facilityNumber;
    private String city;
    private String district;
    private String street;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Facility facility;

    public Address() {
    }

    public Address(Integer postcode, Integer facilityNumber,
                   String city, String district, String street) {
        this.postcode = postcode;
        this.facilityNumber = facilityNumber;
        this.city = city;
        this.district = district;
        this.street = street;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPostcode() {
        return postcode;
    }

    public void setPostcode(Integer postcode) {
        this.postcode = postcode;
    }

    public Integer getFacilityNumber() {
        return facilityNumber;
    }

    public void setFacilityNumber(Integer facilityNumber) {
        this.facilityNumber = facilityNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", postcode=" + postcode +
                ", facilityNumber=" + facilityNumber +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", street='" + street + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(id, address.id) &&
                Objects.equals(postcode, address.postcode) &&
                Objects.equals(facilityNumber, address.facilityNumber) &&
                Objects.equals(city, address.city) &&
                Objects.equals(district, address.district) &&
                Objects.equals(street, address.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, postcode, facilityNumber, city, district, street);
    }
}
