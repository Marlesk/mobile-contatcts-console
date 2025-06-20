package gr.aueb.cf.mobilecontacts.dao;

import gr.aueb.cf.mobilecontacts.model.MobileContact;

import java.util.ArrayList;
import java.util.List;

public class MobileContactDAOImpl implements IMobileContactDAO {
    // είναι static, έτσι η τιμή είναι κοινή για όλα τα αντικείμενα της κλάσης.
    private static Long id = 1L;
    // είναι final, οπότε δεν μπορείς να αναθέσεις μια νέα λίστα στη μεταβλητή αυτή,
    // αλλά μπορείς να προσθέσεις ή να αφαιρέσεις στοιχεία από την υπάρχουσα λίστα
    private static final List<MobileContact> contacts = new ArrayList<>();

    @Override
    public MobileContact insert(MobileContact mobileContact) {
        mobileContact.setId(id++);
        contacts.add(mobileContact);
        return mobileContact; // επιστρέφει το instance που αποθήκευσε
    }

    // βοηθητική συνάρτηση για το id αν υπάρχει ή όχι
    public int getIndexById(Long id) {
        int positionToReturn = -1;
        for (int i = 0; i < contacts.size(); i++) {
            // η λίστα contacts είναι μια λίστα με διαφορετικά αντικείμενα
            // με get(i) παίρνουμε ένα ολόκληρο αντικείμενο της κλάσης MobileContact π.χ. (1L, "Alice", "12345")
            // με getId() παίρνουμε το id πεδίο του αντικειμένου
            if (contacts.get(i).getId().equals(id)) {
                positionToReturn = i;
                break;
            }
        }
        return positionToReturn;
    }

    @Override
    public MobileContact update(Long id, MobileContact mobileContact) {
        contacts.set(getIndexById(id), mobileContact);
        return mobileContact;
    }

    @Override
    public void deleteById(Long id) {
        contacts.remove(getIndexById(id));
        // εφαρμόζει το predicate(boolean function) σε κάθε στοιχείο του contacts
        // contacts.removeIf(contact -> contact.getId().equals(id));
    }

    // βοηθητική συνάρτηση για το phoneNumber αν υπάρχει ή όχι
    public int getIndexByPhoneNumber(String phoneNumber) {
        int positionToReturn = -1;
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getPhoneNumber().equals(phoneNumber)) {
                positionToReturn = i;
                break;
            }
        }
        return positionToReturn;
    }

    @Override
    public void deleteByPhoneNumber(String phoneNumber) {
        // contacts.removeIf(contact -> contact.getPhoneNumber().equals(phoneNumber));
        contacts.remove(getIndexByPhoneNumber(phoneNumber));
    }

    @Override
    public MobileContact getById(Long id) {
        int position = getIndexById(id);
        return (position != -1) ? contacts.get(position) : null;

        // return contacts.stream()
        //               .filter(contact -> contact.getId().equals(id))
        //               .findFirst()
        //               .orElse(null);
    }

    @Override
    public MobileContact getByPhoneNumber(String phoneNumber) {
        int position = getIndexByPhoneNumber(phoneNumber);
        return (position != -1) ? contacts.get(position) : null;

        // return contacts.stream()
        //                .filter(contact -> contact.getPhoneNumber().equals(phoneNumber))
        //                .findFirst()
        //               .orElse(null);
    }

    /*
     * επιστρέφει όχι αναφορά (return contacts) αλλά
     * fresh copy(new ArrayList) για να μην μπορεί να την αλλάξει κανείς
     * που λαμβάνει μία αναφορά.
     */
    @Override
    public List<MobileContact> getAll() {
        return new ArrayList<>(contacts);
    }

    @Override
    public boolean userIdExists(Long id) {
        int position = getIndexById(id);
        return position != -1;

        // return contacts.stream().anyMatch(contact -> contact.getId().equals(id));
    }

    @Override
    public boolean phoneNumberExists(String phoneNumber) {
        int position = getIndexByPhoneNumber(phoneNumber);
        return position != -1;

        // return contacts.stream().anyMatch(contact -> contact.getPhoneNumber().equals(phoneNumber);
    }
}
