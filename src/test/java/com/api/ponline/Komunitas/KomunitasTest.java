package com.api.ponline.Komunitas;

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

import com.api.ponline.dao.Request.KomunitasRequest;
import com.api.ponline.dao.Request.LoginRequest;
import com.api.ponline.model.Entity.user.AuthProvider;
import com.api.ponline.model.Entity.user.User;
import com.api.ponline.model.Entity.user.UserRole;
import com.api.ponline.model.repository.komunitas.KomunitasRepo;
import com.api.ponline.model.repository.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class KomunitasTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

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
    public void ujiCreateKomunitas_namaNull() throws Exception {

        String bearerToken =  getBearerTokenUser("email_user@test.com", "passwordBaru123");
        
        KomunitasRequest komunitasRequest = new KomunitasRequest();
        komunitasRequest.setNama(null);
        komunitasRequest.setAlamat("Alamat Komunitas");

        String requestJson = new ObjectMapper().writeValueAsString(komunitasRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/komunitas")
                .header("Authorization", "Bearer " + bearerToken)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void ujiCreateKomunitas_valid() throws Exception {

        String bearerToken =  getBearerTokenUser("email_user@test.com", "passwordBaru123");
        
        KomunitasRequest komunitasRequest = new KomunitasRequest();
        komunitasRequest.setNama("Nama Komunitas");
        komunitasRequest.setAlamat("Alamat Komunitas");

        String requestJson = new ObjectMapper().writeValueAsString(komunitasRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/komunitas")
                .header("Authorization", "Bearer " + bearerToken)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void ujiReadAllKomunitas_valid() throws Exception {

        String bearerToken =  getBearerTokenUser("email_user@test.com", "passwordBaru123");
        
        mockMvc.perform(MockMvcRequestBuilders.get("/komunitas")
                .header("Authorization", "Bearer " + bearerToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void ujiReadOneKomunitas_valid() throws Exception {

        String bearerToken =  getBearerTokenUser("email_user@test.com", "passwordBaru123");
        Long idKomunitas = komunitasRepo.getAllKomunitas().get(komunitasRepo.getAllKomunitas().size()-1).getId();
        
        mockMvc.perform(MockMvcRequestBuilders.get("/komunitas/find?id="+idKomunitas)
                .header("Authorization", "Bearer " + bearerToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void ujiUpdateKomunitas_valid() throws Exception {

        String bearerToken =  getBearerTokenUser("email_user@test.com", "passwordBaru123");
        
        Long idKomunitas = komunitasRepo.getAllKomunitas().get(komunitasRepo.getAllKomunitas().size()-1).getId();
        
        KomunitasRequest komunitasRequest = new KomunitasRequest();
        komunitasRequest.setId(idKomunitas);
        komunitasRequest.setNama("Edit Nama Komunitas");
        komunitasRequest.setAlamat("Edit Alamat Komunitas");

        String requestJson = new ObjectMapper().writeValueAsString(komunitasRequest);
        mockMvc.perform(MockMvcRequestBuilders.put("/komunitas")
                .header("Authorization", "Bearer " + bearerToken)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void ujiDeleteKomunitas_valid() throws Exception {
        String bearerToken =  getBearerTokenUser("email_user@test.com", "passwordBaru123");

        Long idKomunitas = komunitasRepo.getAllKomunitas().get(komunitasRepo.getAllKomunitas().size()-1).getId(); 
        
        mockMvc.perform(MockMvcRequestBuilders.delete("/komunitas/delete/"+idKomunitas)
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
