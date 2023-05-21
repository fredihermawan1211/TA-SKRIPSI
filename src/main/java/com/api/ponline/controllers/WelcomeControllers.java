package com.api.ponline.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.api.ponline.dao.Response.ApiResponse;
import com.api.ponline.model.Entity.jadwal.Jadwal;
import com.api.ponline.model.Entity.kolam.Kolam;
import com.api.ponline.model.Entity.komunitas.Anggota;
import com.api.ponline.model.Entity.komunitas.Komunitas;
import com.api.ponline.model.Entity.user.User;
import com.api.ponline.model.repository.user.UserRepository;
import com.api.ponline.services.jadwal.JadwalServices;
import com.api.ponline.services.kolam.KolamServices;
import com.api.ponline.services.komunitas.AnggotaServices;
import com.api.ponline.services.komunitas.KomunitasServices;

@RestController
@RequestMapping("/")
public class WelcomeControllers {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KomunitasServices komunitasServices;

    @Autowired
    private KolamServices kolamServices;

    @Autowired
    private AnggotaServices anggotaServices;

    @Autowired
    private JadwalServices jadwalServices;
    
    @GetMapping
    public RedirectView welcome() {
        return new RedirectView("documentation");
    }

    @GetMapping("uji/koneksi")
    public ApiResponse testKoneksi() {
        Boolean status = true;
        ApiResponse response = new ApiResponse(status, null);


        // Uji koneksi user
        try {
            Iterable<User> user = userRepository.findAll();
            status = status && true;
            
        } catch (Exception e) {
            status = status && false;
        }

        // Uji koneksi komunitas
        try {
            Iterable<Komunitas> komunitas = komunitasServices.findAll();
            status = status && true;
            
        } catch (Exception e) {
            status = status && false;
        }
        
        // Uji koneksi kolam
        try {
            Iterable<Kolam> kolam = kolamServices.findAll();
            status = status && true;
            
        } catch (Exception e) {
            status = status && false;
        }

        // Uji koneksi Angggota
        try {
            Iterable<Anggota> angggota = anggotaServices.findAll();
            status = status && true;
            
        } catch (Exception e) {
            status = status && false;
        }

        // Uji koneksi jadwal
        try {
            Iterable<Jadwal> jadwal = jadwalServices.findAll();
            status = status && true;
            
        } catch (Exception e) {
            status = status && false;
        }

        response.setSuccess(status);
        return response;
    }
    
}
