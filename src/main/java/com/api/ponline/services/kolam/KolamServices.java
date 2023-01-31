package com.api.ponline.services.kolam;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.ponline.model.Entity.kolam.Kolam;
import com.api.ponline.model.repository.kolam.KolamRepo;

// Anotasi Bahwa ini adalah kelas service
@Service
// Anotasi Transactional untuk menjalankan transaksi JPA, 
// sehingga tidak perlu mengaktifkan atau mematikan transaksinya
@Transactional
public class KolamServices{
    
    // inject repositori
    @Autowired
    private KolamRepo kolamRepo;

    // fungsi simpan
    public Kolam save(Kolam kolam) {
        return kolamRepo.save(kolam);
    }

    // fungsi cari satu data
    public Kolam findOne(Long id) {
        return kolamRepo.findById(id).get();
    }

    // fungsi baca semua data
    public Iterable<Kolam> findAll() {
        return kolamRepo.findAll();
    }

    // fungsi hapus data
    public Boolean deleteById(Long id) {
        if (isExist(id)) {
            kolamRepo.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    // fungsi cek apakah data ada di database
    public Boolean isExist(Long id) {
        Kolam kolam = findOne(id);
        if (kolam!=null) {
            return true;
        }else{
            return false;
        }
    }
}
