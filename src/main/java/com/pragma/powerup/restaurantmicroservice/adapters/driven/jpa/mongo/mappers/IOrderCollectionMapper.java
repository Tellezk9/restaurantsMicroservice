package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mongo.mappers;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mongo.collection.OrderCollection;
import com.pragma.powerup.restaurantmicroservice.domain.model.OrderDocument;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderCollectionMapper {
    OrderCollection toOrderCollection(OrderDocument orderDocument);
    OrderDocument toOrderDocument(OrderCollection orderCollection);
    List<OrderDocument> toOrderDocumentList(List<OrderCollection> orderCollectionsList);
}