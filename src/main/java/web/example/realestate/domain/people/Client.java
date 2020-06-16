package web.example.realestate.domain.people;

import web.example.realestate.domain.building.Facility;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "clients")
public class Client extends Person {

    @Lob
    private String customerRequirements;

    @OneToMany(mappedBy = "client")
    private Set<Facility> facilities;

    @ManyToMany(mappedBy = "clients")
    private Set<RealEstateAgent> realEstateAgents;

    public Client() {}

    public Client(String firstName, String lastName, String login,
                  String password, Contact contact, String customerRequirements) {
        super(firstName, lastName, login, password, contact);
        this.customerRequirements = customerRequirements;
    }

    public String getCustomerRequirements() {
        return customerRequirements;
    }

    public void setCustomerRequirements(String customerRequirements) {
        this.customerRequirements = customerRequirements;
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
        facility.setClient(this);
    }

    public void removeFacility(Facility facility) {
        this.facilities.remove(facility);
        facility.setClient(null);
    }

    public void addAgent(RealEstateAgent agent) {
        this.realEstateAgents.add(agent);
        agent.getClients().add(this);
    }

    public void removeAgent(RealEstateAgent agent) {
        this.realEstateAgents.remove(agent);
        agent.getClients().remove(this);
    }

    @Override
    public String toString() {
        return "Client{" +
                "customerRequirements='" + customerRequirements + '\'' +
                '}';
    }
}
