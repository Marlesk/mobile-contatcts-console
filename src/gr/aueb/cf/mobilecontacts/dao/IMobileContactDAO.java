package gr.aueb.cf.mobilecontacts.dao;

import gr.aueb.cf.mobilecontacts.model.MobileContact;

import java.util.List;

public interface IMobileContactDAO {
    MobileContact insert(MobileContact mobileContact);
    MobileContact update(Long id, MobileContact mobileContact);

    void deleteById(Long id);
    void deleteByPhoneNumber(String phoneNumber);

    MobileContact getById(Long id);
    MobileContact getByPhoneNumber(String phoneNumber);
    List<MobileContact> getAll();

    boolean userIdExists(Long id);
    boolean phoneNumberExists(String phoneNumber);


}
