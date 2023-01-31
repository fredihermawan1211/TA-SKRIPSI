package com.api.ponline.controllers.jadwal;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
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

import com.api.ponline.dao.Request.JadwalRequest;
import com.api.ponline.dao.Response.AbstractResponse;
import com.api.ponline.dao.Response.ApiResponse;
import com.api.ponline.model.Entity.jadwal.Jadwal;
import com.api.ponline.services.jadwal.JadwalServices;

// Anotasi RESTController untuk menandakan bahwa ini kelas rest controller
@RestController
// Set base url endpoint (baseurl/jadwal)
@RequestMapping("/jadwal")
public class JadwalController {

    // Inject komunitas service untuk memakai fungsi fungsi yg ada di kelas service
    @Autowired
    private JadwalServices jadwalServices;
    
    // inject model mapper untuk memudahkan penyusunan data dari json yang di kirimkan frontend ke objek
    @Autowired
    private ModelMapper modelMapper;

    // enspoin untuk menyimpan data
    // anotasi untuk menandakan metode yang di gunakan adalah POST
    @PostMapping
    @PreAuthorize("hasRole('OWNER')")
    // @PreAuthorize("hasRole('OWNER') || hasRole('PQOWNEDRETVY')")
    public ResponseEntity<AbstractResponse<Jadwal>> create(@Valid @RequestBody JadwalRequest jadwalRequest, Errors errors ) {
    
        // Siapkan objek kosong untuk di kembalikan
        AbstractResponse<Jadwal> responseData = new AbstractResponse<>();

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
        Jadwal jadwal = modelMapper.map(jadwalRequest, Jadwal.class);
        responseData.setSuccess(true);
        responseData.setPayLoad(jadwalServices.save(jadwal));
        return ResponseEntity.ok(responseData);       

    }

    // buat endpoint untuk update, penjelasanya sama kaya simpan data hanya saja respon yang di terima sudah ada id objeknya
    // buat metode PUT
    @PutMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<AbstractResponse<Jadwal>> update(@Valid @RequestBody JadwalRequest jadwalRequest, Errors errors ) {
    
        AbstractResponse<Jadwal> responseData = new AbstractResponse<>();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setSuccess(false);
            responseData.setPayLoad(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Jadwal jadwal = modelMapper.map(jadwalRequest, Jadwal.class);
        responseData.setSuccess(true);
        responseData.setPayLoad(jadwalServices.save(jadwal));
        return ResponseEntity.ok(responseData);       

    }
    
    
    // enpoind untuk membaca semua data
    @GetMapping
    public Iterable<Jadwal> findAll() {
        return jadwalServices.findAll();
    }
    
    // Endpoitn delete
    // metode DELETE
    // id di baca dari url
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ApiResponse deleteById(@PathVariable("id") Long id) {
        ApiResponse response = new ApiResponse(false, "Data Gagal Di hapus");
        if (jadwalServices.deleteById(id)) {
            response.setSuccess(true);
            response.setMessage("Data Berhasil Di hapus");
        }
        return response;
    }
}
