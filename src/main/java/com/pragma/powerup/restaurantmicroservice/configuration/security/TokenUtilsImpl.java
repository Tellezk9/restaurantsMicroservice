package com.pragma.powerup.restaurantmicroservice.configuration.security;

public class TokenUtilsImpl {
    public boolean validateRolePaths(String role, String path) {
        String[] adminPath = {"/restaurant"};
        String[] ownerPath = {"/dish","/restaurant/","/employee"};
        String[] clientPath = {"/restaurant/","/getDishes","/order"};
        boolean result = false;

        switch (role) {
            case ("ROLE_OWNER"):
                result = validatePath(path, ownerPath);
                break;
            case ("ROLE_ADMIN"):
                result = validatePath(path, adminPath);
                break;
            case ("ROLE_CLIENT"):
                result = validatePath(path, clientPath);
                break;
            default:
                break;
        }
        return result;
    }

    private boolean validatePath(String path, String[] userPath) {
        for(String validPath: userPath){
            if (path.contains(validPath)){
                return true;
            }
        }
        return false;
    }

}
