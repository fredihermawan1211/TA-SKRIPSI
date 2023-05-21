package com.api.ponline.model.repository.jadwal;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

import com.api.ponline.model.Entity.jadwal.Jadwal;

public interface JadwalRepo extends CrudRepository<Jadwal, Long>{
    default List<Jadwal> getAllJadwal(){
        List<Jadwal> result = new ArrayList<Jadwal>();
        for (Jadwal anggota : findAll()) {
            result.add(anggota);
        }
        return result;
    }
}
