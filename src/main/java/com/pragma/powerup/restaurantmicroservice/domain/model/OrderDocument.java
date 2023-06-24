package com.pragma.powerup.restaurantmicroservice.domain.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderDocument {
    private String id;
    private Long idOrder;
    private Long idClient;
    private Long idEmployee;
    private Long idRestaurant;
    private Date dateInit;
    private Date dateEnd;
    private Long previousStatus;
    private Long actualStatus;
    private List<Map<String, String>> order;

    public OrderDocument(String id, Long idOrder, Long idClient, Long idEmployee, Long idRestaurant, Date dateInit, Date dateEnd, Long previousStatus, Long actualStatus, List<Map<String, String>> order) {
        this.id = id;
        this.idOrder = idOrder;
        this.idClient = idClient;
        this.idEmployee = idEmployee;
        this.idRestaurant = idRestaurant;
        this.dateInit = dateInit;
        this.dateEnd = dateEnd;
        this.previousStatus = previousStatus;
        this.actualStatus = actualStatus;
        this.order = order;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Long idOrder) {
        this.idOrder = idOrder;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public Long getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Long idEmployee) {
        this.idEmployee = idEmployee;
    }

    public Long getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(Long idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public Date getDateInit() {
        return dateInit;
    }

    public void setDateInit(Date dateInit) {
        this.dateInit = dateInit;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Long getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(Long previousStatus) {
        this.previousStatus = previousStatus;
    }

    public Long getActualStatus() {
        return actualStatus;
    }

    public void setActualStatus(Long actualStatus) {
        this.actualStatus = actualStatus;
    }

    public List<Map<String, String>> getOrder() {
        return order;
    }

    public void setOrder(List<Map<String, String>> order) {
        this.order = order;
    }
}
