package com.api.ponline.model.repository.komunitas;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.api.ponline.model.Entity.komunitas.Anggota;
import com.api.ponline.model.Entity.komunitas.Komunitas;
import com.api.ponline.model.Entity.user.User;

// Turunkan fungsi CrudRepository
public interface AnggotaRepo extends CrudRepository<Anggota, Long>{

    // kueri cari anggota berdasarkan user
    List<Anggota> findByUser(User user);

    // kueri cari anggota berdasarkan komunitas
    List<Anggota> findByKomunitas(Komunitas komunitas);
    
}
