package com.api.ponline.dao.Request;

import javax.validation.constraints.NotNull;

import com.api.ponline.model.Entity.komunitas.Anggota;
import com.api.ponline.model.Entity.komunitas.StatusAnggota;

public class StatusAnggotaRequest {

    @NotNull(message = "Anggota tidak boleh kosong")
    private Anggota anggota;
    

    @NotNull(message = "Status tidak boleh kosong")
    private StatusAnggota statusAnggota;

    
    public StatusAnggota getStatusAnggota() {
        return statusAnggota;
    }


    public void setStatusAnggota(StatusAnggota statusAnggota) {
        this.statusAnggota = statusAnggota;
    }


    public Anggota getAnggota() {
        return anggota;
    }


    public void setAnggota(Anggota anggota) {
        this.anggota = anggota;
    }

    
    
}
