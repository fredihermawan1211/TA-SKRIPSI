package com.api.ponline.Anggota;

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

import com.api.ponline.dao.Request.AnggotaRequest;
import com.api.ponline.dao.Request.LoginRequest;
import com.api.ponline.model.Entity.Anggota.Anggota;
import com.api.ponline.model.Entity.Anggota.StatusAnggota;
import com.api.ponline.model.Entity.user.AuthProvider;
import com.api.ponline.model.Entity.user.User;
import com.api.ponline.model.Entity.user.UserRole;
import com.api.ponline.model.repository.Anggota.AnggotaRepo;
import com.api.ponline.model.repository.komunitas.KomunitasRepo;
import com.api.ponline.model.repository.user.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AnggotaTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private AnggotaRepo anggotaRepo;

    @Autowired
    private KomunitasRepo komunitasRepo;

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
    public void ujiCreateAnggota_userNull() throws Exception {

        String bearerToken = getBearerTokenUser("email_user@test.com", "passwordBaru123");
        
        AnggotaRequest anggotaRequest = new AnggotaRequest();
        anggotaRequest.setKomunitas(komunitasRepo.getAllKomunitas().get(0));
        anggotaRequest.setUser(null);
        anggotaRequest.setStatusAnggota(StatusAnggota.RESMI);

        String requestJson = new ObjectMapper().writeValueAsString(anggotaRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/anggota")
                .header("Authorization", "Bearer " + bearerToken)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void ujiCreateAnggota_valid() throws Exception {

        String bearerToken = getBearerTokenUser("email_user@test.com", "passwordBaru123");

        User user = userRepository.getAllUsers().get(0);
        
        AnggotaRequest anggotaRequest = new AnggotaRequest();
        anggotaRequest.setKomunitas(komunitasRepo.getAllKomunitas().get(0));
        anggotaRequest.setUser(user);
        anggotaRequest.setStatusAnggota(StatusAnggota.RESMI);

        String requestJson = new ObjectMapper().writeValueAsString(anggotaRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/anggota")
                .header("Authorization", "Bearer " + bearerToken)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void ujiReadAllAnggota_valid() throws Exception {

        String bearerToken =  getBearerTokenUser("email_user@test.com", "passwordBaru123");
        
        mockMvc.perform(MockMvcRequestBuilders.get("/anggota")
                .header("Authorization", "Bearer " + bearerToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void ujiReadAnggotaByUser_valid() throws Exception {

        String bearerToken =  getBearerTokenUser("email_user@test.com", "passwordBaru123");
        Long idUser = userRepository.getAllUsers().get(0).getId();
        
        mockMvc.perform(MockMvcRequestBuilders.get("/anggota/find/user/"+idUser)
                .header("Authorization", "Bearer " + bearerToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void ujiReadAnggotaByKomunitas_valid() throws Exception {

        String bearerToken =  getBearerTokenUser("email_user@test.com", "passwordBaru123");
        Long idKomunitas = komunitasRepo.getAllKomunitas().get(0).getId();
        
        mockMvc.perform(MockMvcRequestBuilders.get("/anggota/find/komunitas/"+idKomunitas)
                .header("Authorization", "Bearer " + bearerToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void ujiUpdateAnggota_valid() throws Exception {
        String bearerToken =  getBearerTokenUser("email_user@test.com", "passwordBaru123");        
        Anggota anggota = anggotaRepo.getAllAnggota().get(anggotaRepo.getAllAnggota().size()-1);

        AnggotaRequest anggotaRequest = new AnggotaRequest();
        anggotaRequest.setId(anggota.getId());
        anggotaRequest.setUser(anggota.getUser());
        anggotaRequest.setKomunitas(anggota.getKomunitas());
        anggotaRequest.setStatusAnggota(anggota.getStatusAnggota().equals(StatusAnggota.RESMI) ? StatusAnggota.PENDING : StatusAnggota.RESMI);

        String requestJson = new ObjectMapper().writeValueAsString(anggotaRequest);
        mockMvc.perform(MockMvcRequestBuilders.put("/anggota")
                .header("Authorization", "Bearer " + bearerToken)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void ujiDeleteAnggota_valid() throws Exception {
        String bearerToken =  getBearerTokenUser("email_user@test.com", "passwordBaru123");

        Anggota anggota = anggotaRepo.getAllAnggota().get(anggotaRepo.getAllAnggota().size()-1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/anggota/delete/"+anggota.getId())
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
