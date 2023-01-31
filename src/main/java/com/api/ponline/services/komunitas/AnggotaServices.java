package com.api.ponline.services.komunitas;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.ponline.model.Entity.komunitas.Anggota;
import com.api.ponline.model.Entity.komunitas.Komunitas;
import com.api.ponline.model.Entity.user.User;
import com.api.ponline.model.repository.komunitas.AnggotaRepo;

// Anotasi Bahwa ini adalah kelas service
@Service

// Anotasi Transactional untuk menjalankan transaksi JPA, 
// sehingga tidak perlu mengaktifkan atau mematikan transaksinya
@Transactional
public class AnggotaServices{
    
    // inject repositori
    @Autowired
    private AnggotaRepo anggotaRepo;

    // fungsi simpan
    public Anggota save(Anggota anggota) {
        return anggotaRepo.save(anggota);
    }

    // fungsi cari satu data
    public Anggota findOne(Long id) {
        return anggotaRepo.findById(id).get();
    }

    // fungsi baca semua data
    public Iterable<Anggota> findAll() {
        return anggotaRepo.findAll();
    }

    // fungsi cari data berdasarkan pengguna
    public List<Anggota> findByUser(User user){
        return anggotaRepo.findByUser(user);
    }

    // cari data berdasarkan komunitas
    public List<Anggota> findByKomunitas(Komunitas komunitas){
        return anggotaRepo.findByKomunitas(komunitas);
    }

    // fungsi hapus data
    public Boolean deleteById(Long id) {
        if (isExist(id)) {
            anggotaRepo.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    // fungsi cek apakah data ada di database
    public Boolean isExist(Long id) {
        Anggota anggota = findOne(id);
        if (anggota!=null) {
            return true;
        }else{
            return false;
        }
    }
}