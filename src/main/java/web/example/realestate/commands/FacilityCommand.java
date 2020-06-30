package web.example.realestate.commands;

import web.example.realestate.domain.enums.Status;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class FacilityCommand {
    private Long id;
    private Long clientId;
    private Integer numberOfRooms;
    private Integer totalArea;
    private String description;
    private LocalDateTime publishedDateTime;
    private LocalDateTime closedDateTime;
    private BigInteger monthRent;
    private BigInteger price;
    private Status status;
    private AddressCommand address;
    //Apartment
    private boolean itApartment = false;
    private Integer apartmentNumber;
    private Integer floor;
    //Basement
    private boolean itBasement = false;
    private boolean itCommercial;
    //Garage
    private boolean itGarage = false;
    private boolean hasPit;
    private boolean hasEquipment;
    //House
    private boolean itHouse = false;
    private Integer numberOfStoreys;
    private boolean hasBackyard;
    private boolean hasGarden;
    //Storage
    private boolean itStorage = false;
    private Integer commercialCapacity;
    private boolean hasCargoEquipment;

    public FacilityCommand() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public Integer getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(Integer totalArea) {
        this.totalArea = totalArea;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getPublishedDateTime() {
        return publishedDateTime;
    }

    public void setPublishedDateTime(LocalDateTime publishedDateTime) {
        this.publishedDateTime = publishedDateTime;
    }

    public LocalDateTime getClosedDateTime() {
        return closedDateTime;
    }

    public void setClosedDateTime(LocalDateTime closedDateTime) {
        this.closedDateTime = closedDateTime;
    }

    public AddressCommand getAddress() {
        return address;
    }

    public void setAddress(AddressCommand address) {
        this.address = address;
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

    public boolean isItCommercial() {
        return itCommercial;
    }

    public void setItCommercial(boolean itCommercial) {
        this.itCommercial = itCommercial;
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

    public BigInteger getMonthRent() {
        return monthRent;
    }

    public void setMonthRent(BigInteger monthRent) {
        this.monthRent = monthRent;
    }

    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isItApartment() {
        return itApartment;
    }

    public void setItApartment(boolean itApartment) {
        this.itApartment = itApartment;
    }

    public boolean isItBasement() {
        return itBasement;
    }

    public void setItBasement(boolean itBasement) {
        this.itBasement = itBasement;
    }

    public boolean isItGarage() {
        return itGarage;
    }

    public void setItGarage(boolean itGarage) {
        this.itGarage = itGarage;
    }

    public boolean isItHouse() {
        return itHouse;
    }

    public void setItHouse(boolean itHouse) {
        this.itHouse = itHouse;
    }

    public boolean isItStorage() {
        return itStorage;
    }

    public void setItStorage(boolean itStorage) {
        this.itStorage = itStorage;
    }
}
