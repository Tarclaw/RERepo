package web.example.realestate.domain.building;

import web.example.realestate.domain.enums.Status;
import web.example.realestate.domain.people.Client;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "facilities")
@Inheritance(strategy = InheritanceType.JOINED)
public class Facility implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigInteger monthRent;
    private BigInteger price;
    private Integer numberOfRooms;
    private Integer totalArea;
    private String description;
    private LocalDateTime publishedDateTime;
    private LocalDateTime closedDateTime;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Lob
    private List<Byte[]> photos;

    @Lob
    private List<Byte[]> videos;

    @OneToOne(mappedBy = "facility", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;

    public Facility() {}

    public Facility(Integer numberOfRooms, Integer totalArea, String description,
                    LocalDateTime publishedDateTime, BigInteger monthRent, BigInteger price, Status status) {
        this.numberOfRooms = numberOfRooms;
        this.totalArea = totalArea;
        this.description = description;
        this.publishedDateTime = publishedDateTime;
        this.monthRent = monthRent;
        this.price = price;
        this.status = status;
    }

    public Facility(Integer numberOfRooms, Integer totalArea, String description, LocalDateTime publishedDateTime,
                    BigInteger monthRent, BigInteger price, Status status, Address address) {
        this.numberOfRooms = numberOfRooms;
        this.totalArea = totalArea;
        this.description = description;
        this.publishedDateTime = publishedDateTime;
        this.monthRent = monthRent;
        this.price = price;
        this.status = status;
        this.address = address;
    }

    public Facility(Integer numberOfRooms, Integer totalArea, String description,
                    LocalDateTime publishedDateTime, BigInteger monthRent, BigInteger price,
                    Status status, List<Byte[]> photos, List<Byte[]> videos, Address address) {
        this.numberOfRooms = numberOfRooms;
        this.totalArea = totalArea;
        this.description = description;
        this.publishedDateTime = publishedDateTime;
        this.monthRent = monthRent;
        this.price = price;
        this.status = status;
        this.photos = photos;
        this.videos = videos;
        this.address = address;
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

    public List<Byte[]> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Byte[]> photos) {
        this.photos = photos;
    }

    public List<Byte[]> getVideos() {
        return videos;
    }

    public void setVideos(List<Byte[]> videos) {
        this.videos = videos;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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

    @Override
    public String toString() {
        return "Facility{" +
                "id=" + id +
                ", numberOfRooms=" + numberOfRooms +
                ", totalArea=" + totalArea +
                ", description='" + description + '\'' +
                ", publishedDateTime=" + publishedDateTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Facility facility = (Facility) o;
        return Objects.equals(id, facility.id) &&
                Objects.equals(numberOfRooms, facility.numberOfRooms) &&
                Objects.equals(totalArea, facility.totalArea) &&
                Objects.equals(description, facility.description) &&
                Objects.equals(publishedDateTime, facility.publishedDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numberOfRooms, totalArea, description, publishedDateTime);
    }
}
