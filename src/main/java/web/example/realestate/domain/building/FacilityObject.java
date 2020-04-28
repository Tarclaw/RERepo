package web.example.realestate.domain.building;

import web.example.realestate.domain.people.RealEstateAgent;
import web.example.realestate.domain.enums.Status;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Set;
import java.util.Objects;

@Entity
@Table(name = "facility_objects")
public class FacilityObject implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private BigInteger monthRent;
    private BigInteger price;
    private BigInteger commissionAmount;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    private RealEstateAgent agent;

    @OneToMany(mappedBy = "facilityObject", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Facility> facilities;

    public FacilityObject() {}

    public FacilityObject(Status status, BigInteger monthRent, BigInteger price, BigInteger commissionAmount) {
        this.status = status;
        this.monthRent = monthRent;
        this.price = price;
        this.commissionAmount = commissionAmount;
    }

    public FacilityObject(Status status, BigInteger monthRent, BigInteger price,
                          BigInteger commissionAmount, RealEstateAgent agent) {
        this.status = status;
        this.monthRent = monthRent;
        this.price = price;
        this.commissionAmount = commissionAmount;
        this.agent = agent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    public BigInteger getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(BigInteger commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public RealEstateAgent getAgent() {
        return agent;
    }

    public void setAgent(RealEstateAgent agent) {
        this.agent = agent;
    }

    public Set<Facility> getFacilities() {
        return facilities;
    }

    public void setFacilities(Set<Facility> facilities) {
        this.facilities = facilities;
    }

    public void addFacility(Facility facility) {
        this.facilities.add(facility);
        facility.setFacilityObject(this);
    }

    public void removeFacility(Facility facility) {
        this.facilities.remove(facility);
        facility.setFacilityObject(null);
    }

    @Override
    public String toString() {
        return "FacilityObject{" +
                "id=" + id +
                ", status=" + status +
                ", monthRent=" + monthRent +
                ", price=" + price +
                ", commissionAmount=" + commissionAmount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FacilityObject that = (FacilityObject) o;
        return Objects.equals(id, that.id) &&
                status == that.status &&
                Objects.equals(monthRent, that.monthRent) &&
                Objects.equals(price, that.price) &&
                Objects.equals(commissionAmount, that.commissionAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, monthRent, price, commissionAmount);
    }
}
