package com.api.ponline.controllers.komunitas;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.ponline.dao.Request.KomunitasRequest;
import com.api.ponline.dao.Response.AbstractResponse;
import com.api.ponline.dao.Response.ApiResponse;
import com.api.ponline.model.Entity.komunitas.Komunitas;
import com.api.ponline.services.komunitas.KomunitasServices;

// Anotasi RESTController untuk menandakan bahwa ini kelas rest controller
@RestController
// Set base url endpoint (baseurl/komunitas)
@RequestMapping("/komunitas")
public class KomunitasController {
    
    // Inject komunitas service untuk memakai fungsi fungsi yg ada di kelas service
    @Autowired
    private KomunitasServices komunitasServices;
    
    // inject model mapper untuk memudahkan penyusunan data dari json yang di kirimkan frontend ke objek
    @Autowired
    private ModelMapper modelMapper;

    // enspoin untuk menyimpan data
    // anotasi untuk menandakan metode yang di gunakan adalah POST
    @PostMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<AbstractResponse<Komunitas>> create(@Valid @RequestBody KomunitasRequest komunitasRequest, Errors errors ) {
        
        // Siapkan objek kosong untuk di kembalikan
        AbstractResponse<Komunitas> responseData = new AbstractResponse<>();

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
        Komunitas komunitas = modelMapper.map(komunitasRequest, Komunitas.class);
        responseData.setSuccess(true);
        responseData.setPayLoad(komunitasServices.save(komunitas));
        return ResponseEntity.ok(responseData);       

    }

    // buat endpoint untuk update, penjelasanya sama kaya simpan data hanya saja respon yang di terima sudah ada id objeknya
    // buat metode PUT
    @PutMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<AbstractResponse<Komunitas>> update(@Valid @RequestBody KomunitasRequest komunitasRequest, Errors errors ) {
    
        AbstractResponse<Komunitas> responseData = new AbstractResponse<>();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setSuccess(false);
            responseData.setPayLoad(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Komunitas komunitas = modelMapper.map(komunitasRequest, Komunitas.class);
        responseData.setSuccess(true);
        responseData.setPayLoad(komunitasServices.save(komunitas));
        return ResponseEntity.ok(responseData);       

    }
    
    // Endpoitn delete
    // metode DELETE
    // id di baca dari url
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ApiResponse deleteById(@PathVariable("id") Long id) {

        // Buat respon gagal
        ApiResponse response = new ApiResponse(false, "Data Gagal Di hapus");

        // Periksa jika data berhasil di hapus
        if (komunitasServices.deleteById(id)) {
            // update respon menjadi berhasil
            response.setSuccess(true);
            response.setMessage("Data Berhasil Di hapus");
        }
        // kembalikan respon
        return response;
    }
    
    // enpoind untuk membaca semua data
    @GetMapping
    public Iterable<Komunitas> findAll(){
        return komunitasServices.findAll();
    }

    // endpoin untuk mencari data
    // contoh url (base_url/find/id/{idnya})
    @GetMapping("/find")
    public Komunitas findOne(@RequestParam Long id) {
        return komunitasServices.findOne(id);
    }
}
