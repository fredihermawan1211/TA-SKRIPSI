package com.api.ponline.dao.Request;

import javax.validation.constraints.NotNull;

import com.api.ponline.model.Entity.Anggota.StatusAnggota;
import com.api.ponline.model.Entity.komunitas.Komunitas;
import com.api.ponline.model.Entity.user.User;

public class AnggotaRequest {

    private Long id;

    @NotNull(message = "Komunitas tidak boleh kosong")
    private Komunitas komunitas;

    @NotNull(message = "User tidak boleh kosong")
    private User user;

    @NotNull(message = "Status tidak boleh kosong")
    private StatusAnggota statusAnggota;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Komunitas getKomunitas() {
        return komunitas;
    }

    public void setKomunitas(Komunitas komunitas) {
        this.komunitas = komunitas;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public StatusAnggota getStatusAnggota() {
        return statusAnggota;
    }


    public void setStatusAnggota(StatusAnggota statusAnggota) {
        this.statusAnggota = statusAnggota;
    }


    
    
}
