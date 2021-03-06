package web.example.realestate.domain.people;

import web.example.realestate.domain.building.FacilityObject;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "real_estate_agents")
public class RealEstateAgent extends Person {

    private BigInteger salary;
    private LocalDate hiredDate;
    private LocalDate quitDate;

    @OneToMany(mappedBy = "agent")
    private Set<FacilityObject> facilityObjects;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "clients_agents",
            joinColumns = @JoinColumn(name = "agent_id"),
            inverseJoinColumns = @JoinColumn(name = "client_id"))
    private Set<Client> clients;

    public RealEstateAgent() {}

    public RealEstateAgent(String firstName, String lastName, String login, String password,
                           Contact contact, BigInteger salary, LocalDate hiredDate) {
        super(firstName, lastName, login, password, contact);
        this.salary = salary;
        this.hiredDate = hiredDate;
    }

    public BigInteger getSalary() {
        return salary;
    }

    public void setSalary(BigInteger salary) {
        this.salary = salary;
    }

    public LocalDate getHiredDate() {
        return hiredDate;
    }

    public void setHiredDate(LocalDate hiredDate) {
        this.hiredDate = hiredDate;
    }

    public LocalDate getQuitDate() {
        return quitDate;
    }

    public void setQuitDate(LocalDate quitDate) {
        this.quitDate = quitDate;
    }

    public Set<FacilityObject> getFacilityObjects() {
        return facilityObjects;
    }

    public void setFacilityObjects(Set<FacilityObject> facilityObjects) {
        this.facilityObjects = facilityObjects;
    }

    public void addFacilityObject(FacilityObject facilityObject) {
        this.facilityObjects.add(facilityObject);
        facilityObject.setAgent(this);
    }

    public void removeFacilityObject(FacilityObject facilityObject) {
        this.facilityObjects.remove(facilityObject);
        facilityObject.setAgent(null);
    }

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

    public void addClient(Client client) {
        this.clients.add(client);
        client.getRealEstateAgents().add(this);
    }

    public void removeClient(Client client) {
        this.clients.remove(client);
        client.getRealEstateAgents().remove(this);
    }

    @Override
    public String toString() {
        return "RealEstateAgent{" +
                "salary=" + salary +
                ", hiredDate=" + hiredDate +
                ", quitDate=" + quitDate +
                '}';
    }
}
