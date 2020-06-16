package web.example.realestate.commands;

import java.util.HashSet;
import java.util.Set;

public class ClientCommand {
    //person
    private Long id;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    //contact
    private String email;
    private String skype;
    private String mobileNumber;
    //client
    private boolean isSeller;
    private boolean isBuyer;
    private boolean isRenter;
    private boolean isLeaser;

    private Set<FacilityCommand> facilityCommands = new HashSet<>();
    private Set<RealEstateAgentCommand> realEstateAgentCommands = new HashSet<>();

    public ClientCommand() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
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

    public Set<FacilityCommand> getFacilityCommands() {
        return facilityCommands;
    }

    public void setFacilityCommands(Set<FacilityCommand> facilityCommands) {
        this.facilityCommands = facilityCommands;
    }

    public Set<RealEstateAgentCommand> getRealEstateAgentCommands() {
        return realEstateAgentCommands;
    }

    public void setRealEstateAgentCommands(Set<RealEstateAgentCommand> realEstateAgentCommands) {
        this.realEstateAgentCommands = realEstateAgentCommands;
    }
}
