package web.example.realestate.commands;

import java.time.LocalDateTime;

public class FacilityCommand {
    private Long id;
    private Integer numberOfRooms;
    private Integer totalArea;
    private String description;
    private LocalDateTime publishedDateTime;
    private LocalDateTime closedDateTime;
    private AddressCommand addressCommand;

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

    public AddressCommand getAddressCommand() {
        return addressCommand;
    }

    public void setAddressCommand(AddressCommand addressCommand) {
        this.addressCommand = addressCommand;
    }
}
