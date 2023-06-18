package com.pragma.powerup.restaurantmicroservice.domain.spi;

import com.pragma.powerup.restaurantmicroservice.domain.model.Employee;

public interface IEmployeePersistencePort {
    void saveEmployee(Employee employee);
    Employee getEmployeeByIdEmployeeAndIdRestaurant(Long idEmployee, Long idRestaurant);
    Employee getEmployeeById(Long idEmployee);
}
