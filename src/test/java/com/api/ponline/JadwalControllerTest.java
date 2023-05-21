package com.api.ponline;

import java.util.Date;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.api.ponline.dao.Request.AuthorizationRequest;
import com.api.ponline.dao.Request.JadwalRequest;
import com.api.ponline.dao.Request.KomunitasRequest;
import com.api.ponline.dao.Request.LoginRequest;
import com.api.ponline.model.Entity.jadwal.Jadwal;
import com.api.ponline.model.Entity.komunitas.Anggota;
import com.api.ponline.model.Entity.komunitas.Komunitas;
import com.api.ponline.model.Entity.user.AuthProvider;
import com.api.ponline.model.Entity.user.User;
import com.api.ponline.model.Entity.user.UserRole;
import com.api.ponline.model.repository.jadwal.JadwalRepo;
import com.api.ponline.model.repository.kolam.KolamRepo;
import com.api.ponline.model.repository.komunitas.AnggotaRepo;
import com.api.ponline.model.repository.komunitas.KomunitasRepo;
import com.api.ponline.model.repository.user.UserRepository;
import com.api.ponline.services.komunitas.KomunitasServices;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JadwalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KomunitasRepo komunitasRepo;

    @Autowired
    private KolamRepo kolamRepo;

    @Autowired
    private AnggotaRepo anggotaRepo;

    @Autowired
    private JadwalRepo jadwalRepo;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private String token;

    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private LoginRequest loginRequest;

    private User user;
    

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();
        
        token = getSetupToken("user_dummy@test.com", "password123", UserRole.ROLE_OWNER);
    }

    private String getSetupToken(String email, String password, UserRole userRole) {

        user = userRepository.findOneByEmail(email);

        if (user == null) {
            user = new User();
        }
        
        user.setName("Nama Pengguna");
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setProvider(AuthProvider.local);
        user.setRole(userRole);
        user.setEmailVerified(true);
        userRepository.save(user);

        // Set up request data
        loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);
        
        try {
            // Perform request
            String result = mockMvc.perform(MockMvcRequestBuilders.post("/authentication/login")
            .content(objectMapper.writeValueAsString(loginRequest))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").isNotEmpty())
            .andReturn().getResponse().getContentAsString();
            
            JSONObject jsonObject = new JSONObject(result);
            return jsonObject.getString("accessToken");
        } catch (Exception e) {
            return null;
        }
    }

    @Test
    public void ujiTambahJadwal_berhasil() throws Exception {

        if (user != null && !user.getRole().equals(UserRole.ROLE_OWNER)) {
            user.setRole(UserRole.ROLE_OWNER);
            userRepository.save(user);
        }
        
        JadwalRequest jadwalRequest = new JadwalRequest();
        jadwalRequest.setAnggota(anggotaRepo.getAllAnggota().get(0));
        jadwalRequest.setDateToDo(new Date());
        jadwalRequest.setKolam(kolamRepo.getAllKolam().get(0));

        String requestJson = new ObjectMapper().writeValueAsString(jadwalRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/jadwal")
                .header("Authorization", "Bearer " + token)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void ujiTambahJadwal_validasiError() throws Exception {

        if (user != null && !user.getRole().equals(UserRole.ROLE_OWNER)) {
            user.setRole(UserRole.ROLE_OWNER);
            userRepository.save(user);
        }
        
        JadwalRequest jadwalRequest = new JadwalRequest();
        jadwalRequest.setAnggota(null);
        jadwalRequest.setDateToDo(new Date());
        jadwalRequest.setKolam(kolamRepo.getAllKolam().get(0));

        String requestJson = new ObjectMapper().writeValueAsString(jadwalRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/jadwal")
                .header("Authorization", "Bearer " + token)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void ujiReadJadwal_validasiError() throws Exception {

        if (user != null && !user.getRole().equals(UserRole.ROLE_OWNER)) {
            user.setRole(UserRole.ROLE_OWNER);
            userRepository.save(user);
        }        
        
        mockMvc.perform(MockMvcRequestBuilders.get("/jadwal")
                .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void ujiUpdateJadwal_berhasil() throws Exception {

        if (user != null && !user.getRole().equals(UserRole.ROLE_OWNER)) {
            user.setRole(UserRole.ROLE_OWNER);
            userRepository.save(user);
        }

        Jadwal jadwal = new Jadwal();
        jadwal = jadwalRepo.getAllJadwal().get(0);
        
        JadwalRequest jadwalRequest = new JadwalRequest();
        jadwalRequest.setId(jadwal.getId());
        jadwalRequest.setAnggota(jadwal.getAnggota());
        jadwalRequest.setDateToDo(new Date());
        jadwalRequest.setKolam(kolamRepo.getAllKolam().get(0));

        String requestJson = new ObjectMapper().writeValueAsString(jadwalRequest);
        mockMvc.perform(MockMvcRequestBuilders.put("/jadwal")
                .header("Authorization", "Bearer " + token)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void ujiUpdateJadwal_validasiError() throws Exception {

        if (user != null && !user.getRole().equals(UserRole.ROLE_OWNER)) {
            user.setRole(UserRole.ROLE_OWNER);
            userRepository.save(user);
        }

        Jadwal jadwal = new Jadwal();
        jadwal = jadwalRepo.getAllJadwal().get(0);
        
        JadwalRequest jadwalRequest = new JadwalRequest();
        jadwalRequest.setId(jadwal.getId());
        jadwalRequest.setAnggota(jadwal.getAnggota());
        jadwalRequest.setDateToDo(null);
        jadwalRequest.setKolam(kolamRepo.getAllKolam().get(0));

        String requestJson = new ObjectMapper().writeValueAsString(jadwalRequest);
        mockMvc.perform(MockMvcRequestBuilders.put("/jadwal")
                .header("Authorization", "Bearer " + token)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void ujiDeleteJadwal_berhasil() throws Exception {

        if (user != null && !user.getRole().equals(UserRole.ROLE_OWNER)) {
            user.setRole(UserRole.ROLE_OWNER);
            userRepository.save(user);
        }

        Jadwal jadwal = new Jadwal();
        jadwal = jadwalRepo.getAllJadwal().get(0);
        
        mockMvc.perform(MockMvcRequestBuilders.delete("/jadwal/delete/"+jadwal.getId())
                .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    
}
