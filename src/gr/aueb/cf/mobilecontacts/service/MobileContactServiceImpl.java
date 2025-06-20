package gr.aueb.cf.mobilecontacts.service;

import gr.aueb.cf.mobilecontacts.dao.IMobileContactDAO;
import gr.aueb.cf.mobilecontacts.dto.MobileContactInsertDTO;
import gr.aueb.cf.mobilecontacts.dto.MobileContactUpdateDTO;
import gr.aueb.cf.mobilecontacts.exceptions.ContactNotFoundException;
import gr.aueb.cf.mobilecontacts.exceptions.PhoneNumberAlreadyExistsException;
import gr.aueb.cf.mobilecontacts.model.MobileContact;

import java.util.List;

public class MobileContactServiceImpl implements IMobileContactService {
    private final IMobileContactDAO dao;

    public MobileContactServiceImpl(IMobileContactDAO dao) {
        this.dao = dao;
    }

    @Override
    public MobileContact insertMobileContact(MobileContactInsertDTO mobileContactInsertDTO)
            throws PhoneNumberAlreadyExistsException {
        MobileContact mobileContact;
        try {
            if (dao.phoneNumberExists(mobileContactInsertDTO.getPhoneNumber())) {
                throw new PhoneNumberAlreadyExistsException("User with phone number " +
                        mobileContactInsertDTO.getPhoneNumber() + " already exists");
            }
            mobileContact = mapInsertDTOToContact(mobileContactInsertDTO);
            System.err.printf("User Logger: %s was inserted.\n", mobileContact);
            return dao.insert(mobileContact);
        } catch (PhoneNumberAlreadyExistsException e) {
            System.err.printf("User with phone number %s already exists.\n", mobileContactInsertDTO.getPhoneNumber());
            throw e;
        }
    }

    @Override
    public MobileContact updateMobileContact(MobileContactUpdateDTO mobileContactUpdateDTO)
            throws PhoneNumberAlreadyExistsException, ContactNotFoundException {
        MobileContact mobileContact;
        MobileContact newContact;

        try {
            if (!dao.userIdExists(mobileContactUpdateDTO.getId())) {
                throw new ContactNotFoundException("Contact with id: " + mobileContactUpdateDTO.getId() + "not found for update");
            }

            mobileContact = dao.getById(mobileContactUpdateDTO.getId());
            boolean isPhoneNumberOurOwn = mobileContact.getPhoneNumber().equals(mobileContactUpdateDTO.getPhoneNumber());
            boolean isPhoneNumberExists = dao.phoneNumberExists(mobileContactUpdateDTO.getPhoneNumber());

            // Αν το νέο τηλέφωνο υπάρχει ήδη στη βάση και δεν είναι το ίδιο με το παλιό τηλέφωνο
            if (isPhoneNumberExists && !isPhoneNumberOurOwn) {
                throw new PhoneNumberAlreadyExistsException("Contact with phone number: " + mobileContactUpdateDTO.getPhoneNumber()
                        + "already exists and cannot update");
            }

            if (isPhoneNumberOurOwn) {
                // Αν το νέο τηλέφωνο είναι το ίδιο με το παλιό
                throw new PhoneNumberAlreadyExistsException("No change in phone number. Update is not needed.");
            }

            newContact = mapUpdateDTOContact(mobileContactUpdateDTO);
            System.err.printf("User Logger: %s was updated with new info: %s\n", mobileContact, newContact);
            return dao.update(mobileContact.getId(), newContact);

        } catch (PhoneNumberAlreadyExistsException | ContactNotFoundException e) {
            System.err.printf(e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteContactById(Long id) throws ContactNotFoundException {
        try {
            if (!dao.userIdExists(id)) {
                throw new ContactNotFoundException("Contact with id: " + id + "not found for delete");
            }
            System.err.printf("User with id: %d was deleted.\n", id);
            dao.deleteById(id);
        } catch (ContactNotFoundException e) {
            System.err.printf("User with id: %d not found for delete.\n", id);
            throw e;
        }

    }

    @Override
    public void deleteContactByPhoneNumber(String phoneNumber) throws ContactNotFoundException {
        try {
            if(!dao.phoneNumberExists(phoneNumber)) {
                throw new ContactNotFoundException("Contact with phone number: " + phoneNumber + "not found for delete");
            }
            System.err.printf("User with phone number: %s was deleted.\n", phoneNumber);
            dao.deleteByPhoneNumber(phoneNumber);
        } catch (ContactNotFoundException e) {
            System.err.printf("User with phone number: %s not found for delete.\n", phoneNumber);
            throw e;
        }
    }

    @Override
    public MobileContact getContactById(Long id) throws ContactNotFoundException {
        MobileContact mobileContact;

        try {
            mobileContact = dao.getById(id);
            if (mobileContact == null) {
                throw new ContactNotFoundException("Contact with id: " + id + "not found");
            }
            return mobileContact;
        } catch (ContactNotFoundException e) {
            System.err.printf("User with id: %d not found.\n", id);
            throw e;
        }
    }

    @Override
    public MobileContact getContactByPhoneNumber(String phoneNumber) throws ContactNotFoundException {
        MobileContact mobileContact;

        try {
            mobileContact = dao.getByPhoneNumber(phoneNumber);
            if (mobileContact == null) {
                throw new ContactNotFoundException("Contact with phone number: " + phoneNumber + "not found");
            }
            return mobileContact;
        } catch (ContactNotFoundException e) {
            System.err.printf("User with phone number: %s not found.\n", phoneNumber);
            throw e;
        }
    }

    @Override
    public List<MobileContact> getAllContacts() {
        return dao.getAll();
    }

    // Μετατροπή dto σε MobileContact ώστε να γίνει χρήση των μεθόδων του dao
    private MobileContact mapInsertDTOToContact(MobileContactInsertDTO dto) {
        return new MobileContact(null, dto.getFirstname(), dto.getLastname(), dto.getPhoneNumber());
    }

    private MobileContact mapUpdateDTOContact(MobileContactUpdateDTO dto) {
        return new MobileContact(dto.getId(), dto.getFirstname(), dto.getLastname(), dto.getPhoneNumber());
    }
}
