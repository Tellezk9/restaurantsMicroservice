package com.pragma.powerup.usermicroservice.configuration.security;

public class TokenUtilsImpl {
    public boolean validateRolePaths(String role, String path) {
        String[] adminPath = {"/restaurant"};
        String[] ownerPath = {"/dish"};
        String[] employeePath = {""};
        String[] clientPath = {""};
        boolean result = false;

        switch (role) {
            case ("ROLE_OWNER"):
                result = validatePath(path, ownerPath);
                break;
            case ("ROLE_ADMIN"):
                result = validatePath(path, adminPath);
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
