package com.pragma.powerup.usermicroservice.domain.service;

import com.pragma.powerup.usermicroservice.domain.exceptions.*;
import com.pragma.powerup.usermicroservice.domain.model.Owner;
import com.pragma.powerup.usermicroservice.domain.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class OwnerValidatorTest {

    private OwnerValidator ownerValidator;

    @BeforeEach
    void setUp() {
        ownerValidator = new OwnerValidator();
    }

    @Test
    void allFieldsFilled() {
        Role role = new Role(4L, null, null);
        Owner owner = new Owner("test", "testLastName", 1234, "+123456789012", "2002/05/01", "test@gmail.com", "1234", role);
        assertTrue(() -> ownerValidator.allFieldsFilled(owner));
    }

    @ParameterizedTest
    @CsvSource({
            ",testLastName, 1234, +123456789012, 2002/05/01, test@gmail.com, 1234",
            "test,, 1234, +123456789012, 2002/05/01, test@gmail.com, 1234",
            "test,testLastName, , +123456789012, 2002/05/01, test@gmail.com, 1234",
            "test,testLastName, 1234, , 2002/05/01, test@gmail.com, 1234",
            "test,testLastName, 1234, +123456789012, , test@gmail.com, 1234",
            "test,testLastName, 1234, +123456789012, 2002/05/01, , 1234",
            "test,testLastName, 1234, +123456789012, 2002/05/01, test@gmail.com, ",
    })
    void isEmptyWithoutParams(String name, String lastName, Integer dniNumber, String phone, String birthDate, String mail, String password) {

        Role role = new Role(4L, null, null);
        Owner owner = new Owner(name, lastName, dniNumber, phone, birthDate, mail, password, role);

        assertThrows(EmptyFieldFoundException.class, () -> ownerValidator.allFieldsFilled(owner));
    }

    @Test
    @DisplayName("when the phone is valid")
    void isValidPhone() {
        assertTrue(() -> ownerValidator.isValidPhone("+123456789012"));
    }

    @ParameterizedTest
    @CsvSource({
            "+123456789",
            "+1234567890129",
    })
    @DisplayName("when the number has less than 10 or more than 13 digits")
    void isValidPhoneOutOfRange(String phone) {
        assertThrows(InvalidPhoneLengthException.class, () -> ownerValidator.isValidPhone(phone));
    }

    @Test
    @DisplayName("when the phone is empty")
    void isValidPhoneEmpty() {
        assertThrows(EmptyFieldFoundException.class, () -> ownerValidator.isValidPhone(""));
    }

    @Test
    @DisplayName("When the phone does not have the plus (+) character")
    void isValidPhoneWithoutCharacter() {
        assertThrows(InvalidPhoneException.class, () -> ownerValidator.isValidPhone("123456789012"));
    }

    @Test
    void isOlder() {
        assertTrue(() -> ownerValidator.isOlder("2002/05/01"));
    }

    @Test
    @DisplayName("when the owner isn't older")
    void isNotOlder() {
        assertThrows(UserIsNotOfLegalAgeException.class, () -> ownerValidator.isOlder("2012/05/01"));
    }

    @Test
    @DisplayName("when the date is empty")
    void isOlderEmpty() {
        assertThrows(EmptyFieldFoundException.class, () -> ownerValidator.isOlder(""));
    }

    @Test
    void isValidMail() {
        assertTrue(ownerValidator.isValidMail("test@gmail.com"));
    }
    @Test
    @DisplayName("When the Mail does not have the @ character or (.)")
    void isNotValidMail(){
        assertThrows(InvalidFormatMailException.class,()-> ownerValidator.isValidMail("test@icom"));
        assertThrows(InvalidFormatMailException.class,()-> ownerValidator.isValidMail("testi.com"));
        assertThrows(InvalidFormatMailException.class,()-> ownerValidator.isValidMail("testicom"));
    }
    @Test
    @DisplayName("when the date is empty")
    void isEmptyMail(){
        assertThrows(EmptyFieldFoundException.class,()-> ownerValidator.isValidMail(""));
    }
}