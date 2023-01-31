package com.api.ponline.model.repository.jadwal;

import org.springframework.data.repository.CrudRepository;

import com.api.ponline.model.Entity.jadwal.Jadwal;

public interface JadwalRepo extends CrudRepository<Jadwal, Long>{
    
}
