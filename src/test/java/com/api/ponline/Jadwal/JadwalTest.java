package com.api.ponline.Jadwal;

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

import com.api.ponline.dao.Request.JadwalRequest;
import com.api.ponline.dao.Request.LoginRequest;
import com.api.ponline.model.Entity.jadwal.Jadwal;
import com.api.ponline.model.Entity.user.AuthProvider;
import com.api.ponline.model.Entity.user.User;
import com.api.ponline.model.Entity.user.UserRole;
import com.api.ponline.model.repository.jadwal.JadwalRepo;
import com.api.ponline.model.repository.kolam.KolamRepo;
import com.api.ponline.model.repository.komunitas.AnggotaRepo;
import com.api.ponline.model.repository.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JadwalTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KolamRepo kolamRepo;

    @Autowired
    private AnggotaRepo anggotaRepo;
    
    @Autowired
    private JadwalRepo jadwalRepo;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void ujiCreateJadwal_userNull() throws Exception {

        String bearerToken = getBearerTokenUser("email_user@test.com", "passwordBaru123");

        JadwalRequest jadwalRequest = new JadwalRequest();
        jadwalRequest.setAnggota(null);
        jadwalRequest.setDateToDo(new Date());
        jadwalRequest.setKolam(kolamRepo.getAllKolam().get(0));

        String requestJson = new ObjectMapper().writeValueAsString(jadwalRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/jadwal")
                .header("Authorization", "Bearer " + bearerToken)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void ujiCreateJadwal_valid() throws Exception {

        String bearerToken = getBearerTokenUser("email_user@test.com", "passwordBaru123");

        JadwalRequest jadwalRequest = new JadwalRequest();
        jadwalRequest.setAnggota(anggotaRepo.getAllAnggota().get(0));
        jadwalRequest.setDateToDo(new Date());
        jadwalRequest.setKolam(kolamRepo.getAllKolam().get(0));

        String requestJson = new ObjectMapper().writeValueAsString(jadwalRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/jadwal")
                .header("Authorization", "Bearer " + bearerToken)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void ujiReadAllJadwal_valid() throws Exception {

        String bearerToken =  getBearerTokenUser("email_user@test.com", "passwordBaru123");
        
        mockMvc.perform(MockMvcRequestBuilders.get("/jadwal")
                .header("Authorization", "Bearer " + bearerToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void ujiUpdateJadwal_valid() throws Exception {

        String bearerToken =  getBearerTokenUser("email_user@test.com", "passwordBaru123");
        
        Jadwal jadwal = new Jadwal();
        jadwal = jadwalRepo.getAllJadwal().get(jadwalRepo.getAllJadwal().size()-1);
        
        JadwalRequest jadwalRequest = new JadwalRequest();
        jadwalRequest.setId(jadwal.getId());
        jadwalRequest.setAnggota(jadwal.getAnggota());
        jadwalRequest.setDateToDo(new Date());
        jadwalRequest.setKolam(kolamRepo.getAllKolam().get(0));

        String requestJson = new ObjectMapper().writeValueAsString(jadwalRequest);
        mockMvc.perform(MockMvcRequestBuilders.put("/jadwal")
                .header("Authorization", "Bearer " + bearerToken)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void ujiDeleteJadwal_valid() throws Exception {
        String bearerToken =  getBearerTokenUser("email_user@test.com", "passwordBaru123");

        Jadwal jadwal = new Jadwal();
        jadwal = jadwalRepo.getAllJadwal().get(jadwalRepo.getAllJadwal().size()-1);
        
        mockMvc.perform(MockMvcRequestBuilders.delete("/jadwal/delete/"+jadwal.getId())
                .header("Authorization", "Bearer " + bearerToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }    
    
    private String getBearerTokenUser(String email, String password) {
        User user = userRepository.findOneByEmail(email);

        if (user == null) {
            user = new User();
        }
        
        user.setName("Nama Pengguna");
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setProvider(AuthProvider.local);
        user.setEmailVerified(true);
        user.setRole(UserRole.ROLE_OWNER);
        userRepository.save(user);

        // Set up request data
        LoginRequest loginRequest = new LoginRequest();
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
    
}
