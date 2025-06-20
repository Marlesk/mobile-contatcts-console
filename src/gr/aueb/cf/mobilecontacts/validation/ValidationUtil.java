package gr.aueb.cf.mobilecontacts.validation;

import gr.aueb.cf.mobilecontacts.dto.MobileContactInsertDTO;
import gr.aueb.cf.mobilecontacts.dto.MobileContactUpdateDTO;

public class ValidationUtil {

    public ValidationUtil() {
    }

    public static String validateDTO(MobileContactInsertDTO mobileContactInsertDTO) {
        String errorResponse = "";
        if (mobileContactInsertDTO.getPhoneNumber().trim().length() <= 5)
            errorResponse += "phone length must be greater than 5 symbols \n";
        if (mobileContactInsertDTO.getFirstname().trim().length() < 2)
            errorResponse += "firstname length must be greater-equal than 2 symbols \n";
        if (mobileContactInsertDTO.getLastname().trim().length() < 2)
            errorResponse += "lastname length must be greater-equal than 2 symbols \n";

        return errorResponse;
    }

    public static String validateDTO(MobileContactUpdateDTO mobileContactUpdateDTO) {
        String errorResponse = "";
        if (mobileContactUpdateDTO.getPhoneNumber().trim().length() <= 5)
            errorResponse += "phone length must be greater than 5 symbols \n";
        if (mobileContactUpdateDTO.getFirstname().trim().length() < 2)
            errorResponse += "firstname length must be greater-equal than 2 symbols \n";
        if (mobileContactUpdateDTO.getLastname().trim().length() < 2)
            errorResponse += "lastname length must be greater-equal than 2 symbols \n";

        return errorResponse;
    }
}
