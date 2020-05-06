package web.example.realestate.commands;

import web.example.realestate.domain.enums.Status;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

public class FacilityObjectCommand {

    private Long id;
    private BigInteger monthRent;
    private BigInteger price;
    private BigInteger commissionAmount;
    private Status status;
    private RealEstateAgentCommand realEstateAgentCommand;
    private Set<FacilityCommand> facilityCommands = new HashSet<>();

    public FacilityObjectCommand() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public RealEstateAgentCommand getRealEstateAgentCommand() {
        return realEstateAgentCommand;
    }

    public void setRealEstateAgentCommand(RealEstateAgentCommand realEstateAgentCommand) {
        this.realEstateAgentCommand = realEstateAgentCommand;
    }

    public Set<FacilityCommand> getFacilityCommands() {
        return facilityCommands;
    }

    public void setFacilityCommands(Set<FacilityCommand> facilityCommands) {
        this.facilityCommands = facilityCommands;
    }
}
