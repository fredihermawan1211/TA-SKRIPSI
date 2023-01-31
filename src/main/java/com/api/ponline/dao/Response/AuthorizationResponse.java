package com.api.ponline.dao.Response;

import com.api.ponline.model.Entity.user.UserRole;

public class AuthorizationResponse {
    
    private Boolean success;
    
    private UserRole userRole;
    
    private String message;

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    
}
