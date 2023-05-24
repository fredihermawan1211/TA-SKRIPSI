package com.api.ponline.Kolam;

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
import com.api.ponline.dao.Request.KolamRequest;
import com.api.ponline.dao.Request.KomunitasRequest;
import com.api.ponline.dao.Request.LoginRequest;
import com.api.ponline.model.Entity.komunitas.Komunitas;
import com.api.ponline.model.Entity.user.AuthProvider;
import com.api.ponline.model.Entity.user.User;
import com.api.ponline.model.Entity.user.UserRole;
import com.api.ponline.model.repository.kolam.KolamRepo;
import com.api.ponline.model.repository.komunitas.KomunitasRepo;
import com.api.ponline.model.repository.user.UserRepository;
import com.api.ponline.services.komunitas.KomunitasServices;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class KolamTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KolamRepo kolamRepo;

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
    public void ujiCreateKolam_namaNull() throws Exception {

        String bearerToken =  getBearerTokenUser("email_user@test.com", "passwordBaru123");
        
        KolamRequest kolamRequest = new KolamRequest();
        kolamRequest.setKomunitas(komunitasRepo.getAllKomunitas().get(0));
        kolamRequest.setName(null);

        String requestJson = new ObjectMapper().writeValueAsString(kolamRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/kolam")
                .header("Authorization", "Bearer " + bearerToken)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void ujiCreateKolam_valid() throws Exception {

        String bearerToken =  getBearerTokenUser("email_user@test.com", "passwordBaru123");
        
        KolamRequest kolamRequest = new KolamRequest();
        kolamRequest.setKomunitas(komunitasRepo.getAllKomunitas().get(0));
        kolamRequest.setName("Nama Kolam Test");

        String requestJson = new ObjectMapper().writeValueAsString(kolamRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/kolam")
                .header("Authorization", "Bearer " + bearerToken)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void ujiReadAllKolam_valid() throws Exception {

        String bearerToken =  getBearerTokenUser("email_user@test.com", "passwordBaru123");
        
        mockMvc.perform(MockMvcRequestBuilders.get("/kolam")
                .header("Authorization", "Bearer " + bearerToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void ujiUpdateKolam_valid() throws Exception {

        String bearerToken =  getBearerTokenUser("email_user@test.com", "passwordBaru123");
        
        Long idKolam = kolamRepo.getAllKolam().get(kolamRepo.getAllKolam().size()-1).getId();
        
        KolamRequest kolamRequest = new KolamRequest();
        kolamRequest.setId(idKolam);
        kolamRequest.setKomunitas(komunitasRepo.getAllKomunitas().get(0));
        kolamRequest.setName("Nama Kolam - Edit");

        String requestJson = new ObjectMapper().writeValueAsString(kolamRequest);
        mockMvc.perform(MockMvcRequestBuilders.put("/kolam")
                .header("Authorization", "Bearer " + bearerToken)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void ujiDeleteKolam_valid() throws Exception {
        String bearerToken =  getBearerTokenUser("email_user@test.com", "passwordBaru123");

        Long idKolam = kolamRepo.getAllKolam().get(kolamRepo.getAllKolam().size()-1).getId();
        mockMvc.perform(MockMvcRequestBuilders.delete("/kolam/delete/"+idKolam)
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
