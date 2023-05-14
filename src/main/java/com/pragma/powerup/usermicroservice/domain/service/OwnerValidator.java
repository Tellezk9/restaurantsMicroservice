package com.pragma.powerup.usermicroservice.domain.service;

import com.pragma.powerup.usermicroservice.domain.exceptions.*;
import com.pragma.powerup.usermicroservice.domain.model.Owner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OwnerValidator {

    public boolean allFieldsFilled(Owner owner) {
        if (
                (owner.getName() == null || owner.getName().isEmpty()) ||
                        (owner.getLastName() == null || owner.getLastName().isEmpty()) ||
                        (owner.getDniNumber() == null || owner.getDniNumber() <= 0) ||
                        (owner.getPhone() == null || owner.getPhone().isEmpty()) ||
                        (owner.getBirthDate() == null || owner.getBirthDate().isBlank()) ||
                        (owner.getMail() == null || owner.getMail().isEmpty()) ||
                        (owner.getPassword() == null || owner.getPassword().isEmpty())
        ) {
            throw new EmptyFieldFoundException();
        }
        return true;

    }

    public boolean isValidPhone(String phone) {
        if (phone.isEmpty()) {
            throw new EmptyFieldFoundException();
        }
        if (phone.length() > 13) {
            throw new InvalidPhoneLengthException();
        }
        if (phone.length() > 10) {
            char[] charPhone = phone.toCharArray();
            if (charPhone[0] != 43) {
                throw new InvalidPhoneException();
            }
            for (int i = 1; i < charPhone.length; i++) {
                if (charPhone[i] < 48 || charPhone[i] > 57) {
                    throw new InvalidPhoneException();
                }
            }
        }
        if (phone.length() <= 10) {
            throw new InvalidPhoneLengthException();
        }
        return true;
    }

    public boolean isOlder(String date) {
        if (date == null || date.isEmpty()) {
            throw new EmptyFieldFoundException();
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate lastDate = LocalDate.parse(date, formatter);
        LocalDate validDate = LocalDate.now().minusYears(18);
        if (validDate.isBefore(lastDate)) {
            throw new UserIsNotOfLegalAgeException();
        }
        return true;
    }

    public boolean isValidMail(String mail) {
        if (mail == null || mail.isEmpty()) {
            throw new EmptyFieldFoundException();
        }
        if (!(mail.contains("@") && mail.contains("."))) {
            throw new InvalidFormatMailException();
        }
        return true;
    }
}
