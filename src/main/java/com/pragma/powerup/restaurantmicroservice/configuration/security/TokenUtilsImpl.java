package com.pragma.powerup.restaurantmicroservice.configuration.security;

import com.pragma.powerup.restaurantmicroservice.configuration.Constants;

public class TokenUtilsImpl {
    public boolean validateRolePaths(String role, String path) {
        String[] adminPath = {"/restaurant"};
        String[] ownerPath = {"/dish", "/restaurant/", "/employee","/getOrdersDuration","/getRankingEmployees"};
        String[] employeePath = {"/getOrders", "/getOrderDish", "/assignOrder/", "/changeOrderStatus", "/deliverOrder"};
        String[] clientPath = {"/restaurant/", "/getDishes", "/order", "/cancelOrder"};
        boolean result = false;

        switch (role) {
            case (Constants.OWNER_ROLE_NAME) -> result = validatePath(path, ownerPath);
            case (Constants.ADMIN_ROLE_NAME) -> result = validatePath(path, adminPath);
            case (Constants.CLIENT_ROLE_NAME) -> result = validatePath(path, clientPath);
            case (Constants.EMPLOYEE_ROLE_NAME) -> result = validatePath(path, employeePath);
            default -> {}
        }
        return result;
    }

    private boolean validatePath(String path, String[] userPath) {
        for (String validPath : userPath) {
            if (path.contains(validPath)) {
                return true;
            }
        }
        return false;
    }

}
