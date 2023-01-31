package com.api.ponline.controllers.komunitas;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.ponline.dao.Request.AnggotaRequest;
import com.api.ponline.dao.Request.StatusAnggotaRequest;
import com.api.ponline.dao.Response.AbstractResponse;
import com.api.ponline.dao.Response.ApiResponse;
import com.api.ponline.model.Entity.komunitas.Anggota;
import com.api.ponline.model.Entity.komunitas.Komunitas;
import com.api.ponline.model.Entity.komunitas.StatusAnggota;
import com.api.ponline.model.Entity.user.User;
import com.api.ponline.model.repository.user.UserRepository;
import com.api.ponline.services.komunitas.AnggotaServices;
import com.api.ponline.services.komunitas.KomunitasServices;

// Anotasi RESTController untuk menandakan bahwa ini kelas rest controller
@RestController
// Set base url endpoint (baseurl/anggota)
@RequestMapping("/anggota")
public class AnggotaController {

    // Inject anggota service untuk memakai fungsi fungsi yg ada di kelas service
    @Autowired
    private AnggotaServices anggotaServices;

    // Inject komunitas service untuk memakai fungsi fungsi yg ada di kelas service
    @Autowired
    private KomunitasServices komunitasServices;

    // Inject user service untuk memakai fungsi fungsi yg ada di kelas service
    @Autowired
    private UserRepository userRepository;
    
    // enspoin untuk menyimpan data
    // anotasi untuk menandakan metode yang di gunakan adalah POST
    @PostMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<AbstractResponse<Anggota>> create(@Valid @RequestBody AnggotaRequest anggotaRequest, Errors errors ) {
        
        // Siapkan objek kosong untuk di kembalikan
        AbstractResponse<Anggota> responseData = new AbstractResponse<>();

        // periksa jika ada error
        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setSuccess(false);
            responseData.setPayLoad(null);

            // kembalikan AbstractResponse dengan pesan gagal dan kode error 500
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        // jika tidak ada error, maka akan di kembalikan respon 200
        Komunitas komunitas = komunitasServices.findOne(anggotaRequest.getKomunitas().getId());
        User user = userRepository.findOneById(anggotaRequest.getUser().getId());
        Anggota anggota = new Anggota();
        anggota.setKomunitas(komunitas);
        anggota.setUser(user);
        anggota.setStatusAnggota(StatusAnggota.RESMI);
        responseData.setSuccess(true);
        responseData.setPayLoad(anggotaServices.save(anggota));
        return ResponseEntity.ok(responseData);       

    }

    // buat endpoint untuk update, penjelasanya sama kaya simpan data hanya saja respon yang di terima sudah ada id objeknya
    // buat metode PUT
    @PutMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<AbstractResponse<Anggota>> update(@Valid @RequestBody AnggotaRequest anggotaRequest, Errors errors ) {
    
        AbstractResponse<Anggota> responseData = new AbstractResponse<>();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setSuccess(false);
            responseData.setPayLoad(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Komunitas komunitas = komunitasServices.findOne(anggotaRequest.getKomunitas().getId());
        User user = userRepository.findOneById(anggotaRequest.getUser().getId());
        Anggota anggota = new Anggota();
        anggota.setId(anggotaRequest.getId());
        anggota.setKomunitas(komunitas);
        anggota.setUser(user);
        responseData.setSuccess(true);
        responseData.setPayLoad(anggotaServices.save(anggota));
        return ResponseEntity.ok(responseData);       

    }

    // enspoin untuk menyimpan data
    // anotasi untuk menandakan metode yang di gunakan adalah POST
    @PostMapping("/join")
    public ResponseEntity<AbstractResponse<Anggota>> joinAnggota(@Valid @RequestBody AnggotaRequest anggotaRequest, Errors errors ) {
        
        // Siapkan objek kosong untuk di kembalikan
        AbstractResponse<Anggota> responseData = new AbstractResponse<>();

        // periksa jika ada error
        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setSuccess(false);
            responseData.setPayLoad(null);

            // kembalikan AbstractResponse dengan pesan gagal dan kode error 500
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        // jika tidak ada error, maka akan di kembalikan respon 200
        Komunitas komunitas = komunitasServices.findOne(anggotaRequest.getKomunitas().getId());
        User user = userRepository.findOneById(anggotaRequest.getUser().getId());
        Anggota anggota = new Anggota();
        anggota.setKomunitas(komunitas);
        anggota.setUser(user);
        anggota.setStatusAnggota(StatusAnggota.PENDING);
        responseData.setSuccess(true);
        responseData.setPayLoad(anggotaServices.save(anggota));
        return ResponseEntity.ok(responseData);       

    }

    @PostMapping("/update/status")
    public ResponseEntity<ApiResponse> updateStatusAnggota(@Valid @RequestBody StatusAnggotaRequest statusAnggotaRequest, Errors errors){
        // Siapkan objek kosong untuk di kembalikan
        ApiResponse response = new ApiResponse(false, "Data Gagal Di perbarui");
        
        // periksa jika ada error
        if (!errors.hasErrors()) {
            Anggota anggota = anggotaServices.findOne(statusAnggotaRequest.getAnggota().getId());
            if (anggota!=null) {
                anggota.setStatusAnggota(statusAnggotaRequest.getStatusAnggota());
                anggotaServices.save(anggota);
                response.setMessage("Data Berhasil Diperbarui");
                response.setSuccess(true);
                return ResponseEntity.ok(response); 
            }else{
                response.setMessage("Data Anggota tidak ditemukan");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
        }else{
            // kembalikan ApiResponse dengan pesan gagal dan kode error 500
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    

    // Endpoitn delete
    // metode DELETE
    // id di baca dari url
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ApiResponse deleteById(@PathVariable("id") Long id) {

        // Buat respon gagal
        ApiResponse response = new ApiResponse(false, "Data Gagal Di hapus");
        if (anggotaServices.deleteById(id)) {
            // update respon menjadi berhasil
            response.setSuccess(true);
            response.setMessage("Data Berhasil Di hapus");
        }
        // kembalikan respon
        return response;
    }

    // enpoind untuk membaca semua data
    @GetMapping
    public Iterable<Anggota> findAll() {
        return anggotaServices.findAll();
    }

    // endpoin untuk mencari data berdasarkan user
    @GetMapping("/find/user/{user}")
    public List<Anggota> findByUser(@PathVariable("user") User user) {
        return anggotaServices.findByUser(user);
    }

    // endpoin untuk mencari data berdasarkan komunitas
    @GetMapping("/find/komunitas/{komunitas}")
    public List<Anggota> findByKomunitas(@PathVariable("komunitas") Komunitas komunitas) {
        return anggotaServices.findByKomunitas(komunitas);
    }
    
}
