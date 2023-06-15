package com.pragma.powerup.restaurantmicroservice.configuration;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.exceptions.*;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.exceptions.ErrorExecutionException;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.exceptions.UserRoleNotFoundException;
import com.pragma.powerup.restaurantmicroservice.domain.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.pragma.powerup.restaurantmicroservice.configuration.Constants.*;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errorMessages = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            if (error instanceof FieldError fieldError) {
                errorMessages.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
            } else {
                errorMessages.add(error.getDefaultMessage());
            }
        }
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoDataFoundException(NoDataFoundException noDataFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, NO_DATA_FOUND_MESSAGE));
    }
    @ExceptionHandler(PersonAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handlePersonAlreadyExistsException(
            PersonAlreadyExistsException personAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, PERSON_ALREADY_EXISTS_MESSAGE));
    }

    @ExceptionHandler(MailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleMailAlreadyExistsException(
            MailAlreadyExistsException mailAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, MAIL_ALREADY_EXISTS_MESSAGE));
    }
    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlePersonNotFoundException(
            PersonNotFoundException personNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, PERSON_NOT_FOUND_MESSAGE));
    }
    @ExceptionHandler(RoleNotAllowedForCreationException.class)
    public ResponseEntity<Map<String, String>> handleRoleNotAllowedForCreationException(
            RoleNotAllowedForCreationException roleNotAllowedForCreationException) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, ROLE_NOT_ALLOWED_MESSAGE));
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyExistsException(
            UserAlreadyExistsException userAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, USER_ALREADY_EXISTS_MESSAGE));
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(
            UserNotFoundException userNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, USER_NOT_FOUND_MESSAGE));
    }
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleRoleNotFoundException(
            RoleNotFoundException roleNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, ROLE_NOT_FOUND_MESSAGE));
    }

    @ExceptionHandler(InvalidRestaurantNameException.class)
    public ResponseEntity<Map<String,String>> invalidRestaurantNameException(
            InvalidRestaurantNameException invalidRestaurantNameException
    ){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, INVALID_RESTAURANT_NAME_MESSAGE));
    }
    @ExceptionHandler(UserIsNotOfLegalAgeException.class)
    public ResponseEntity<Map<String,String>> userIsNotOfLegalAgeException(
            UserIsNotOfLegalAgeException userIsNotOfLegalAgeException
    ){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, USER_IS_NOT_LEGAL_AGE));
    }

    @ExceptionHandler(RestaurantAlreadyExistsException.class)
    public ResponseEntity<Map<String,String>> restaurantAlreadyExistsException(
            RestaurantAlreadyExistsException restaurantAlreadyExistsException
    ){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, RESTAURANT_ALREADY_EXISTS_MESSAGE));
    }
    @ExceptionHandler(UserRoleNotFoundException.class)
    public ResponseEntity<Map<String, String>>userRoleNotFoundException(UserRoleNotFoundException userRoleNotFoundException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, USER_NOT_FOUND_WITH_ROLE_MESSAGE));
    }

    @ExceptionHandler(InvalidFormatUrlException.class)
    public ResponseEntity<Map<String, String>>invalidFormatUrlException(InvalidFormatUrlException invalidFormatUrlException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, INVALID_FORMAT_URL_MESSAGE));
    }

    @ExceptionHandler(InvalidPriceException.class)
    public ResponseEntity<Map<String, String>>invalidPriceException(InvalidPriceException invalidPriceException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, INVALID_PRICE_MESSAGE));
    }

    @ExceptionHandler(ErrorExecutionException.class)
    public ResponseEntity<Map<String, String>>errorExcecutionException(ErrorExecutionException errorExcecutionException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, RESPONSE_ERROR_EXECUTION));
    }

    @ExceptionHandler(RoleNotAllowedForThisActionException.class)
    public ResponseEntity<Map<String, String>>roleNotAllowedForThisActionException(RoleNotAllowedForThisActionException roleNotAllowedForThisActionException){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, ROLE_NOT_ALLOWED_FOR_ACTION_MESSAGE));
    }

    @ExceptionHandler(NotOwnerTheRestaurantException.class)
    public ResponseEntity<Map<String, String>>notOwnerTheRestaurantException(NotOwnerTheRestaurantException notOwnerTheRestaurantException){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, NOT_OWNER_THE_RESTAURANT_MESSAGE));
    }

    @ExceptionHandler(EmployeeAlreadyAssignedException.class)
    public ResponseEntity<Map<String, String>>employeeAlreadyAssignedException(EmployeeAlreadyAssignedException employeeAlreadyAssignedException){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, EMPLOYEE_ALREADY_ASSIGNED_WITH_A_RESTAURANT_MESSAGE));
    }

    @ExceptionHandler(InvalidFormatDateException.class)
    public ResponseEntity<Map<String, String>>invalidFormatDateException(InvalidFormatDateException invalidFormatDateException){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, INVALID_FORMAT_DATE_MESSAGE));
    }
    @ExceptionHandler(DishNotFoundException.class)
    public ResponseEntity<Map<String, String>> dishNotFoundException(
            DishNotFoundException dishNotFoundException) {
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, DISH_NOT_FOUND_MESSAGE));
    }
    @ExceptionHandler(NoRestaurantFoundException.class)
    public ResponseEntity<Map<String, String>> noRestaurantFoundException(
            NoRestaurantFoundException noRestaurantFoundException) {
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, RESTAURANT_NOT_FOUND_MESSAGE));
    }
    @ExceptionHandler(DishDoesNotBelongToTheRestaurantException.class)
    public ResponseEntity<Map<String, String>> dishDoesNotBelongToTheRestaurantException(
            DishDoesNotBelongToTheRestaurantException dishDoesNotBelongToTheRestaurantException) {
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, DISH_DOES_NOT_BELONG_MESSAGE));
    }

    @ExceptionHandler(EmptyFieldFoundException.class)
    public ResponseEntity<Map<String, String>> emptyFieldFoundException(
            EmptyFieldFoundException emptyFieldFoundException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, EMPTY_FIELD_FOUND_MESSAGE));
    }

    @ExceptionHandler(OrderAndAmountIsNotEqualsException.class)
    public ResponseEntity<Map<String, String>> orderAndAmountIsNotEqualsException(
            OrderAndAmountIsNotEqualsException orderAndAmountIsNotEqualsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, ORDER_AND_AMOUNT_IS_NOT_EQUALS_MESSAGE));
    }
    @ExceptionHandler(InvalidValueException.class)
    public ResponseEntity<Map<String, String>> invalidValueException(
            InvalidValueException invalidValueException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, INVALID_VALUE_MESSAGE));
    }
    @ExceptionHandler(ClientHasPendingOrderException.class)
    public ResponseEntity<Map<String, String>> clientHasPendingOrderException(
            ClientHasPendingOrderException clientHasPendingOrderException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, CLIENT_HAS_PENDING_ORDER_MESSAGE));
    }
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Map<String, String>> orderNotFoundException(
            OrderNotFoundException orderNotFoundException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, ORDER_NOT_FOUND_MESSAGE));
    }
    @ExceptionHandler(EmployeeDoesNotBelongRestaurantException.class)
    public ResponseEntity<Map<String, String>> employeeDoesNotBelongRestaurantException(
            EmployeeDoesNotBelongRestaurantException employeeDoesNotBelongRestaurantException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, EMPLOYEE_DOES_NOT_BELONG_RESTAURANT_MESSAGE));
    }
    @ExceptionHandler(InvalidStatusException.class)
    public ResponseEntity<Map<String, String>> invalidStatusException(
            InvalidStatusException invalidStatusException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, INVALID_STATUS_MESSAGE));
    }

}
