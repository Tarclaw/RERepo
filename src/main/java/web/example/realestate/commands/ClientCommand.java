package web.example.realestate.commands;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

public class ClientCommand {
    //person
    private Long id;
    private Long agentId;

    @NotNull(message = "Please enter value")
    @Size(min = 2, max = 50, message = "Please enter value between 2 and 50 characters")
    private String firstName;

    @NotNull(message = "Please enter value")
    @Size(min = 2, max = 50, message = "Please enter value between 2 and 50 characters")
    private String lastName;

    @NotNull(message = "Please enter value")
    @Size(min = 2, max = 50, message = "Please enter value between 2 and 50 characters")
    private String login;

    @NotNull(message = "Please enter value")
    @Size(min = 2, max = 50, message = "Please enter value between 2 and 50 characters")
    private String password;

    //contact
    @Email(message = "Please enter valid email address")
    private String email;

    @NotNull(message = "Please enter value")
    @Size(min = 2, max = 50, message = "Please enter value between 2 and 50 characters")
    private String skype;

    @NotNull(message = "Please enter value")
    @Size(min = 10, max = 17, message = "Please provide valid mobile")
    private String mobileNumber;

    //client
    @NotNull(message = "Please enter value")
    @Size(min = 10, max = 1000, message = "Please provide requirements between 10 and 1000 characters")
    private String customerRequirements;

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

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
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

    public String getCustomerRequirements() {
        return customerRequirements;
    }

    public void setCustomerRequirements(String customerRequirements) {
        this.customerRequirements = customerRequirements;
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
