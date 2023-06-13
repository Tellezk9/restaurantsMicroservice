package com.pragma.powerup.restaurantmicroservice.domain.model;

public class Employee {
    private Long id;
    private Long idEmployee;
    private Restaurant idRestaurant;

    public Employee(Long id, Long idEmployee, Restaurant idRestaurant) {
        this.id = id;
        this.idEmployee = idEmployee;
        this.idRestaurant = idRestaurant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Long idEmployee) {
        this.idEmployee = idEmployee;
    }

    public Restaurant getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(Restaurant idRestaurant) {
        this.idRestaurant = idRestaurant;
    }
}
