package com.pragma.powerup.restaurantmicroservice.domain.model;

public class RankingEmployee {
    private Long idEmployee;
    private Long average;

    public RankingEmployee(Long idEmployee, Long average) {
        this.idEmployee = idEmployee;
        this.average = average;
    }

    public Long getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Long idEmployee) {
        this.idEmployee = idEmployee;
    }

    public Long getAverage() {
        return average;
    }

    public void setAverage(Long average) {
        this.average = average;
    }
}
