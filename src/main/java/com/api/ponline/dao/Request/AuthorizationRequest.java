package com.api.ponline.dao.Request;

import com.api.ponline.model.Entity.user.UserRole;

public class AuthorizationRequest {
    
    private UserRole userRole;

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    

}
