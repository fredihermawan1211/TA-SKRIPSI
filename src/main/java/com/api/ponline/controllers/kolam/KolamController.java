package com.api.ponline.controllers.kolam;

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

import com.api.ponline.dao.Request.KolamRequest;
import com.api.ponline.dao.Response.AbstractResponse;
import com.api.ponline.dao.Response.ApiResponse;
import com.api.ponline.model.Entity.kolam.Kolam;
import com.api.ponline.services.kolam.KolamServices;

// Anotasi RESTController untuk menandakan bahwa ini kelas rest controller
@RestController
// Set base url endpoint (baseurl/anggota)
@RequestMapping("/kolam")
public class KolamController {

    // Inject anggota service untuk memakai fungsi fungsi yg ada di kelas service
    @Autowired
    private KolamServices kolamServices;
    
    // inject model mapper untuk memudahkan penyusunan data dari json yang di kirimkan frontend ke objek
    @Autowired
    private ModelMapper modelMapper;

    // enspoin untuk menyimpan data
    // anotasi untuk menandakan metode yang di gunakan adalah POST
    @PostMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<AbstractResponse<Kolam>> create(@Valid @RequestBody KolamRequest kolamRequest, Errors errors ) {
    
        // Siapkan objek kosong untuk di kembalikan
        AbstractResponse<Kolam> responseData = new AbstractResponse<>();

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
        Kolam kolam = modelMapper.map(kolamRequest, Kolam.class);
        responseData.setSuccess(true);
        responseData.setPayLoad(kolamServices.save(kolam));
        return ResponseEntity.ok(responseData);       

    }

    // buat endpoint untuk update, penjelasanya sama kaya simpan data hanya saja respon yang di terima sudah ada id objeknya
    // buat metode PUT
    @PutMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<AbstractResponse<Kolam>> update(@Valid @RequestBody KolamRequest kolamRequest, Errors errors ) {
    
        AbstractResponse<Kolam> responseData = new AbstractResponse<>();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setSuccess(false);
            responseData.setPayLoad(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Kolam kolam = modelMapper.map(kolamRequest, Kolam.class);
        responseData.setSuccess(true);
        responseData.setPayLoad(kolamServices.save(kolam));
        return ResponseEntity.ok(responseData);       

    }

    // enpoind untuk membaca semua data
    @GetMapping
    public Iterable<Kolam> findAll() {
        return kolamServices.findAll();
    }
    
    // Endpoitn delete
    // metode DELETE
    // id di baca dari url
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ApiResponse deleteById(@PathVariable("id") Long id) {
        ApiResponse response = new ApiResponse(false, "Data Gagal Di hapus");
        if (kolamServices.deleteById(id)) {
            response.setSuccess(true);
            response.setMessage("Data Berhasil Di hapus");
        }
        return response;
    }
}
