package com.api.ponline.model.repository.Anggota;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.api.ponline.model.Entity.Anggota.Anggota;
import com.api.ponline.model.Entity.komunitas.Komunitas;
import com.api.ponline.model.Entity.user.User;

// Turunkan fungsi CrudRepository
public interface AnggotaRepo extends CrudRepository<Anggota, Long>{

    // kueri cari anggota berdasarkan user
    List<Anggota> findByUser(User user);

    // kueri cari anggota berdasarkan komunitas
    List<Anggota> findByKomunitas(Komunitas komunitas);

    default List<Anggota> getAllAnggota(){
        List<Anggota> result = new ArrayList<Anggota>();
        for (Anggota anggota : findAll()) {
            result.add(anggota);
        }
        return result;
    }
    
}
