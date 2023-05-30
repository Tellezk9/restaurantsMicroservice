package com.pragma.powerup.usermicroservice.configuration;

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

    public static final String RESPONSE_MESSAGE_KEY = "message";
    public static final String RESTAURANT_CREATED_MESSAGE = "Restaurant created successfully";
    public static final String DISH_CREATED_MESSAGE = "Dish created successfully";
    public static final String DISH_UPDATED_MESSAGE = "Dish updated successfully";
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
    public static final String RESTAURANT_ALREADY_EXISTS_MESSAGE = "A restaurant already exists with the nit";
    public static final String INVALID_RESTAURANT_NAME_MESSAGE = "The name of the restaurant should not be just numbers";
    public static final String USER_IS_NOT_LEGAL_AGE = "The user is not legal age";
    public static final String INVALID_FORMAT_URL_MESSAGE = "The url is not valid";
    public static final String INVALID_PRICE_MESSAGE = "The price must be greater than zero (0)";
    public static final String USER_NOT_FOUND_MESSAGE = "No user found with the role provided";
    public static final String USER_NOT_FOUND_WITH_ROLE_MESSAGE = "No user found with the role";
    public static final String NOT_OWNER_THE_RESTAURANT_MESSAGE = "You are not the owner of this restaurant.";
    public static final String SWAGGER_TITLE_MESSAGE = "User API Pragma Power Up";
    public static final String SWAGGER_DESCRIPTION_MESSAGE = "User microservice";
    public static final String SWAGGER_VERSION_MESSAGE = "1.0.0";
    public static final String SWAGGER_LICENSE_NAME_MESSAGE = "Apache 2.0";
    public static final String SWAGGER_LICENSE_URL_MESSAGE = "http://springdoc.org";
    public static final String SWAGGER_TERMS_OF_SERVICE_MESSAGE = "http://swagger.io/terms/";
}
