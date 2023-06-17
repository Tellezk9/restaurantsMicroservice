package com.pragma.powerup.restaurantmicroservice.domain.model;

public class Client {
    private Long id;
    private Long idOrder;
    private String name;
    private String lastName;
    private Integer dniNumber;
    private String phone;
    private String birthDate;
    private String mail;

    public Client(Long id, Long idOrder, String name, String lastName, Integer dniNumber, String phone, String birthDate, String mail) {
        this.id = id;
        this.idOrder = idOrder;
        this.name = name;
        this.lastName = lastName;
        this.dniNumber = dniNumber;
        this.phone = phone;
        this.birthDate = birthDate;
        this.mail = mail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Long idOrder) {
        this.idOrder = idOrder;
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
}
