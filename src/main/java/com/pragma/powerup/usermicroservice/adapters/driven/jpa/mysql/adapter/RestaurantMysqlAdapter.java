package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.NoDataFoundException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.RestaurantAlreadyExistsException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.IRestaurantEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantRepository;
import com.pragma.powerup.usermicroservice.domain.exceptions.NotOwnerTheRestaurantException;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import com.pragma.powerup.usermicroservice.domain.spi.IRestaurantPersistencePort;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.pragma.powerup.usermicroservice.configuration.Constants.MAX_PAGE_SIZE;

@AllArgsConstructor
public class RestaurantMysqlAdapter implements IRestaurantPersistencePort {
    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        if (restaurantRepository.findByNit(Integer.toString(restaurant.getNit())).isPresent()) {
            throw new RestaurantAlreadyExistsException();
        }
        restaurantRepository.save(restaurantEntityMapper.toRestaurantEntity(restaurant));
    }

    @Override
    public Restaurant getRestaurant(Long idRestaurant) {

        Optional<RestaurantEntity> restaurantEntity = restaurantRepository.findById(idRestaurant);
        if (!restaurantEntity.isPresent()) {
            throw new NoDataFoundException();
        }
        return restaurantEntityMapper.toRestaurant(restaurantEntity.get());
    }

    @Override
    public List<String[]> getRestaurants(Integer page) {
        Pageable pagination = PageRequest.of(page, MAX_PAGE_SIZE);
        List<String[]> restaurantEntities = restaurantRepository.getNameAndUrl(pagination);
        if (restaurantEntities.isEmpty()){
            throw new NoDataFoundException();
        }
        return restaurantEntities;
    }

    @Override
    public List<Restaurant> getOwnerRestaurants(Long idOwner) {
        List<RestaurantEntity> restaurantEntities = restaurantRepository.findByIdOwner(idOwner);
        if (restaurantEntities.isEmpty()) {
            throw new NoDataFoundException();
        }
        return restaurantEntityMapper.toRestaurantList(restaurantEntities);
    }

    @Override
    public Restaurant getRestaurantByIdOwnerAndIdRestaurant(Long idOwner, Long idRestaurant) {
        Optional<RestaurantEntity> restaurantEntity = restaurantRepository.findByIdOwnerAndId(idOwner, idRestaurant);
        if (!restaurantEntity.isPresent()) {
            throw new NotOwnerTheRestaurantException();
        }
        return restaurantEntityMapper.toRestaurant(restaurantEntity.get());
    }
}
