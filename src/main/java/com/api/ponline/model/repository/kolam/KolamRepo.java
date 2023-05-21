package com.api.ponline.model.repository.kolam;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

import com.api.ponline.model.Entity.kolam.Kolam;

public interface KolamRepo extends CrudRepository<Kolam, Long>{
    
    default List<Kolam> getAllKolam(){
        List<Kolam> result = new ArrayList<Kolam>();
        
        for (Kolam anggota : findAll()) {
            result.add(anggota);
        }
        return result;
    }
    
}
