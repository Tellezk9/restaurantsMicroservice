package com.pragma.powerup.usermicroservice.domain.model;

public class Owner {
    private String name;
    private String lastName;
    private Integer dniNumber;
    private String phone;
    private String birthDate;
    private String mail;
    private String password;
    private Role role;

    public Owner(String name, String lastName, Integer dniNumber, String phone, String birthDate, String mail, String password, Role role) {
        this.name = name;
        this.lastName = lastName;
        this.dniNumber = dniNumber;
        this.phone = phone;
        this.birthDate = birthDate;
        this.mail = mail;
        this.password = password;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getDniNumber() {
        return dniNumber;
    }

    public void setDniNumber(Integer dniNumber) {
        this.dniNumber = dniNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
