package web.example.realestate.commands;

import javax.validation.constraints.*;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RealEstateAgentCommand {

    //person
    private Long id;

    @NotNull(message = "Please enter value")
    @Size(min = 2, max = 50, message = "Please enter first name between 2 and 50 characters")
    private String firstName;

    @NotNull(message = "Please enter value")
    @Size(min = 2, max = 50, message = "Please enter last name between 2 and 50 characters")
    private String lastName;

    @NotNull(message = "Please enter value")
    @Size(min = 2, max = 50, message = "Please enter login between 2 and 50 characters")
    private String login;

    @NotNull(message = "Please enter value")
    @Size(min = 2, max = 50, message = "Please enter password between 2 and 50 characters")
    private String password;

    //contact
    @Email(message = "Please enter valid email address")
    private String email;

    @NotNull(message = "Please enter value")
    @Size(min = 2, max = 50, message = "Please enter skype between 2 and 50 characters")
    private String skype;

    @NotNull(message = "Please enter value")
    @Size(min = 10, max = 17, message = "Please provide valid mobile")
    private String mobileNumber;

    //agent
    @NotNull(message = "Common, everybody must have something to eat")
    private BigInteger salary;

    @PastOrPresent(message = "Hired date should be past or present")
    private LocalDate hiredDate;

    @FutureOrPresent(message = "Quit date should be present or future")
    private LocalDate quitDate;

    private List<Long> clientIds = new ArrayList<>();

    public RealEstateAgentCommand() {
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

    public List<Long> getClientIds() {
        return clientIds;
    }

    public void setClientIds(List<Long> clientIds) {
        this.clientIds = clientIds;
    }
}
