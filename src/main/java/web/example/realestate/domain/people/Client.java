package web.example.realestate.domain.people;

import web.example.realestate.domain.building.Facility;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "clients")
public class Client extends Person {

    private boolean isSeller;
    private boolean isBuyer;
    private boolean isRenter;
    private boolean isLeaser;

    @ManyToMany(mappedBy = "clients")
    private Set<RealEstateAgent> realEstateAgents;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "clients_facilities",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "facility_id"))
    private Set<Facility> facilities;

    public Client() {}

    public Client(String firstName, String lastName, String login, String password, Contact contact,
                  boolean isSeller, boolean isBuyer, boolean isRenter, boolean isLeaser) {
        super(firstName, lastName, login, password, contact);
        this.isSeller = isSeller;
        this.isBuyer = isBuyer;
        this.isRenter = isRenter;
        this.isLeaser = isLeaser;
    }

    public boolean isSeller() {
        return isSeller;
    }

    public void setSeller(boolean seller) {
        isSeller = seller;
    }

    public boolean isBuyer() {
        return isBuyer;
    }

    public void setBuyer(boolean buyer) {
        isBuyer = buyer;
    }

    public boolean isRenter() {
        return isRenter;
    }

    public void setRenter(boolean renter) {
        isRenter = renter;
    }

    public boolean isLeaser() {
        return isLeaser;
    }

    public void setLeaser(boolean leaser) {
        isLeaser = leaser;
    }

    public Set<RealEstateAgent> getRealEstateAgents() {
        return realEstateAgents;
    }

    public void setRealEstateAgents(Set<RealEstateAgent> realEstateAgents) {
        this.realEstateAgents = realEstateAgents;
    }

    public Set<Facility> getFacilities() {
        return facilities;
    }

    public void setFacilities(Set<Facility> facilities) {
        this.facilities = facilities;
    }

    public void addFacility(Facility facility) {
        this.facilities.add(facility);
        facility.getClients().remove(this);
    }

    public void removeFacility(Facility facility) {
        this.facilities.remove(facility);
        facility.getClients().remove(this);
    }

    @Override
    public String toString() {
        return "Client{" +
                "isSeller=" + isSeller +
                ", isBuyer=" + isBuyer +
                ", isRenter=" + isRenter +
                ", isLeaser=" + isLeaser +
                '}';
    }


}
