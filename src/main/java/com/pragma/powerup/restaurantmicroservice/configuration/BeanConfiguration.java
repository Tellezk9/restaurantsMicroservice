package com.pragma.powerup.restaurantmicroservice.configuration;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.adapter.*;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.mappers.*;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.repositories.*;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.adapter.HttpAdapterImpl;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers.IClientHandler;
import com.pragma.powerup.restaurantmicroservice.configuration.security.TokenHolder;
import com.pragma.powerup.restaurantmicroservice.domain.api.IDishServicePort;
import com.pragma.powerup.restaurantmicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.restaurantmicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.restaurantmicroservice.domain.geteway.IHttpAdapter;
import com.pragma.powerup.restaurantmicroservice.domain.spi.*;
import com.pragma.powerup.restaurantmicroservice.domain.usecase.DishUseCase;
import com.pragma.powerup.restaurantmicroservice.domain.usecase.OrderUseCase;
import com.pragma.powerup.restaurantmicroservice.domain.usecase.RestaurantUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final IDishEntityMapper dishEntityMapper;
    private final IDishRepository dishRepository;
    private final IEmployeeRepository employeeRepository;
    private final IEmployeeEntityMapper employeeEntityMapper;
    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;
    private final IOrderDishRepository orderDishRepository;
    private final IOrderDishEntityMapper orderDishEntityMapper;
    private final IClientHandler clientHandler;
    private final TokenHolder tokenHolder;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public IRestaurantServicePort restaurantServicePort() {
        return new RestaurantUseCase(restaurantPersistencePort(), employeePersistencePort(), tokenHolder, httpAdapter());
    }

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort() {
        return new RestaurantMysqlAdapter(restaurantRepository, restaurantEntityMapper);
    }

    @Bean
    public IEmployeePersistencePort employeePersistencePort() {
        return new EmployeeMysqlAdapter(employeeRepository, employeeEntityMapper);
    }

    @Bean
    public IDishServicePort dishServicePort() {
        return new DishUseCase(dishPersistencePort(), tokenHolder, restaurantPersistencePort());
    }

    @Bean
    public IDishPersistencePort dishPersistencePort() {
        return new DishMysqlAdapter(dishEntityMapper, dishRepository);
    }

    @Bean
    public IHttpAdapter httpAdapter() {
        return new HttpAdapterImpl(restTemplate(), clientHandler);
    }

    @Bean
    public IOrderServicePort orderServicePort() {
        return new OrderUseCase(orderPersistencePort(), dishPersistencePort(), orderDishPersistencePort(), employeePersistencePort(), tokenHolder, httpAdapter());
    }

    @Bean
    public IOrderPersistencePort orderPersistencePort() {
        return new OrderMysqlAdapter(orderRepository, orderEntityMapper);
    }

    @Bean
    public IOrderDishPersistencePort orderDishPersistencePort() {
        return new OrderDishMysqlAdapter(orderDishRepository, orderDishEntityMapper);
    }
}
