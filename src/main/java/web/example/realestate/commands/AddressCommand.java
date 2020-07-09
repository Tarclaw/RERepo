package web.example.realestate.commands;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddressCommand {

    private Long id;

    @NotNull(message = "Please enter value")
    @Digits(integer = 1000000, fraction = 0, message = "Please enter a number up to 1 000 000")
    private Integer postcode;

    @NotNull(message = "Please enter value")
    @Digits(integer = 1000000, fraction = 0, message = "Please enter a number up to 1 000 000")
    private Integer facilityNumber;

    @NotNull(message = "Please enter value")
    @Size(min = 2, max = 50, message = "Please provide city between 2 and 50 characters")
    private String city;

    @NotNull(message = "Please enter value")
    @Size(min = 2, max = 50, message = "Please provide district between 2 and 50 characters")
    private String district;

    @NotNull(message = "Please enter value")
    @Size(min = 2, max = 50, message = "Please provide street between 2 and 50 characters")
    private String street;

    public AddressCommand() {
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
}
