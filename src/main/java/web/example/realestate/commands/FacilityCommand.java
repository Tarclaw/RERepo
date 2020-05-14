package web.example.realestate.commands;

import java.time.LocalDateTime;

public class FacilityCommand {
    private Long id;
    private Integer numberOfRooms;
    private Integer totalArea;
    private String description;
    private LocalDateTime publishedDateTime;
    private LocalDateTime closedDateTime;
    private AddressCommand address;
    //Apartment
    private boolean isApartment = false;
    private Integer apartmentNumber;
    private Integer floor;
    //Basement
    private boolean isBasement = false;
    private boolean itCommercial;
    //Garage
    private boolean isGarage = false;
    private boolean hasPit;
    private boolean hasEquipment;
    //House
    private boolean isHouse = false;
    private Integer numberOfStoreys;
    private boolean hasBackyard;
    private boolean hasGarden;
    //Storage
    private boolean isStorage = false;
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

    public boolean isApartment() {
        return isApartment;
    }

    public void setApartment(boolean apartment) {
        isApartment = apartment;
    }

    public boolean isBasement() {
        return isBasement;
    }

    public void setBasement(boolean basement) {
        isBasement = basement;
    }

    public boolean isGarage() {
        return isGarage;
    }

    public void setGarage(boolean garage) {
        isGarage = garage;
    }

    public boolean isHouse() {
        return isHouse;
    }

    public void setHouse(boolean house) {
        isHouse = house;
    }

    public boolean isStorage() {
        return isStorage;
    }

    public void setStorage(boolean storage) {
        isStorage = storage;
    }
}
