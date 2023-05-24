package com.api.ponline.model.repository.komunitas;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.api.ponline.model.Entity.komunitas.Komunitas;

public interface KomunitasRepo extends CrudRepository<Komunitas, Long>{

    public default List<Komunitas> getAllKomunitas() {
        List<Komunitas> result = new ArrayList<Komunitas>();
        for (Komunitas komunitas : this.findAll()) {
            result.add(komunitas);
        }
        
        return result;
    }
    
}
