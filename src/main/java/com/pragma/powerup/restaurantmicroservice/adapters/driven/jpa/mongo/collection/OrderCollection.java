package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mongo.collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;
import java.util.Map;


@Document(collection = "Orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderCollection {
    @MongoId
    private String id;
    private Long idOrder;
    private Long idClient;
    private Long idEmployee;
    private String dateInit;
    private String dateEnd;
    private Long previousStatus;
    private Long actualStatus;
    private List<Map<String, String>> order;
}
