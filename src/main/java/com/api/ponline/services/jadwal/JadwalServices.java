package com.api.ponline.services.jadwal;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.ponline.model.Entity.jadwal.Jadwal;
import com.api.ponline.model.repository.jadwal.JadwalRepo;

// Anotasi Bahwa ini adalah kelas service
@Service
// Anotasi Transactional untuk menjalankan transaksi JPA, 
// sehingga tidak perlu mengaktifkan atau mematikan transaksinya
@Transactional
public class JadwalServices{
    
    // inject repositori
    @Autowired
    private JadwalRepo jadwalRepo;

    // fungsi simpan
    public Jadwal save(Jadwal jadwal) {
        return jadwalRepo.save(jadwal);
    }

    // fungsi cari satu data
    public Jadwal findOne(Long id) {
        return jadwalRepo.findById(id).get();
    }

    // fungsi baca semua data
    public Iterable<Jadwal> findAll() {
        return jadwalRepo.findAll();
    }

    // fungsi hapus data
    public Boolean deleteById(Long id) {
        if (isExist(id)) {
            jadwalRepo.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    // fungsi cek apakah data ada di database
    public Boolean isExist(Long id) {
        Jadwal jadwal = findOne(id);
        if (jadwal!=null) {
            return true;
        }else{
            return false;
        }
    }
}
