package gr.aueb.cf.mobilecontacts.controller;

import gr.aueb.cf.mobilecontacts.dao.IMobileContactDAO;
import gr.aueb.cf.mobilecontacts.dao.MobileContactDAOImpl;
import gr.aueb.cf.mobilecontacts.dto.MobileContactInsertDTO;
import gr.aueb.cf.mobilecontacts.dto.MobileContactReadOnlyDTO;
import gr.aueb.cf.mobilecontacts.dto.MobileContactUpdateDTO;
import gr.aueb.cf.mobilecontacts.exceptions.ContactNotFoundException;
import gr.aueb.cf.mobilecontacts.exceptions.PhoneNumberAlreadyExistsException;
import gr.aueb.cf.mobilecontacts.model.MobileContact;
import gr.aueb.cf.mobilecontacts.service.IMobileContactService;
import gr.aueb.cf.mobilecontacts.service.MobileContactServiceImpl;
import gr.aueb.cf.mobilecontacts.validation.ValidationUtil;

import java.util.ArrayList;
import java.util.List;

public class MobileContactController {
    IMobileContactDAO dao = new MobileContactDAOImpl();
    IMobileContactService service = new MobileContactServiceImpl(dao);

    public String insertContact(MobileContactInsertDTO mobileContactInsertDTO) {
        try {
            // validate input data
            String error = ValidationUtil.validateDTO(mobileContactInsertDTO);
            if (!error.isEmpty()) { // (error != "")
                return "Error. " + "Validation Error\n" + error;
            }
            // if validation is ok insert contact
            MobileContact mobileContact = service.insertMobileContact(mobileContactInsertDTO);
            MobileContactReadOnlyDTO readOnlyDTO = mapMobileContactToDTO(mobileContact);

            return "OK\n" + serializeDTO(readOnlyDTO) + "\n";
        } catch (PhoneNumberAlreadyExistsException e) {
            return "Insert Error\n Phone number exists\n";
        }
    }

    public String updateContact(MobileContactUpdateDTO mobileContactUpdateDTO) {
        try {
            // validate input data
            String error = ValidationUtil.validateDTO(mobileContactUpdateDTO);
            if (!error.isEmpty()) { // (error != "")
                return "Error. " + "Validation Error\n" + error;
            }
            // if validation is ok insert contact
            MobileContact mobileContact = service.updateMobileContact(mobileContactUpdateDTO);
            MobileContactReadOnlyDTO readOnlyDTO = mapMobileContactToDTO(mobileContact);

            return "OK\n" + serializeDTO(readOnlyDTO) + "\n";
        } catch (PhoneNumberAlreadyExistsException e) {
            return "Update Error\n Phone number not found\n";
        } catch (ContactNotFoundException e) {
            return "Update Error\n Contact not found\n";
        }
    }


    public String deleteContactById(Long id) {
        try {
            service.deleteContactById(id);
            return "OK. User is deleted\n";
        } catch (ContactNotFoundException e) {
            return "Delete Error\n Contact not found\n";
        }
    }

    public String deleteContact(String phoneNumber) {
        try {
            MobileContact mobileContact = service.getContactByPhoneNumber(phoneNumber);
            MobileContactReadOnlyDTO readOnlyDTO = mapMobileContactToDTO(mobileContact);
            service.deleteContactByPhoneNumber(phoneNumber);
            return "OK\n" + serializeDTO(readOnlyDTO) + "\n";
        } catch (ContactNotFoundException e) {
            return "Delete Error\n Contact not found\n";
        }
    }

    public String getContactById(Long id) {
        try {
            MobileContact mobileContact = service.getContactById(id);
            MobileContactReadOnlyDTO readOnlyDTO = mapMobileContactToDTO(mobileContact);
            return "OK\n" + serializeDTO(readOnlyDTO) + "\n";
        } catch (ContactNotFoundException e) {
            return "Contact not found\n";
        }
    }

    public  String getContactByPhoneNumber(String phoneNumber) {
        try {
            MobileContact mobileContact = service.getContactByPhoneNumber(phoneNumber);
            MobileContactReadOnlyDTO readOnlyDTO = mapMobileContactToDTO(mobileContact);
            return "OK\n" + serializeDTO(readOnlyDTO) + "\n";
        } catch (ContactNotFoundException e) {
            return "Error\n Contact not found\n";
        }
    }

    public List<String> getAllContacts() {
        List<MobileContact> contacts;
        List<String> serializedList = new ArrayList<>();

        contacts = service.getAllContacts();
        for (MobileContact contact : contacts ) {
            String serialized = serializeDTO(mapMobileContactToDTO(contact));
            serializedList.add(serialized);
        }
        return serializedList;
    }

    /*
     * Ο Mapper(mapMobileContactToDTO())απλά αντιστοιχεί το model entity σε ReadOnlyDTO
     * ώστε να επιστραφεί από τον Controller.Ο Controller δέχεται DTO και επιστρέφει DTO
     */
    private MobileContactReadOnlyDTO mapMobileContactToDTO(MobileContact mobileContact) {
        return new MobileContactReadOnlyDTO(mobileContact.getId(), mobileContact.getFirstname(),
                mobileContact.getLastname(), mobileContact.getPhoneNumber());
    }

    // Ο serializer(serializeDTO()) μετατρέπει το DTO σε String για να το στείλει στον Client.
    private String serializeDTO(MobileContactReadOnlyDTO readOnlyDTO) {
        return "ID: " + readOnlyDTO.getId() + "Firstname: " + readOnlyDTO.getFirstname() +
                "Lastname: " + readOnlyDTO.getLastname() + "Phone number: " +
                readOnlyDTO.getPhoneNumber();
    }




}
