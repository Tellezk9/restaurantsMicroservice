package com.pragma.powerup.restaurantmicroservice.configuration;

public class Constants {

    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    public static final Long CLIENT_ROLE_ID = 1L;
    public static final Long EMPLOYEE_ROLE_ID = 2L;
    public static final Long OWNER_ROLE_ID = 3L;
    public static final Long ADMIN_ROLE_ID = 4L;
    public static final String CLIENT_ROLE_NAME = "ROLE_CLIENT";
    public static final String EMPLOYEE_ROLE_NAME = "ROLE_EMPLOYEE";
    public static final String OWNER_ROLE_NAME = "ROLE_OWNER";
    public static final String ADMIN_ROLE_NAME = "ROLE_ADMIN";
    public static final Long ORDER_STATUS_OK = 1L;
    public static final Long ORDER_STATUS_PENDING = 2L;
    public static final Long ORDER_STATUS_PREPARING = 3L;
    public static final Long ORDER_STATUS_DELIVERED = 4L;

    public static final int MAX_PAGE_SIZE = 3;

    public static final String RESPONSE_MESSAGE_KEY = "message";
    public static final String RESTAURANT_CREATED_MESSAGE = "Restaurant created successfully";
    public static final String RESTAURANT_EMPLOYEE_CREATED_MESSAGE = "Restaurant employee created successfully";
    public static final String DISH_CREATED_MESSAGE = "Dish created successfully";
    public static final String DISH_UPDATED_MESSAGE = "Dish updated successfully";
    public static final String ORDER_CREATED_MESSAGE = "Order created successfully";
    public static final String ORDER_ASSIGNED_MESSAGE = "Order assigned successfully";
    public static final String ORDER_STATUS_CHANGED_MESSAGE = "Order status changed successfully";
    public static final String ORDER_DELIVERED_MESSAGE = "Order delivered successfully";
    public static final String RESPONSE_ERROR_MESSAGE_KEY = "error";
    public static final String RESPONSE_ERROR_EXECUTION = "error to make the process";
    public static final String ROLE_NOT_ALLOWED_FOR_ACTION_MESSAGE = "Permission to perform this action has not been granted with this role";
    public static final String WRONG_CREDENTIALS_MESSAGE = "Wrong credentials";
    public static final String NO_DATA_FOUND_MESSAGE = "No data found for the requested petition";
    public static final String PERSON_ALREADY_EXISTS_MESSAGE = "A person already exists with the DNI number provided";
    public static final String MAIL_ALREADY_EXISTS_MESSAGE = "A person with that mail already exists";
    public static final String PERSON_NOT_FOUND_MESSAGE = "No person found with the id provided";
    public static final String ROLE_NOT_FOUND_MESSAGE = "No role found with the id provided";
    public static final String ROLE_NOT_ALLOWED_MESSAGE = "No permission granted to create users with this role";
    public static final String USER_ALREADY_EXISTS_MESSAGE = "A user already exists with the role provided";
    public static final String EMPLOYEE_ALREADY_ASSIGNED_WITH_A_RESTAURANT_MESSAGE = "Employee already assigned with a restaurant";
    public static final String INVALID_FORMAT_DATE_MESSAGE = "Date must be in the format 'yyyy/MM/dd'";
    public static final String RESTAURANT_ALREADY_EXISTS_MESSAGE = "A restaurant already exists with the nit";
    public static final String INVALID_RESTAURANT_NAME_MESSAGE = "The name of the restaurant should not be just numbers";
    public static final String RESTAURANT_NOT_FOUND_MESSAGE = "No restaurant found";
    public static final String DISH_DOES_NOT_BELONG_MESSAGE = "The dish does not belong to the restaurant";
    public static final String EMPTY_FIELD_FOUND_MESSAGE = "All fields must be completed";
    public static final String ORDER_AND_AMOUNT_IS_NOT_EQUALS_MESSAGE = "each order of dishes must have its respective amount";
    public static final String CLIENT_HAS_PENDING_ORDER_MESSAGE = "The client has a pending order";
    public static final String ORDER_NOT_FOUND_MESSAGE = "Order not found";
    public static final String EMPLOYEE_DOES_NOT_BELONG_RESTAURANT_MESSAGE = "The employee does not belong to the restaurant";
    public static final String INVALID_VALUE_MESSAGE = "The value/s must be greater than 0(zero)";
    public static final String INVALID_STATUS_MESSAGE = "The status value is invalid";
    public static final String INVALID_ORDER_STATUS_MESSAGE = "the status cannot be assigned to the order";

    public static final String USER_IS_NOT_LEGAL_AGE = "The user is not legal age";
    public static final String INVALID_FORMAT_URL_MESSAGE = "The url is not valid";
    public static final String INVALID_PRICE_MESSAGE = "The price must be greater than zero (0)";
    public static final String USER_NOT_FOUND_MESSAGE = "No user found with the role provided";
    public static final String DISH_NOT_FOUND_MESSAGE = "No dish found with";
    public static final String USER_NOT_FOUND_WITH_ROLE_MESSAGE = "No user found with the role";
    public static final String NOT_OWNER_THE_RESTAURANT_MESSAGE = "You are not the owner of this restaurant.";
    public static final String SWAGGER_TITLE_MESSAGE = "User API Pragma Power Up";
    public static final String SWAGGER_DESCRIPTION_MESSAGE = "User microservice";
    public static final String SWAGGER_VERSION_MESSAGE = "1.0.0";
    public static final String SWAGGER_LICENSE_NAME_MESSAGE = "Apache 2.0";
    public static final String SWAGGER_LICENSE_URL_MESSAGE = "http://springdoc.org";
    public static final String SWAGGER_TERMS_OF_SERVICE_MESSAGE = "http://swagger.io/terms/";
}
