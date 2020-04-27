package web.example.realestate.domain.people;

import javax.persistence.Embeddable;

@Embeddable
public class Contact {

    private String email;
    private String skype;
    private String mobileNumber;

    public Contact() {
    }

    public Contact(String email, String skype, String mobileNumber) {
        this.email = email;
        this.skype = skype;
        this.mobileNumber = mobileNumber;
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

    @Override
    public String toString() {
        return "Contact{" +
                "email='" + email + '\'' +
                ", skype='" + skype + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                '}';
    }
}
