package com.api.ponline.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.ponline.dao.Request.AuthorizationRequest;
import com.api.ponline.dao.Response.ApiResponse;
import com.api.ponline.dao.Response.AuthorizationResponse;
import com.api.ponline.dao.exception.ResourceNotFoundException;
import com.api.ponline.model.Entity.user.User;
import com.api.ponline.model.repository.user.UserRepository;
import com.api.ponline.security.CurrentUser;
import com.api.ponline.security.UserPrincipal;

@RestController
@RequestMapping("/authorization")
public class AuthtorizationController {
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/check")
    public AuthorizationResponse checkUserRole(@CurrentUser UserPrincipal userPrincipal) {
        AuthorizationResponse response = new AuthorizationResponse();

        User user = getCurrentUser(userPrincipal);
        if (user.getRole()!=null) {
            response.setSuccess(true);
            response.setMessage("Otorisasi di temukan");
            response.setUserRole(user.getRole());
        }else{
            response.setUserRole(null);
            response.setMessage("Anda tidak memiliki otorisasi apapun !!");
            response.setSuccess(false);
        }

        return response;
    }

    @PostMapping("/update")
    public ApiResponse updateRole(@RequestBody AuthorizationRequest authorizationRequest, @CurrentUser UserPrincipal userPrincipal){
        ApiResponse response = new ApiResponse(false, "Gagal memperbarui otorisasi");

        User user = getCurrentUser(userPrincipal);
        if (user != null) {
            user.setRole(authorizationRequest.getUserRole());
            userRepository.save(user);
            if (userRepository.findOneById(user.getId()).getRole().equals(authorizationRequest.getUserRole())) {
                response.setSuccess(true);
                response.setMessage("Berhasil Memperbarui Otorisasi");
            }
        }

        return response;
    }

    public User getCurrentUser(UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }
}
