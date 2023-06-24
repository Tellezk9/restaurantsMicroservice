package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.exceptions.ClientHasPendingOrderException;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.exceptions.InvalidOrderStatusException;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.exceptions.OrderCannotBeCanceledException;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.exceptions.OrderNotFoundException;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.mappers.IOrderEntityMapper;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.repositories.IOrderRepository;
import com.pragma.powerup.restaurantmicroservice.configuration.Constants;
import com.pragma.powerup.restaurantmicroservice.domain.model.Order;
import com.pragma.powerup.restaurantmicroservice.domain.spi.mySql.IOrderPersistencePort;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.pragma.powerup.restaurantmicroservice.configuration.Constants.MAX_PAGE_SIZE;

@AllArgsConstructor
public class OrderMysqlAdapter implements IOrderPersistencePort {
    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;

    @Override
    public void saveOrderInformation(Order order) {
        Optional<OrderEntity> orderEntity = orderRepository.findByIdClientAndRestaurantEntityIdAndStatusNotAndStatusNotAndStatusNot(order.getIdClient(), order.getRestaurant().getId(), Constants.ORDER_STATUS_OK, Constants.ORDER_STATUS_DELIVERED, Constants.ORDER_STATUS_CANCELED);
        if (orderEntity.isPresent()) {
            throw new ClientHasPendingOrderException();
        }
        orderRepository.save(orderEntityMapper.toOrderEntity(order));
    }

    @Override
    public Order findOrderInformation(Order order) {
        Optional<OrderEntity> orderEntity = orderRepository.findByIdClientAndRestaurantEntityIdAndStatus(order.getIdClient(), order.getRestaurant().getId(), order.getStatus());
        if (orderEntity.isEmpty()) {
            throw new OrderNotFoundException();
        }

        return orderEntityMapper.toOrder(orderEntity.get());
    }

    @Override
    public Order getOrder(Long idOrder) {
        Optional<OrderEntity> orderEntity = orderRepository.findById(idOrder);
        if (orderEntity.isEmpty()) {
            throw new OrderNotFoundException();
        }

        return orderEntityMapper.toOrder(orderEntity.get());
    }

    @Override
    public Order getOrderBySecurityPinAndIdRestaurant(Long securityPin, Long idRestaurant) {
        Optional<OrderEntity> orderEntity = orderRepository.findBySecurityPinAndRestaurantEntityId(securityPin, idRestaurant);
        if (orderEntity.isEmpty()) {
            throw new OrderNotFoundException();
        }

        return orderEntityMapper.toOrder(orderEntity.get());
    }

    @Override
    public List<Order> getOrdersPageable(Long status, Long idRestaurant, Integer page) {
        Pageable pagination = PageRequest.of(page, MAX_PAGE_SIZE);
        List<OrderEntity> orderEntityList = orderRepository.findByRestaurantEntityIdAndStatus(idRestaurant, status, pagination);

        if (orderEntityList.isEmpty()) {
            throw new OrderNotFoundException();
        }
        return orderEntityMapper.toOrderList(orderEntityList);
    }

    @Override
    public void assignOrder(Long idOrder, Long idEmployee, Long status) {
        Optional<OrderEntity> orderEntity = orderRepository.findById(idOrder);

        if (orderEntity.isEmpty()) {
            throw new OrderNotFoundException();
        }
        orderEntity.get().setStatus(status);
        orderEntity.get().setIdChef(idEmployee);
        orderRepository.save(orderEntity.get());
    }

    @Override
    public void changeOrderStatus(Long idOrder, Long status) {
        Optional<OrderEntity> orderEntity = orderRepository.findById(idOrder);
        if (orderEntity.isEmpty()) {
            throw new OrderNotFoundException();
        }
        if (orderEntity.get().getStatus().equals(Constants.ORDER_STATUS_DELIVERED) && !status.equals(Constants.ORDER_STATUS_OK)) {
            throw new InvalidOrderStatusException();
        }
        orderEntity.get().setStatus(status);
        orderRepository.save(orderEntity.get());
    }

    @Override
    public void deliverOrder(Long securityPin, Long idRestaurant, Long status) {
        Optional<OrderEntity> orderEntity = orderRepository.findBySecurityPinAndRestaurantEntityIdAndStatus(securityPin, idRestaurant, Constants.ORDER_STATUS_OK);
        if (orderEntity.isEmpty()) {
            throw new OrderNotFoundException();
        }
        orderEntity.get().setStatus(status);
        orderRepository.save(orderEntity.get());
    }

    @Override
    public void cancelOrder(Long idOrder, Long idClient, Long status) {
        Optional<OrderEntity> orderEntity = orderRepository.findByIdAndIdClient(idOrder, idClient);
        if (orderEntity.isEmpty()) {
            throw new OrderNotFoundException();
        }
        if (!orderEntity.get().getStatus().equals(Constants.ORDER_STATUS_PENDING)) {
            throw new OrderCannotBeCanceledException();
        }
        orderEntity.get().setStatus(status);
        orderRepository.save(orderEntity.get());
    }
}
