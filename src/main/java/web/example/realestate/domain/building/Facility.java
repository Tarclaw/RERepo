package web.example.realestate.domain.building;

import web.example.realestate.domain.people.Client;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "facilities")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Facility implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private Integer numberOfRooms;
    private Integer totalArea;
    private String description;
    private LocalDateTime publishedDateTime;
    private LocalDateTime closedDateTime;
    @Lob
    private List<Byte[]> photos;
    @Lob
    private List<Byte[]> videos;

    @OneToOne(mappedBy = "facility", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    private FacilityObject facilityObject;

    @ManyToMany(mappedBy = "facilities")
    private Set<Client> clients;

    public Facility() {}

    public Facility(Integer numberOfRooms, Integer totalArea, String description, LocalDateTime publishedDateTime) {
        this.numberOfRooms = numberOfRooms;
        this.totalArea = totalArea;
        this.description = description;
        this.publishedDateTime = publishedDateTime;
    }

    public Facility(Integer numberOfRooms, Integer totalArea, String description,
                    LocalDateTime publishedDateTime, Address address) {
        this.numberOfRooms = numberOfRooms;
        this.totalArea = totalArea;
        this.description = description;
        this.publishedDateTime = publishedDateTime;
        this.address = address;
    }

    public Facility(Integer numberOfRooms, Integer totalArea, String description,
                    LocalDateTime publishedDateTime, FacilityObject facilityObject) {
        this.numberOfRooms = numberOfRooms;
        this.totalArea = totalArea;
        this.description = description;
        this.publishedDateTime = publishedDateTime;
        this.facilityObject = facilityObject;
    }

    public Facility(Integer numberOfRooms, Integer totalArea, String description,
                    LocalDateTime publishedDateTime, Address address, FacilityObject facilityObject) {
        this.numberOfRooms = numberOfRooms;
        this.totalArea = totalArea;
        this.description = description;
        this.publishedDateTime = publishedDateTime;
        this.address = address;
        this.facilityObject = facilityObject;
    }

    public Facility(Integer numberOfRooms, Integer totalArea, String description, LocalDateTime publishedDateTime,
                    List<Byte[]> photos, List<Byte[]> videos, Address address, FacilityObject facilityObject) {
        this.numberOfRooms = numberOfRooms;
        this.totalArea = totalArea;
        this.description = description;
        this.publishedDateTime = publishedDateTime;
        this.photos = photos;
        this.videos = videos;
        this.address = address;
        this.facilityObject = facilityObject;
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

    public FacilityObject getFacilityObject() {
        return facilityObject;
    }

    public void setFacilityObject(FacilityObject facilityObject) {
        this.facilityObject = facilityObject;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
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
