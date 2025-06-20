package gr.aueb.cf.mobilecontacts.model;

import java.util.Objects;

public class MobileContact extends AbstractEntity {
    private String firstname;
    private String lastname;
    private String phoneNumber;

    public MobileContact() {
    }

    public MobileContact(Long id, String phoneNumber, String lastname, String firstname) {
        setId(id);
        this.phoneNumber = phoneNumber;
        this.lastname = lastname;
        this.firstname = firstname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Firstname: " + firstname + ", Lastname: " + lastname +", Phone Number: " + phoneNumber;
    }

    // υπερκαλύπτω τις μεθόδους equals και hashCode
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof MobileContact that)) return false;
        // if (!(other instanceof MobileContact)) return false;
        // MobileContact that = (MobileContact) other γιατί το other είναι τύπου object
        return Objects.equals(getFirstname(), that.getFirstname())
                && Objects.equals(getLastname(), that.getLastname())
                && Objects.equals(getPhoneNumber(), that.getPhoneNumber());
    }

    // παρέχει έναν αριθμό (κωδικό) που χρησιμοποιείται για την ταχύτερη αναζήτηση του αντικειμένου
    // Εάν δύο αντικείμενα έχουν διαφορετικούς κωδικούς, είναι σίγουρο ότι δεν είναι το ίδιο αντικείμενο
    @Override
    public int hashCode() {
        return Objects.hash(getFirstname(), getLastname(), getPhoneNumber());
    }
}
