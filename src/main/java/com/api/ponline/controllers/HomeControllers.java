package com.api.ponline.controllers;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.api.ponline.controllers.Anggota.AnggotaController;
import com.api.ponline.controllers.jadwal.JadwalController;
import com.api.ponline.controllers.kolam.KolamController;
import com.api.ponline.controllers.komunitas.KomunitasController;
import com.api.ponline.controllers.user.AuthenticationController;
import com.api.ponline.controllers.user.AuthtorizationController;
import com.api.ponline.controllers.user.UserController;
import com.api.ponline.dao.Request.LoginRequest;
import com.api.ponline.dao.Response.ApiResponse;
import com.api.ponline.model.Entity.user.UserRole;
import com.api.ponline.security.UserPrincipal;

@RestController
@RequestMapping("/")
public class HomeControllers {

    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    private AuthtorizationController authtorizationController;

    @Autowired
    private UserController userController;

    @Autowired
    private KomunitasController komunitasController;

    @Autowired
    private AnggotaController anggotaController;

    @Autowired
    private KolamController kolamController;

    @Autowired
    private JadwalController jadwalController;
    
    @GetMapping()
    public RedirectView home() {
        return new RedirectView("/documentation/index.html");
    }

    @GetMapping("uji/koneksi")
    public ApiResponse testKoneksi() {
        Boolean status = true;
        ApiResponse response = new ApiResponse(status, null);

        // Uji koneksi authentication
        try {
            authenticationController.authenticateUser(new LoginRequest("email@email.com", "pasWord123"));
            status = status && true;
            
        } catch (Exception e) {
            status = status && false;
        }

        // Uji koneksi authentication
        try {
            List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(UserRole.ROLE_OWNER.name()));
            authtorizationController.checkUserRole(new UserPrincipal(1L, "email@email.com", "passWord123", authorities));
            status = status && true;
            
        } catch (Exception e) {
            status = status && false;
        }


        // Uji koneksi user
        try {
            userController.findAll();
            status = status && true;
            
        } catch (Exception e) {
            status = status && false;
        }

        // Uji koneksi komunitas
        try {
            komunitasController.findAll();
            status = status && true;
            
        } catch (Exception e) {
            status = status && false;
        }
        
        // Uji koneksi kolam
        try {
            kolamController.findAll();
            status = status && true;
            
        } catch (Exception e) {
            status = status && false;
        }

        // Uji koneksi Angggota
        try {
            anggotaController.findAll();
            status = status && true;
            
        } catch (Exception e) {
            status = status && false;
        }

        // Uji koneksi jadwal
        try {
            jadwalController.findAll();
            status = status && true;
            
        } catch (Exception e) {
            status = status && false;
        }

        response.setSuccess(status);
        response.setMessage("Terhubung");
        return response;
    }
    
}
